package desue.craftRoyal.Cards.Ranged;

import desue.craftRoyal.Cards.Card;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Musketeer extends Card {
    public static EntityType getEntityType() {
        return EntityType.PILLAGER;
    }
    public Musketeer(Player player, int elixirCost, int troopLevel, int spawnNumber, Location spawnLocation) {
        super(
                "Musketeer " + troopLevel,
                player,
                elixirCost,
                getEntityType(),
                troopLevel,
                spawnNumber,
                desue.craftRoyal.Troops.Ranged.Musketeer.class
        );
        spawnTroops(spawnLocation);
    }
}
