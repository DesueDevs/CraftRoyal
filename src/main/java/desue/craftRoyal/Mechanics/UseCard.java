package desue.craftRoyal.Mechanics;

import desue.craftRoyal.Cards.Card;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class UseCard {
    public Card usedCard(ItemStack item) {
        String[] itemDisplayName = String.valueOf(item.displayName()).split(":");

        String troopName = itemDisplayName[0].trim();
        int level = Integer.parseInt(itemDisplayName[1].replace("Lvl ", "").trim());

        List<Component> itemLore = item.lore();
        int elixerCost = 0;

        int numberOfTroops = 0;
        return null;
    }

    public boolean spawnTroop(Card card, Block block) {
        if (card == null || block == null) {
            return false;
        }
        // Implement troop spawning logic here, using card.spawnedTroop and block location
        return true;
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
