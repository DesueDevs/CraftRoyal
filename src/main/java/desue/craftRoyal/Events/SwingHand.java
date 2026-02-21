package desue.craftRoyal.Events;

import desue.craftRoyal.Cards.Card;
import desue.craftRoyal.Cards.CardInfo;
import desue.craftRoyal.Cards.CardManager;
import desue.craftRoyal.Mechanics.CardUsage;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.lang.reflect.Constructor;

public class SwingHand implements Listener {
    // Listener for when the player swings their hand
    @EventHandler
    public void onSwingHand(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            if (CardUsage.isCard(event.getItem())) {
                event.setCancelled(true); // dont let player open the book
                Location location = CardUsage.findPlacementBlock(event.getPlayer()).getLocation();
                CardInfo cardInfo = CardUsage.getCardInfo(event.getItem());

                if (location != null && cardInfo != null) {
                    Constructor<? extends Card> cardConstructor;
                    try {
                        cardConstructor = CardManager.getInstance().getCardByInfo(cardInfo).getConstructor(Player.class, int.class, int.class, int.class, Location.class);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    if (cardConstructor != null) {
                        try {
                            Card card = cardConstructor.newInstance(event.getPlayer(), cardInfo.elixirCost(), cardInfo.troopLevel(), cardInfo.numberOfTroops(), location);
                            event.getPlayer().setCooldown(event.getItem(), 100); // 5 second cooldown
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
