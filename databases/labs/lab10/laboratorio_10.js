// use cc3201

// P1. Los documentos que tengan Jewel Pokemon en Category
db.pokedex.find(
  { Category: "Jewel Pokemon" }
).pretty();

// P2. Los documentos de los pokemon con Type 1 Bug y Type 2 Poison
db.pokedex.find(
  {
    "Type 1": "Bug",
    "Type 2": "Poison"
  }
).pretty();

// P3. Name de los pokemon que tienen doble tipo, es decir, aquellos cuyo atributo Type 2 es distinto de None
db.pokedex.find(
  {
    "Type 2": { $ne: "None" }
  },
  {
    Name: 1,
    _id: 0
  }
).pretty();

// P4. Name, Type 1 y Type 2 de los pokemon “Ca˜nones de Cristal”, es decir, aquellos que tienen Attack mayor a 120 y Defense menor a 50
db.pokedex.find(
  {
    Attack: { $gt: 120 },
    Defense: { $lt: 50 }
  },
  {
    Name: 1,
    "Type 1": 1,
    "Type 2": 1,
    _id: 0
  }
).pretty();

// P5. Name, Type 1 y Type 2 de los pokemon “Defensivos”, es decir, aquellos que tienen HP o Defense o Special Defense mayor o igual a 120
db.pokedex.find(
  {
    $or: [
      { HP: { $gte: 120 } },
      { Defense: { $gte: 120 } },
      { "Special Defense": { $gte: 120 } }
    ]
  },
  {
    Name: 1,
    "Type 1": 1,
    "Type 2": 1,
    _id: 0
  }
).pretty();

// P6. Name, HP y Attack de los pokemon que tengan en Abilities alguno de los siguientes: Inner Focus, Insomnia, Defiant, o Stench
db.pokedex.find(
  {
    Abilities: { 
      $in: [
        "Inner Focus",
        "Insomnia",
        "Defiant",
        "Stench"
      ]
    }
  },
  {
    Name: 1,
    HP: 1,
    Attack: 1,
    _id: 0
  }
).pretty();

// P7. Name, HP y Attack de los pokemon que tengan en Abilities todos los siguientes: Intimidate, Anger Point, y Sheer Force
db.pokedex.find(
  {
    Abilities: { 
      $all: [
        "Intimidate",
        "Anger Point",
        "Sheer Force"
      ]
    }
  },
  {
    Name: 1,
    HP: 1,
    Attack: 1,
    _id: 0
  }
).pretty();

// P8. Name, HP y Attack de los pokemon que tengan en Abilities exactamente Keen Eye y Sniper (en cualquier orden, sin otras habilidades)
db.pokedex.find(
  {
    Abilities: { 
      $all: [
        "Keen Eye",
        "Sniper"
      ],
      $size: 2
    }
  },
  {
    Name: 1,
    HP: 1,
    Attack: 1,
    _id: 0
  }
).pretty();

// P9. Todos los atributos excepto Moves de aquellos pokemon que tengan Solar Beam como llave dentro de Moves.
db.pokedex.find(
  {
    "Moves.Solar Beam": { $exists: true }
  },
  {
    Moves: 0,
    _id: 0
  }
).pretty();

// P10. Name, HP y Attack de los pokemon que no tengan Surf en Moves y que tengan Water Gun con Level mayor o igual a 10 en Moves.
db.pokedex.find(
  {
    "Moves.Surf": { $exists: false },
    "Moves.Water Gun.Level": { $gte: 10 }
  },
  {
    Name: 1,
    HP: 1,
    Attack: 1,
    _id: 0
  }
).pretty();