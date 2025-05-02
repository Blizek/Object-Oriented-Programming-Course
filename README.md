# Darwin World

## Overview

A project for the Object-Oriented Programming course at AGH University of Krakow that simulates the lives of monkeys and their environment.  
The simulation features customizable parameters, graphical visualization of the ecosystem, and real-time statistics for both the environment and individual animals.

## About

This project simulates the challenging lives of monkeys living on a rectangular map, trying to survive by searching for food.  
If a monkey gathers enough energy, it can reproduce with another monkey and create a "baby monkey" that inherits genes describing its movement.

The map reflects a simplified Earth. Along the equator lies the jungle, where bananas are most abundant. Outside of the jungle, monkeys roam steppes where edible plants are sparse.  
Bananas provide the energy necessary for monkeys to stay alive.

Each monkey has its own set of statistics, including energy, direction, and a genotype that determines how it moves.

A single day in the simulation includes:
- Removing dead animals from the map
- Turning and moving each monkey
- Consuming plants found at their destination
- Reproducing if two well-fed monkeys share a field
- Growing new plants on selected map tiles

Additionally, the simulation includes two optional game modes:
- **Cold War** – monkeys closer to the poles lose more energy
- **Genetic Mutation** – a random gene in the offspring mutates slightly

Below is an example screenshot of the gameplay:

![image](https://github.com/user-attachments/assets/6807271f-9b33-48c8-a1fe-7fe13bd074c3)

## Game Configuration

The simulation includes a variety of customizable parameters, such as map size, initial banana count, starting energy of monkeys, genome length, and more.

To save a preferred configuration:
1. Fill in your desired parameters.
2. Enter a name for the configuration.
3. Click the save button.

Saved configurations will appear on the left side of the screen. We’ve provided two default presets for you: a casual mode and a slow mode for observing long-term development.

Here is how the configuration page looks:

![image](https://github.com/user-attachments/assets/2a8443e6-5ee2-4352-b107-b3967aad6a18)

## Real-Time Statistics

Throughout the simulation, real-time statistics are displayed on the left side, including:
- Current number of animals
- Number of banana-containing fields
- Number of free fields
- Most popular genotype
- Average monkey energy
- Average monkey lifespan
- Average number of offspring

These statistics update every simulation day.  
For deeper insight, two charts are displayed and updated daily: the number of monkeys and the number of bananas.

On the right side, you can view detailed statistics of a selected monkey.  
To follow a monkey:
1. Pause the simulation using the `Zatrzymaj rozgrywkę` button.
2. Click on the monkey you want to follow.

You’ll be able to see its position, direction, energy, number of children, and more.  
You can then resume the simulation to continue tracking its stats in real time.

While paused, you can also:
- Highlight jungle areas using the `Pokaż preferowane pola traw` button
- Highlight all monkeys with the dominant genotype using the `Pokaż zwierzaki z dominującym genem` button

![image](https://github.com/user-attachments/assets/0c1a6b75-9b27-479f-ae43-cf2820968b29)

## Technical Requirements
- **Java 21**

## Authors
- **[Błażej Naziemiec](https://github.com/Blizek)**
- **[Dawid Żak](https://github.com/depebul)**
