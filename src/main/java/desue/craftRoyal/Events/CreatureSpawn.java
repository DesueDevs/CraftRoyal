package desue.craftRoyal.Events;

import desue.craftRoyal.Troops.Flying.Minion;
import desue.craftRoyal.Troops.Melee.HogRider;
import desue.craftRoyal.Troops.Melee.Knight;
import desue.craftRoyal.Troops.Melee.MiniPekka;
import desue.craftRoyal.Troops.Ranged.Musketeer;
import desue.craftRoyal.Troops.Troop;
import desue.craftRoyal.Troops.TroopKeys;
import desue.craftRoyal.Troops.TroopManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import desue.craftRoyal.CraftRoyal;

import java.util.UUID;

public class CreatureSpawn implements Listener {
    private final JavaPlugin plugin = CraftRoyal.getInstance().plugin;
    private final TroopManager troopManager = TroopManager.getInstance();
    private final TroopKeys troopKeys = TroopKeys.getInstance();
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent creatureSpawnEvent) {
        if (creatureSpawnEvent.getEntityType() != EntityType.VINDICATOR && creatureSpawnEvent.getEntityType() != EntityType.COPPER_GOLEM && creatureSpawnEvent.getEntityType() != EntityType.PILLAGER && creatureSpawnEvent.getEntityType() != EntityType.CHICKEN && creatureSpawnEvent.getEntityType() != EntityType.PIG) { // Temp test for vindicators
            return;
        }
        Entity entity = creatureSpawnEvent.getEntity();
        String TroopID = UUID.randomUUID().toString();
        entity.getPersistentDataContainer().set(
                troopKeys.isTroopKey,
                PersistentDataType.BOOLEAN,
                true
        );
        entity.getPersistentDataContainer().set(
                troopKeys.troopIDKey,
                PersistentDataType.STRING,
                TroopID
        );
        entity.setInvulnerable(true);

        // TODO: Replace the switch statement with a troop registry in the future
        Troop troop = null;
        switch (creatureSpawnEvent.getEntityType()) {
            case VINDICATOR:
                troop = new Knight(1, (Mob) creatureSpawnEvent.getEntity());
                break;
            case COPPER_GOLEM:
                troop = new MiniPekka(1, (Mob) creatureSpawnEvent.getEntity());
                break;
            case PILLAGER:
                troop = new Musketeer(1, (Mob) creatureSpawnEvent.getEntity());
                break;
            case CHICKEN:
                // Just for testing purposes
                troop = new Minion(1, (Mob) creatureSpawnEvent.getEntity());
                entity.setGravity(false);
                break;
            case PIG:
                troop = new HogRider(1, (Mob) creatureSpawnEvent.getEntity());
                break;
            default:
                entity.setCustomName("Unknown Troop");
                entity.setCustomNameVisible(true);
                break;
        }
        entity.setCustomName(troop.troopName + " [Lv. " + entity.getPersistentDataContainer().get(troopKeys.levelKey, PersistentDataType.INTEGER) + "]");
        entity.setCustomNameVisible(true);
        Troop finalTroop = troop;
        // Register the troop in TroopManager
        troopManager.addTroop(TroopID, troop);
        plugin.getLogger().info("Spawned troop: " + troop.mob.getType().toString() + " with ID " + troop.mob.getPersistentDataContainer().get(troopKeys.troopIDKey, PersistentDataType.STRING));
        new BukkitRunnable() {
            @Override
            public void run() {

                if(!(finalTroop.Targeting())) {
                    this.cancel(); // Stop the task if Targeting returns false
                }
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }
}
