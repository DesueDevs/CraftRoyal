package desue.craftRoyal.Cards;

import org.bukkit.entity.EntityType;

public record CardInfo(String cardName, int elixirCost, EntityType entityType, int troopLevel, int numberOfTroops) {
}
