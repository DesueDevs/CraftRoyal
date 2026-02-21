package desue.craftRoyal.Cards.Melee;

import desue.craftRoyal.Cards.Card;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class MiniPekka extends Card {
    public static EntityType getEntityType() {
        return EntityType.COPPER_GOLEM  ;
    }
    public MiniPekka(Player player, int elixirCost, int troopLevel, int spawnNumber, Location spawnLocation) {
        super(
                "MiniPekka " + troopLevel,
                player,
                elixirCost,
                getEntityType(),
                troopLevel,
                spawnNumber,
                desue.craftRoyal.Troops.Melee.MiniPekka.class
        );
        spawnTroops(spawnLocation);
    }
}
