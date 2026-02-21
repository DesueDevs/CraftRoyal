package desue.craftRoyal;

import desue.craftRoyal.Cards.Card;
import desue.craftRoyal.Cards.CardInfo;
import desue.craftRoyal.Cards.CardManager;
import desue.craftRoyal.Events.SwingHand;
import desue.craftRoyal.Mechanics.Targeting;
import desue.craftRoyal.Events.CreatureSpawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Level;

public final class CraftRoyal extends JavaPlugin {
    private static CraftRoyal Instance;
    public static CraftRoyal getInstance() {
        return Instance;
    }


    public final JavaPlugin plugin = this;


    @Override
    public void onEnable() {
        // set the singleton instance so other classes can access the plugin
        Instance = this;

        // Plugin startup logic
        // Initialize CreatureSpawn listener (registers itself in constructor)
//        try {
//            this.getServer().getPluginManager().registerEvents(new CreatureSpawn(), plugin);
//        } catch (Throwable t) {
//            getLogger().log(Level.SEVERE, "Failed to register CreatureSpawn listener", t);
//        }
        try {
            this.getServer().getPluginManager().registerEvents(new SwingHand(), plugin);
        } catch (Throwable t) {
            getLogger().log(Level.SEVERE, "Failed during plugin initialization", t);
        }

        getLogger().info("CraftRoyal enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("CraftRoyal disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("getTroopCard")) {
            CardInfo cardInfo = null;
            switch (args[0].toLowerCase()) {
                case "knight":
                    cardInfo = new CardInfo("Knight Card", 3, EntityType.VINDICATOR, 1, 1);
                    break;
                case "musketeer":
                    cardInfo = new CardInfo("Musketeer Card", 4, EntityType.PILLAGER, 1, 1);
                    break;
                case "minipekka":
                    cardInfo = new CardInfo("MiniPekka Card", 4, EntityType.COPPER_GOLEM, 1, 1);
                    break;
                case "hogrider":
                    cardInfo = new CardInfo("Hog Rider Card", 4, EntityType.PIG, 1, 1);
                    break;
                case "minion":
                    cardInfo = new CardInfo("Minion Card", 3, EntityType.CHICKEN, 1, 1);
                    break;

                default:
                    sender.sendMessage("Unknown troop type: " + args[0]);
            }
            Player player = (Player) sender;
            player.give(CardManager.getInstance().createCardItem(cardInfo));
            return true;
        }
        if (command.getName().equalsIgnoreCase("summonTarget")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command may only be run by a player.");
                return true;
            }
            Player player = (Player) sender;
            // spawn marker at player's current location
            LivingEntity target = Targeting.generateMarker(player.getWorld(), player.getLocation(), "TestTarget-" + Math.round(Math.random() * 100));
            target.setInvisible(false); // make visible for testing
            target.getPersistentDataContainer().set(
                    new org.bukkit.NamespacedKey(plugin, "troopID"),
                    PersistentDataType.STRING,
                    UUID.randomUUID().toString()
            );
            player.sendMessage("Target marker spawned at your location.");
            return true;
        }else if (command.getName().equalsIgnoreCase("spawnTower")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command may only be run by a player.");
                return true;
            }
            Player player = (Player) sender;
            LivingEntity target = Targeting.generateTowerMarker(player.getWorld(), player.getLocation(), "TowerMarker-" + Math.round(Math.random() * 100));
            target.setInvisible(false); // make visible for testing
            target.getPersistentDataContainer().set(
                    new org.bukkit.NamespacedKey(plugin, "troopID"),
                    PersistentDataType.STRING,
                    UUID.randomUUID().toString()
            );

            sender.sendMessage("Spawned a tower at current location.");
            return true;
        }
        return false;
    }
}
