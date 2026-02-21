package desue.craftRoyal.Cards.Melee;

import desue.craftRoyal.Cards.Card;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.Location;
import org.bukkit.entity.*;

public class KnightCard extends Card {
    public KnightCard(Player player, int elixirCost, int troopLevel, int spawnNumber, Location spawnLocation) {
        super(
                player,
                elixirCost,
                EntityType.VINDICATOR,
                troopLevel,
                spawnNumber
        );
    }
}
