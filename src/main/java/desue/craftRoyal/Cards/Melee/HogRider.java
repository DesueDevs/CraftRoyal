package desue.craftRoyal.Cards.Melee;

import desue.craftRoyal.Cards.Card;
import desue.craftRoyal.Troops.Melee.Knight;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class HogRider extends Card {
    public static EntityType getEntityType() {
        return EntityType.PIG;
    }
    public HogRider(Player player, int elixirCost, int troopLevel, int spawnNumber, Location spawnLocation) {
        super(
                "Hog Rider " + troopLevel,
                player,
                elixirCost,
                getEntityType(),
                troopLevel,
                spawnNumber,
                desue.craftRoyal.Troops.Melee.HogRider.class
        );
        spawnTroops(spawnLocation);
    }
}
