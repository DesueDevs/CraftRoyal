package desue.craftRoyal.Cards;

import desue.craftRoyal.Cards.Flying.Minion;
import desue.craftRoyal.Cards.Melee.HogRider;
import desue.craftRoyal.Cards.Melee.KnightCard;
import desue.craftRoyal.Cards.Melee.MiniPekka;
import desue.craftRoyal.Cards.Ranged.Musketeer;
import desue.craftRoyal.Troops.Troop;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CardManager {
        private static CardManager instance = null;

        public static CardManager getInstance() {
            if (instance == null) {
                instance = new CardManager();
            }
            return instance;
        }

        public ItemStack createCardItem(CardInfo info) {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();

            List<Component> pages = new ArrayList<>();
            pages.add(Component.text(info.elixirCost()));
            pages.add(Component.text(info.entityType().toString()));
            pages.add(Component.text(info.troopLevel()));
            pages.add(Component.text(info.numberOfTroops()));

            meta.setTitle(info.cardName());
            meta.setAuthor("CraftRoyal");
            meta.pages(pages);

            book.setItemMeta(meta);
            return book;
        }

        List<Class<? extends Card>> cardClasses = List.of(
                // Melee
                KnightCard.class,
                HogRider.class,
                MiniPekka.class,
                // Ranged
                Musketeer.class,
                // Flying
                Minion.class

        );

        public Class<? extends Card> getCardByInfo(CardInfo cardInfo) {
            for (Class<? extends Card> cardClass : cardClasses) {
                try {
                    if (cardClass.getMethod("getEntityType").invoke(null).equals(cardInfo.entityType())) {
                        return cardClass;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

//    public Book getCardItem(){
//        Collection<Component> pages = new HashSet<>();
//        pages.add(Component.text(elixerCost));
//        pages.add(Component.text(spawnedType.toString()));
//        pages.add(Component.text(spawnTroopLevel));
//        pages.add(Component.text(numberOfTroops));
//
//        return Book.book(Component.text(cardname),Component.text("CraftRoyal"), pages);
//    }
}
