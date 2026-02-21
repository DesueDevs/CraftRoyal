package desue.craftRoyal.Mechanics;

import desue.craftRoyal.Cards.CardInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
            System.out.println("Not a book");
            return false;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        if (meta.author().contains(Component.text("CraftRoyal"))) {
            System.out.println("Author: " + meta.author().toString());
            return false;
        }
        System.out.println("Is a card");
        return true;
    }

    public static CardInfo getCardInfo(ItemStack item) {
        if (!isCard(item)) {
            return null;
        }
        BookMeta meta = (BookMeta) item.getItemMeta();
        String cardName = meta.title().toString();
        int elixirCost = Integer.parseInt(plainSerializer(meta.page(1)));
        EntityType type = EntityType.valueOf(plainSerializer(meta.page(2)));
        int troopLevel = Integer.parseInt(plainSerializer(meta.page(3)));
        int numberOfTroops = Integer.parseInt(plainSerializer(meta.page(4)));

       return new CardInfo(cardName, elixirCost, type, troopLevel, numberOfTroops);
    }

    private static String plainSerializer(Component component) {
        return ((TextComponent) component).content();
    }

    /**
        * Finds the block a player is looking at within a 20-block range.
        * @param player The player whose line of sight is being traced.
        * @return The block the player is looking at, or null if no block is found
    */
    @Nullable
    public static Block findPlacementBlock(Player player) {
        return player.rayTraceBlocks(20).getHitBlock();
    }
}
