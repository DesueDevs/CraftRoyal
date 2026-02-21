package desue.craftRoyal.Cards.Flying;

import desue.craftRoyal.Cards.Card;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Minion extends Card {
    public static EntityType getEntityType() {
        return EntityType.CHICKEN;
    }
    public Minion(Player player, int elixirCost, int troopLevel, int spawnNumber, Location spawnLocation) {
        super(
                "Minion " + troopLevel,
                player,
                elixirCost,
                getEntityType(),
                troopLevel,
                spawnNumber,
                desue.craftRoyal.Troops.Flying.Minion.class
        );
        spawnTroops(spawnLocation);
    }
}
