package desue.craftRoyal.Mechanics;

import desue.craftRoyal.Cards.Card;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class UseCard {
    public Card usedCard(ItemStack item) {
        AtomicInteger troopLevel = new AtomicInteger();
        int numberOfTroops = 0;
        return null;
    }
    /**
        * Finds the block a player is looking at within a 20-block range.
        * @param player The player whose line of sight is being traced.
        * @return The block the player is looking at, or null if no block is found
    */
    public static Block findPlacementBlock(Player player) {
        return player.rayTraceBlocks(20).getHitBlock();
    }
}
