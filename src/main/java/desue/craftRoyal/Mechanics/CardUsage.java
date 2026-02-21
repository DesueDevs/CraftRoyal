package desue.craftRoyal.Mechanics;

import desue.craftRoyal.Cards.CardInfo;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.Nullable;

public class CardUsage {
    public static boolean isCard(@Nullable ItemStack item ) {
        if (item == null) {
            return false;
        }
        if (!(item.getItemMeta() instanceof BookMeta)) {
            return false;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if (meta.author().toString() != "CraftRoyal") {
            return false;
        }
        return true;
    }

    public static CardInfo getCardInfo(ItemStack item) {
        if (!isCard(item)) {
            return null;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        String cardName = meta.title().toString();
        int elixirCost = Integer.parseInt(meta.page(0).toString());
        EntityType type = EntityType.valueOf(meta.page(1).toString());
        int troopLevel = Integer.parseInt(meta.page(2).toString());
        int numberOfTroops = Integer.parseInt(meta.page(3).toString());

       return new CardInfo(cardName, elixirCost, type, troopLevel, numberOfTroops);
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
