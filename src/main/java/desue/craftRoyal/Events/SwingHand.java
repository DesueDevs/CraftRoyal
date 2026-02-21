package desue.craftRoyal.Events;

import desue.craftRoyal.Cards.Card;
import desue.craftRoyal.Mechanics.UseCard;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SwingHand implements Listener {
    // Listener for when the player swings their hand
    @EventHandler
    public void onSwingHand(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            if (UseCard.isCard(event.getItem())) {
                event.setCancelled(true); // dont let player open the book
                Block block = UseCard.findPlacementBlock(event.getPlayer());

                event.getPlayer().sendMessage("You are looking at block: " + block.getType().toString() + "at " + block.getLocation().toString());
            }
        }
    }
}
