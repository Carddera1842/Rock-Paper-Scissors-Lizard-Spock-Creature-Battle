# RPSLS Creature Battle!

A Java console-based creature battling game inspired by the classic **Rock, Paper, Scissors, Lizard, Spock** game.

What started as a simple Rock Paper Scissors Java exercise has evolved into a Pokémon-style battle system featuring unique creatures, individual stats, moves, type advantages, and a MySQL-backed data system.

## How It Works

Players choose a creature belonging to one of five types:

-  Rock
-  Paper
-  Scissors
-  Lizard
-  Spock

Each creature has its own:

- HP
- Attack
- Defense
- Speed
- Type
- Description
- Available moves

The computer randomly selects an opponent and the two creatures battle until one reaches 0 HP.

## Type System

The battle system is based on Rock, Paper, Scissors, Lizard, Spock rules:

| Type | Strong Against |
|---|---|
| Rock | Scissors, Lizard |
| Paper | Rock, Spock |
| Scissors | Paper, Lizard |
| Lizard | Paper, Spock |
| Spock | Rock, Scissors |

Type advantages are stored in the database rather than hard-coded into the battle logic.

When a move has a type advantage against the opposing creature, it receives a damage bonus.

## Creatures

The game currently includes 10 creatures:

| Creature | Type | HP | ATK | DEF | SPD |
|---|---|---:|---:|---:|---:|
| Pebblit | Rock | 85 | 20 | 30 | 10 |
| Obsidion | Rock | 65 | 35 | 18 | 20 |
| Foldfin | Paper | 70 | 22 | 18 | 32 |
| Scrollsage | Paper | 80 | 18 | 25 | 20 |
| Shearling | Scissors | 65 | 30 | 17 | 34 |
| Snipjaw | Scissors | 75 | 28 | 25 | 22 |
| Gecklash | Lizard | 75 | 26 | 20 | 30 |
| Thornscale | Lizard | 90 | 20 | 32 | 15 |
| Logikon | Spock | 70 | 28 | 22 | 26 |
| Vulcanox | Spock | 85 | 32 | 28 | 12 |

Different creatures are designed around different play styles, including high attack, high defense, and high speed.

## Moves

Creatures have their own movesets stored in MySQL.

Moves can contain:

- Type
- Damage
- Accuracy
- Category
- Effect value
- Description

Example moves include:

- Pebble Toss
- Boulder Crush
- Paper Storm
- Blade Rush
- Poison Spit
- Logic Blast
- Mind Meld
- Harden
- Regenerate
- Sharpen

Moves are connected to creatures through a many-to-many database relationship.

## Database Design

The game uses **MySQL** to store game data rather than hard-coding creatures and moves into Java.

Current tables include:

### `creature_types`

Stores the five battle types.

### `creatures`

Stores creature information including:

- Name
- Type
- Base HP
- Attack
- Defense
- Speed
- Description

### `moves`

Stores move information including:

- Name
- Type
- Damage
- Accuracy
- Category
- Effect value
- Description

### `creature_moves`

Junction table representing the many-to-many relationship between creatures and moves.

### `type_advantages`

Stores type matchup relationships used by the battle system.

For example:

```text
ROCK -> SCISSORS
ROCK -> LIZARD
PAPER -> ROCK
SPOCK -> SCISSORS
```

This allows the Java application to retrieve type advantages from the database instead of relying on a large hard-coded conditional statement.

## Technologies

- Java
- MySQL
- JDBC
- Gradle
- IntelliJ IDEA
- Git / GitHub

## Concepts Demonstrated

This project demonstrates:

- Object-Oriented Programming
- Java classes and objects
- Repository pattern
- JDBC database connectivity
- SQL queries
- Prepared statements
- Relational database design
- Primary and foreign keys
- Many-to-many relationships
- SQL JOINs
- Randomization
- Input validation
- Game state management
- Type-based battle logic

## 🏗️ Project Structure

```text
src/main/java/org/example/

├── RockPaperScissors.java
│
├── database/
│   └── DatabaseConnection.java
│
├── model/
│   ├── Creature.java
│   └── Move.java
│
└── repository/
    ├── CreatureRepository.java
    ├── MoveRepository.java
    └── TypeAdvantageRepository.java
```

The application separates responsibilities between models, database access, repositories, and game logic.

## Current Development

The project is actively being expanded.

Current features include:

- Database-backed creature selection
- Random computer opponent
- Creature-specific movesets
- HP system
- Attack and defense stats
- Move accuracy
- Type advantages
- Damage calculation
- Healing and defensive move support
- Computer-controlled opponent

## Planned Features

Future improvements may include:

- Full status effect system
- Poison and other damage-over-time effects
- Attack and defense buffs/debuffs
- Improved computer battle strategy
- Speed-based turn order
- Player accounts
- Creature collections
- Experience and leveling
- Battle history
- More creatures and moves
- Improved battle balancing
- Graphical user interface

## Project Origin

This project began as a basic Java Rock Paper Scissors exercise.

The original version used three strings:

```java
String[] options = {"rock", "paper", "scissors"};
```

The project was expanded first into Rock, Paper, Scissors, Lizard, Spock and then redesigned as a database-driven creature battle game.

The goal is to take a simple programming exercise and progressively apply concepts used in larger software applications.

## Running the Project

### Requirements

- Java
- MySQL
- Gradle

### Database

Create a MySQL database for the project and configure the application database connection.

For security, database passwords should **not** be committed to the repository.

### Run

Clone the repository:

```bash
git clone https://github.com/Carddera1842/Rock-Paper-Scissors.git
```

Navigate into the project and run it through IntelliJ IDEA or Gradle.

## Status

**In Development**

The core battle system is functional, with additional battle mechanics and database features currently being developed.
