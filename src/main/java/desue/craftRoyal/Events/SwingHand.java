package desue.craftRoyal.Events;

import desue.craftRoyal.Cards.CardInfo;
import desue.craftRoyal.Mechanics.CardUsage;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SwingHand implements Listener {
    // Listener for when the player swings their hand
    @EventHandler
    public void onSwingHand(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            if (CardUsage.isCard(event.getItem())) {
                event.setCancelled(true); // dont let player open the book
                Block block = CardUsage.findPlacementBlock(event.getPlayer());
                CardInfo cardInfo = CardUsage.getCardInfo(event.getItem());

                event.getPlayer().sendMessage("You are looking at block: " + block.getType().toString() + "at " + block.getLocation().toString());
            }
        }
    }
}
