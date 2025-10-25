import matplotlib.pyplot as plt
import matplotlib
import matplotlib.animation as animation

import math
import numpy as np

from sklearn.decomposition import PCA

import torch
import torch.nn as nn
from collections import OrderedDict

# This code is a modified version of: https://github.com/logancyang/loss-landscape-anim and https://mathformachines.com/posts/visualizing-the-loss-landscape/


#some functions to work with tensor and matrices
def normalize_weights(weights, origin):
    return [
        w * np.linalg.norm(wc.cpu()) / np.linalg.norm(w)
        for w, wc in zip(weights, origin.values())
    ]

def vectorize_weights_(weights):
    
    if isinstance(weights,(OrderedDict)):
      vec = [w.cpu().flatten() if isinstance(w, torch.Tensor) else w.flatten() for w in weights.values()]
    elif isinstance(weights,(list)):
      vec = [w.cpu().flatten() if isinstance(w, torch.Tensor) else w.flatten() for w in weights]
    
    vec = np.hstack(vec)
    
    return vec

def vectorize_weight_list_(weight_list):
    vec_list = []
    
    for weights in weight_list:
        vec_list.append(vectorize_weights_(weights))
    
    weight_matrix = np.column_stack(vec_list)
    return weight_matrix


def shape_weight_matrix_like_(weight_matrix, example):
    weight_vecs = np.hsplit(weight_matrix, weight_matrix.shape[1])
    
    sizes = [v.numel() if isinstance(v,torch.Tensor) else v.size for v in example.values()]
    shapes = [v.shape for v in example.values()]
    
    weight_list = []
    for net_weights in weight_vecs:
        vs = np.split(net_weights, np.cumsum(sizes))[:-1] #np.cumsum(sizes)
        vs = [v.reshape(s) for v, s in zip(vs, shapes)]
        weight_list.append(vs)
    return weight_list


def get_path_components_(training_path, n_components=2):
    # Vectorize network weights
    weight_matrix = vectorize_weight_list_(training_path)
    # Create components
    pca = PCA(n_components=2, whiten=True,random_state=1300)
    components = pca.fit_transform(weight_matrix)
    # Reshape to fit network
    example = training_path[0]
    weight_list = shape_weight_matrix_like_(components, example)
    return pca, weight_list


#Classes for loss landscape analysis
class Surface(object):
    """
    Class to compute and visualize loss surfaces in weight space.

    Parameters
    ----------
    model : torch.nn.Module
        Model whose loss surface is being analyzed.
    inputs_outputs : DataLoader
        Data used to evaluate the loss surface.
    criterion : torch.nn.Module
        Loss function to evaluate the model.
    """

    def __init__(self,model,loader,criterion,device="cuda:0"):
        self.model_ = model
        self.loader = loader
        self.criterion=criterion
        self.device=device

    def test_on_batch(self,model):
        total_loss = 0
        model.eval()
        model.to(self.device)

        num_batches = 0
        with torch.no_grad():
            for batch_idx, (inputs, targets) in enumerate(self.loader):

                if device is not None:
                    inputs, targets = inputs.to(device), targets.to(device)

                outputs = model(inputs)
                loss = self.criterion(outputs,targets.float())
                total_loss += loss.item()
                num_batches += 1

        return total_loss / num_batches

    def compile(self, range, points, coords, eval_fn=None, ):
        """
        Compile a loss surface grid by evaluating model across weight space.

        Parameters
        ----------
        range : float
            Scaling factor for weight perturbations.
        points : int
            Number of grid points in each axis.
        coords : callable
            Coordinate system (e.g., RandomCoordinates or PCACoordinates).
        variable : str, optional
            Quantity to evaluate ('loss' or 'sharpness').
        nv_params : dict, optional
            Extra parameters for evaluation.
        """

        a_grid = np.linspace(-1.0, 1.0, num=points) ** 3 * range
        b_grid = np.linspace(-1.0, 1.0, num=points) ** 3 * range

        grid = np.empty([len(a_grid), len(b_grid)])
        keys_params = coords.keys

        #we calculate all losses to fill the grid
        for i, a in enumerate(a_grid):
            for j, b in enumerate(b_grid):
                state = coords(a,b) #origin + alpha*rnd_x + beta*rnd_y
                state_dict = {k: v if isinstance(v, torch.Tensor) else torch.tensor(v) for k, v in zip(keys_params, state)}
                self.model_.load_state_dict(state_dict)   #set_weights(coords(a, b))

                results = self.test_on_batch(model=self.model_) if eval_fn is None else eval_fn(self.model_,self.loader,criterion=self.criterion,device=self.device)

                grid[j, i] = results[0] if isinstance(results,tuple) else results

        self.a_grid_ = a_grid
        self.b_grid_ = b_grid
        self.grid_ = grid

    def plot(self,range=1.0,levels=10,ax=None,log_scale=True,v_min=None,v_max=None):

        xs = self.a_grid_
        ys = self.b_grid_
        zs = self.grid_

        if ax is None:
            fig, ax = plt.subplots()
            ax.set_aspect("equal")

        min_loss = np.nanmin(zs)
        max_loss = np.nanmax(zs)

        if log_scale:
            levels = np.exp(np.linspace(math.log(min_loss), math.log(max_loss), num=levels))
            norm = matplotlib.colors.LogNorm(vmin=min_loss, vmax=max_loss * 2.0)
        else:
            levels = np.linspace(min_loss, max_loss, num=levels)
            norm = None

        CS = ax.contourf(xs, ys, zs, levels=levels, cmap='BuGn', norm=norm)

        fig = ax.get_figure()
        cbar = fig.colorbar(CS, ax=ax, orientation="horizontal", pad=0.2)
        cbar.ax.xaxis.set_major_formatter(plt.FuncFormatter(lambda x, _: f"{x:.1f}"))
        cbar.ax.tick_params(labelsize=8)
        #cbar.set_label("Loss")

        return ax, xs, ys, zs, levels, min_loss, max_loss


class PCACoordinates(object):
    '''
    This class generates the PCA of training path, i.e, it generates the two directions that allow to generate most of variation

    Args:

    training_path:  a list with all state_dict for model
    param_keys:  the names of the dictionary of state_dict
    '''

    def __init__(self,training_path,param_keys,origin=None,device="cuda:0"):
        
        if origin is None:
            origin = training_path[-1]
        
        self.keys = param_keys
        self.pca_, self.components = get_path_components_(training_path)
        self.set_origin(origin)
        self.device = device


    def __call__(self, a, b):
        '''
        A call to the class

        It recieves a,b (scalars) and outputs a position in the plane
        '''
        return [
            torch.Tensor(a * w0).to(self.device) + torch.Tensor(b * w1).to(self.device) + wc.to(self.device)
            for w0, w1, wc in zip(self.v0_, self.v1_, self.origin_.values())
        ]

    def set_origin(self, origin, renorm=True):
        self.origin_ = origin
        if renorm:
            self.v0_ = normalize_weights(self.components[0], origin)
            self.v1_ = normalize_weights(self.components[1], origin)
        else:
            self.v0_ = self.components[0]
            self.v1_ = self.components[1]


# a function for get the optimization path
def weights_to_coordinates(coords,training_path, origin_vector = None):
    """Project the training path onto the first two principal components using the pseudoinverse."""

    components = [coords.v0_, coords.v1_]
    comp_matrix = vectorize_weight_list_(components)
    # the pseudoinverse
    comp_matrix_i = np.linalg.pinv(comp_matrix)

    # the origin vector
    if origin_vector is None:
        w_c = vectorize_weights_(training_path[-1])
    else:
        w_c = vectorize_weights_(origin_vector)
    
    # center the weights on the training path and project onto components
    coord_path = np.array(
        [
            comp_matrix_i @ (vectorize_weights_(weights) - w_c)
            for weights in training_path
        ]
    )
    return coord_path


def get_min_idx(grid_):
  min_idx_flat = np.argmin(grid_)              # flat index
  min_idx = np.unravel_index(min_idx_flat, grid_.shape)  # 2D index
  return min_idx

def gen_pcacoords_surface(model,w_history,loader,criterion,eval_fn,resolution=10,loss_range=1):
  
    parameter_keys_in_model = [n for n, p in model.named_parameters()]
    print("doing PCA over training states over model...")
    pcoords = PCACoordinates(training_path=w_history, param_keys=parameter_keys_in_model)
    print("creating Surface instance and calculating loss over landscape of model 1...")
    loss_surface = Surface(model=model, loader=loader, criterion=criterion)
    loss_surface.compile(points=resolution, coords=pcoords, range=loss_range, eval_fn=eval_fn)
    
    return pcoords, loss_surface



# Vizualization of 2 optimization paths
def compare_training_paths(
    model_1,
    model_2,
    w_history_1,
    w_history_2,
    acc_vals_1,
    acc_vals_2,
    loader,
    eval_fn,
    resolution=10,
    levels=10,
    loss_range=0.75,
    criterion=None,
    filename="comparison.gif",
    fps=5,
    interval=50
):
    """Compare two optimization trajectories and animate their progress in loss landscape."""

    if criterion is None:
        criterion = nn.CrossEntropyLoss()

    # --- First model ---
    pcoords_1, loss_surface_1 = gen_pcacoords_surface(model=model_1,
                                                      w_history=w_history_1,
                                                      loader=loader,
                                                      criterion=criterion,
                                                      eval_fn=eval_fn,
                                                      resolution=resolution,
                                                      loss_range=loss_range)

    # --- Second model ---
    pcoords_2, loss_surface_2 = gen_pcacoords_surface(model=model_2,
                                                      w_history=w_history_2,
                                                      loader=loader,
                                                      criterion=criterion,
                                                      eval_fn=eval_fn,
                                                      resolution=resolution,
                                                      loss_range=loss_range)

    # --- Paths and grids ---
    path2d_1 = weights_to_coordinates(pcoords_1, w_history_1).tolist()
    path2d_2 = weights_to_coordinates(pcoords_2, w_history_2).tolist()
    loss_grid_1, loss_grid_2 = loss_surface_1.grid_, loss_surface_2.grid_
    coords_1, coords_2 = (loss_surface_1.a_grid_, loss_surface_1.b_grid_), (loss_surface_2.a_grid_, loss_surface_2.b_grid_)

    # --- Figure setup ---
    fig = plt.figure(figsize=(8, 8))
    gs = fig.add_gridspec(2, 2, height_ratios=[1, 1])
    ax1, ax2, ax3 = fig.add_subplot(gs[0, 0]), fig.add_subplot(gs[0, 1]), fig.add_subplot(gs[1, :])
    ax1.set_title("Model 1")
    ax2.set_title("Model 2")
    ax3.set_ylabel("Accuracy (%)")

    # --- Contours ---
    v_min = np.min([loss_grid_1.min(), loss_grid_2.min()])
    v_max = 3 
    print(f"Loss range: {v_min:.4f} - {v_max:.4f}")

    levels_arr = np.exp(np.linspace(math.log(v_min), math.log(v_max), num=levels))
    norm = matplotlib.colors.LogNorm(vmin=v_min, vmax=v_max)
    cs1 = ax1.contourf(coords_1[0], coords_1[1], loss_grid_1, alpha=0.9, cmap="BuGn", norm=norm, levels=levels_arr)
    cs2 = ax2.contourf(coords_2[0], coords_2[1], loss_grid_2, alpha=0.9, cmap="BuGn", norm=norm, levels=levels_arr)
    cbar = fig.colorbar(cs1, ax=[ax1, ax2], orientation="horizontal", pad=0.2)
    cbar.set_label("Loss")

    true_optim_point_1 = get_min_idx(loss_grid_1)
    true_optim_point_2 = get_min_idx(loss_grid_2)

    # --- Trajectories (loss surface) ---
    W0 = path2d_1[0]
    Z0 = path2d_2[0]
    w1s, w2s, z1s, z2s = [W0[0]], [W0[1]], [Z0[0]], [Z0[1]]
    (pathline_loss_1,) = ax1.plot(w1s, w2s, color="blue", lw=1)
    (point1,) = ax1.plot(W0[0], W0[1], "blue")
    (pathline_loss_2,) = ax2.plot(z1s, z2s, color="orange", lw=1)
    (point2,) = ax2.plot(Z0[0], Z0[1], "orange")

    #(optim_point_1,) = ax1.plot(coords_1[0][true_optim_point_1[0]], coords_1[1][true_optim_point_1[1]], "*", label="target min")
    #(optim_point_2,) = ax2.plot(coords_2[0][true_optim_point_2[0]], coords_2[1][true_optim_point_2[1]], "*", label="target min")

    # --- Accuracy plots ---
    y1s, y2s, q1s, q2s = [], [], [], []
    
    (pathline_accuracy_1,) = ax3.plot([], [], color="blue", lw=1, label="SGD")
    (pathline_accuracy_2,) = ax3.plot([], [], color="orange", lw=1, label="Adam")
    
    ax3.set_xlim(0, len(acc_vals_1));
    ax3.set_ylim(0, 100);
    ax3.legend()

    # --- Bounds ---
    dist1 = np.max([np.abs(W0[0]), np.abs(W0[1])]) * 1.1
    dist2 = np.max([np.abs(Z0[0]), np.abs(Z0[1])]) * 1.1
    ax1.set_xlim(-dist1,dist1); ax1.set_ylim(-dist1,dist1)
    ax2.set_xlim(-dist2,dist2); ax2.set_ylim(-dist2,dist2)

    # Step text
    step_text = ax1.text(0.05, 0.9, "", fontsize=10, ha="left", va="center", transform=ax1.transAxes)

    # --- Animation function ---
    def animate(i):
        W = path2d_1[i] # loss traj for model 1
        Z = path2d_2[i] # loss traj for model 2
        Y = acc_vals_1[i]  # accuracy line for for model 1
        Q = acc_vals_2[i]   # accuracy line for for model 2

        w1s.append(W[0]); w2s.append(W[1])
        z1s.append(Z[0]); z2s.append(Z[1])

        pathline_loss_1.set_data(w1s, w2s)
        pathline_loss_2.set_data(z1s, z2s)
        point1.set_data([W[0]], [W[1]])
        point2.set_data([Z[0]], [Z[1]])

        y1s.append(i); y2s.append(Y)
        q1s.append(i); q2s.append(Q)
        pathline_accuracy_1.set_data(y1s, y2s)
        pathline_accuracy_2.set_data(q1s, q2s)

        step_text.set_text(f"step: {i}")

    anim = animation.FuncAnimation(fig, animate, frames=len(path2d_1), interval=interval, blit=False, repeat=False)
    anim.save(filename, writer="pillow", fps=fps)

    print(f"Animation saved to {filename}")
    return anim


        
      
      