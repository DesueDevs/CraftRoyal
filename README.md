# CraftRoyale

CraftRoyale is an attempt to create similar gameplay to ClashRoyale. CraftRoyal is developed around [Paper](https://github.com/PaperMC/Paper), and Bukkit.


### How Does CraftRoyale Work

CraftRoyale manipulates how mobs in minecraft would ordinarily act.
When the mob is spawned, it triggers a listener called "onCreatureSpawn". Inside this listener, the mob that was spawned is checked to ensure it's a troop. This is a basic check that compares the mob spawned to the approved list of *troop mobs* to prevent the most naturally spawned mob from being considered a troop and thus having troop logic.
After the troop check, the mob is given a few new entries into its Persistent Data Container, including a boolean that identifies the mob as a troop (this is the "isTroopKey"), and a unique troopID (this is a UUID, and is the "troopIDKey").
Next, this mob is given a specific troop object (currently based on which mob is being spawned, but this is expected to change). By assigning the mob as an specific troop object it specifies how much health and damage this troop has/does.
Finally, the troop begins it's unique AI logic loop. Inside the logic loop, it checks to make sure the troop is still alive and active to prevent resources from being allocated to a invalid troop. Then it checks for nearby troops that it can attack. Lastly it begins pathfinding to its target and attacking.


### What To Come

As of 12/19/2025 this project is still in the early stages, and still requires some of the major game mechanics to even begin functioning like ClashRoyale. These mechanics are:

- [ ] Use of *cards* to spawn troops, and troop groups
- [ ] Buildings
- [ ] Towers (Both King and Tower troops)
- [ ] In depth elixer management (Reductions from card use, and passive growth)
- [ ] Game creation (Method to actually vs another player)


### What's The Point

Originally, this project was meant as a joke that one of my friends made to me, and thanks to me being bored at that time I thought "oh why not".
As I started setting up the framework for what I have now, I realized that I was actually using some of the java concepts that I learned about, but never previously found a use for.
So from a offhanded joke made by a friend I began using and gaining experience in elements of Java. I had nothing, but book knowledge in
