import torch.nn as nn
import torch
import torch.optim as optim
import copy

class MLP(nn.Module):
    def __init__(self, activation_fn):
        '''
        Modelo MLP para MNIST dataset. Adaptado de https://github.com/logancyang/loss-landscape-anim/blob/master/loss_landscape_anim/model.py
        '''
        super(MLP, self).__init__()
        self.dropout_rate = 0
        self.dropout = nn.Dropout(self.dropout_rate)

        self.layer_1 = nn.Linear(784, 256) # here we use 784 as in dim, because is the dimension of MNIST dataset
        self.layer_2 = nn.Linear(256, 128)
        self.layer_3 = nn.Linear(128, 10)  # MNIST dataset has 10 possible labels

        # We pass the activation function as a parameter to take advantage of inheritance
        self.activation = activation_fn

    def forward(self, x):
        x = self.activation(self.layer_1(x))
        x = self.dropout(x)
        x = self.activation(self.layer_2(x))
        x = self.dropout(x)
        x = self.layer_3(x) # logits (no sigmoid/softmax here)
        return x

    def get_flat_params(self):
        """Get flattened and concatenated params of the model."""
        params = self._get_params()
        flat_params = torch.Tensor()
        if torch.cuda.is_available() and self.gpus > 0:
            flat_params = flat_params.cuda()
        for _, param in params.items():
            flat_params = torch.cat((flat_params, torch.flatten(param)))
        return flat_params

    def init_from_flat_params(self, flat_params):
        """Set all model parameters from the flattened form."""
        if not isinstance(flat_params, torch.Tensor):
            raise AttributeError(
                "Argument to init_from_flat_params() must be torch.Tensor"
            )
        shapes = self._get_param_shapes()
        state_dict = self._unflatten_to_state_dict(flat_params, shapes)
        self.load_state_dict(state_dict, strict=True)

    def _get_param_shapes(self):
        shapes = []
        for name, param in self.named_parameters():
            shapes.append((name, param.shape, param.numel()))
        return shapes

    def _get_params(self):
        params = {}
        for name, param in self.named_parameters():
            params[name] = param.data
        return params

    def _unflatten_to_state_dict(self, flat_w, shapes):
        state_dict = {}
        counter = 0
        for shape in shapes:
            name, tsize, tnum = shape
            param = flat_w[counter : counter + tnum].reshape(tsize)
            state_dict[name] = torch.nn.Parameter(param)
            counter += tnum
        assert counter == len(flat_w), "counter must reach the end of weight vector"
        return state_dict

# For MLP with ReLu activation
class MLP_ReLU(MLP):
    def __init__(self):
        super().__init__(nn.ReLU())

# For MLP with Sigmoid activation
class MLP_Sigmoid(MLP):
    def __init__(self):
        super().__init__(nn.Sigmoid())

def init_weights(m):
        if isinstance(m, nn.Linear):
            torch.nn.init.xavier_uniform_(m.weight)
            torch.nn.init.zeros_(m.bias)

#for sanity check 
def models_are_equal(model1, model2, check_values=True):
    #check if they have the same structure of parameters
    params1 = dict(model1.named_parameters())
    params2 = dict(model2.named_parameters())

    if params1.keys() != params2.keys():
        return False 

    #check shapes and values
    for k in params1.keys():
        #check shape
        if params1[k].shape != params2[k].shape:
            return False 
        #check values 
        if check_values and not torch.equal(params1[k].data, params2[k].data):
            return False  

    return True
    

def eval_mnist_fn(model, loader, criterion, device="cuda:0"):
    """
    Evaluation loop for classification tasks on MNIST dataset.

    Args:
        model (nn.Module): The PyTorch model to evaluate.
        loader (DataLoader): Data loader for the evaluation dataset (for example, test set).
        criterion (nn.Module): Loss function (for example, nn.CrossEntropyLoss).
        device (str): Device to run evaluation on ("cpu" or "cuda").

    Returns:
        tuple: (avg_loss, accuracy)
            avg_loss (float): Average loss over the dataset.
            accuracy (float): Classification accuracy in percentage.
    """

    # Move model to device and set to eval mode
    model.to(device)
    model.eval()

    # Initialize metrics
    total_loss = 0.0
    correct = 0

    # Evaluation loop
    with torch.no_grad():
        for image, tag in loader:
            # Move data to device
            image, tag = image.to(device), tag.to(device)

            # Forward pass
            outputs = model(image)
            loss = criterion(outputs, tag)

            # Update metrics
            total_loss += loss.item()
            _, preds = torch.max(outputs.data, 1)

            # Calculate number of correct predictions
            correct += (preds == tag).sum().item()

    avg_loss = total_loss / len(loader.dataset)
    accuracy = correct / len(loader.dataset) * 100
    return avg_loss, accuracy

def train_model(model, train_loader, test_loader,
                optimizer_fn=optim.SGD, lr=0.01,
                num_epochs=10, criterion=nn.CrossEntropyLoss,
                device="cuda:0", eval_fn=None, **optimizer_kwargs):
    """
    Generic training loop for classification tasks.
    Args:
        model (nn.Module): The PyTorch model to train.
        train_loader (DataLoader): Training data loader.
        test_loader (DataLoader): Test/validation data loader.
        optimizer_fn (torch.optim.Optimizer): Optimizer class (for example, optim.Adagrad).
        lr (float): Learning rate.
        num_epochs (int): Number of training epochs.
        criterion (nn.Module): Loss function.
        device (str): Device ("cpu" or "cuda").
        optimizer_kwargs: Additional kwargs for optimizer.
    """

    # Move model to device
    model.to(device)

    # Initialize optimizer and loss
    optimizer = optimizer_fn(model.parameters(), lr=lr, **optimizer_kwargs)
    criterion.to(device)

    train_losses = []
    test_losses = []
    accuracies = []
    weights_history = []

    # Evaluate initial model performance (before training)
    if eval_fn is not None:
        test_loss, test_acc = eval_fn(model, test_loader, criterion, device)
        test_losses.append(test_loss)
        accuracies.append(test_acc)
    
    weights_history.append(copy.deepcopy(model.state_dict()))

    for epoch in range(num_epochs):
        # ---- Training ----
        model.train()

        total_loss = 0.0
        for image, tag in train_loader:
            # Move data to device
            image, tag = image.to(device), tag.to(device)

            # Zero gradients
            optimizer.zero_grad()
            
            # Forward pass
            outputs = model(image)
            loss = criterion(outputs, tag)

            # Backward pass and optimization
            loss.backward()
            optimizer.step()

            total_loss += loss.item() #* image.size(0)
        avg_loss = total_loss / len(train_loader.dataset)
        train_losses.append(avg_loss)

        # ---- Evaluation ----
        if eval_fn is not None:
            test_loss, test_acc = eval_fn(model, test_loader, criterion, device)

        test_losses.append(test_loss)
        accuracies.append(test_acc)
        weights_history.append(copy.deepcopy(model.state_dict()))

    return train_losses, test_losses, accuracies, weights_history