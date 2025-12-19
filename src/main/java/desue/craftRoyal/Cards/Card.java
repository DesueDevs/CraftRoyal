package desue.craftRoyal.Cards;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class Card {
    protected Logger logger = CraftRoyal.getInstance().getLogger();

    protected Player player = null;
    protected int elixerCost = 0;
    protected Troop spawnedTroop = null;
    protected int spawnTroopLevel = 0;
    protected int numberOfTroops = 0;
    public Card(Player player, int elixerCost, Troop spawnedTroop, int spawnTroopLevel, int numberOfTroops) {
        this.player = player;
        this.elixerCost = elixerCost;
        this.spawnedTroop = spawnedTroop;
        this.spawnTroopLevel = spawnTroopLevel;
        this.numberOfTroops = numberOfTroops;
    }

    protected void SpawnTroops(Location SpawnLoc) {
        // Basic Validations
        if (this.player == null) {
            logger.warning("Card: SpawnTroops called but player is null");
            return;
        }
        if (this.spawnedTroop == null) {
            logger.warning("Card: SpawnTroops called but spawnedTroop is null");
            return;
        }
        if (this.numberOfTroops <= 0) {
            logger.warning("Card: SpawnTroops called but numberOfTroops is non-positive");
            return;
        }
        if (SpawnLoc == null) {
            logger.warning("Card: SpawnTroops called but SpawnLoc is null");
            return;
        }
        if (this.spawnTroopLevel <= 0) {
            logger.warning("Card: SpawnTroops called but spawnTroopLevel is non-positive");
            return;
        }

        // Elixer Check
        if (this.player.getExpToLevel() < this.elixerCost) {
            this.player.sendMessage("Not enough elixer to play this card!");
            return;
        }else {
            // Deduct Elixer
            this.player.setLevel(this.player.getExpToLevel() - this.elixerCost);
        }

        // Summon troops
        Block spawnBlock = SpawnLoc.getWorld().getBlockAt(SpawnLoc);
        // Spawn Flying troops in air
        int heightInc = 0;
        if(this.spawnedTroop.troopType == Troop.TroopTypes.Flying) {
            heightInc = 2;
        }
        for (int i = 0; i < this.numberOfTroops; i++) {
            SpawnLoc.getWorld().spawn(spawnBlock.getLocation().add(0,heightInc,0), this.spawnedTroop.mob.getClass());
        }
    }
}
