package desue.craftRoyal.Cards;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Troops.Troop;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Card {
    protected Logger logger = CraftRoyal.getInstance().getLogger();

    protected LinkedList<Entity> entities = null;

    protected Class<? extends Troop> troopClass;
    protected String cardname = "";
    protected Player player = null;
    protected int elixerCost = 0;
    protected EntityType spawnedType = null;
    protected int spawnTroopLevel = 0;
    protected int numberOfTroops = 0;
    public Card(String cardName,Player player, int elixerCost, EntityType spawnedType, int spawnTroopLevel, int numberOfTroops, Class<? extends Troop> troopClass) {
        this.cardname = cardName;
        this.player = player;
        this.elixerCost = elixerCost;
        this.spawnedType = spawnedType;
        this.spawnTroopLevel = spawnTroopLevel;
        this.numberOfTroops = numberOfTroops;

        this.troopClass = troopClass;
    }

    public Book getCardItem(){
        Collection<Component> pages = new HashSet<>();
        pages.add(Component.text(elixerCost));
        pages.add(Component.text(spawnedType.toString()));
        pages.add(Component.text(spawnTroopLevel));
        pages.add(Component.text(numberOfTroops));

        return Book.book(Component.text(cardname),Component.text("CraftRoyal"), pages);
    }

    protected void SpawnTroops(Location SpawnLoc) {
        // Basic Validations
        if (this.player == null) {
            logger.warning("Card: SpawnTroops called but player is null");
            return;
        }
        if (this.spawnedType == null) {
            logger.warning("Card: SpawnTroops called but spawnedType is null");
            return;
        }
        if (this.numberOfTroops <= 0) {
            logger.warning("Card: SpawnTroops called but numberOfTroops is non-positive");
            return;
        }
        if (SpawnLoc == null) {
            logger.warning("Card: SpawnTroops called but SpawnLoc is null");
            return;
        }
        if (this.spawnTroopLevel <= 0) {
            logger.warning("Card: SpawnTroops called but spawnTroopLevel is non-positive");
            return;
        }

        // Elixer Check
        if (this.player.getExpToLevel() < this.elixerCost) {
            this.player.sendMessage("Not enough elixer to play this card!");
            return;
        }else {
            // Deduct Elixer
            this.player.setLevel(this.player.getExpToLevel() - this.elixerCost);
        }

        // Summon troops
        Block spawnBlock = SpawnLoc.getWorld().getBlockAt(SpawnLoc);
        // Spawn Flying troops in air
        int heightInc = 0;
//        if(this.spawnedTroop.troopType == Troop.TroopTypes.Flying) {
//            heightInc = 2;
//        }
        for (int i = 0; i < this.numberOfTroops; i++) {
            entities.push(SpawnLoc.getWorld().spawnEntity(spawnBlock.getLocation().add(0,heightInc,0), this.spawnedType));
        }

        if (troopClass != null){
            try {
                Constructor<? extends Troop> constructor = troopClass.getConstructor(int.class, Mob.class);
                for (Entity e : entities) {
                    constructor.newInstance(spawnTroopLevel, e);
                }
            } catch (RuntimeException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e); // TODO: make this not such an ugly try catch
            }
        }
    }
}
