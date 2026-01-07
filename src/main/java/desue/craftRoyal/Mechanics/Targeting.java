package desue.craftRoyal.Mechanics;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Troops.*;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

public class Targeting {
    private static final TroopKeys troopKeys = TroopKeys.getInstance();
    private static final TroopManager troopManager = TroopManager.getInstance();


    public static LivingEntity generateTowerMarker(World w, org.bukkit.Location target, String name) {
        ArmorStand marker = (ArmorStand) w.spawnEntity(target, EntityType.ARMOR_STAND);
        marker.setVisible(false);
        marker.setInvulnerable(true);
        marker.setMarker(true); // Make it a 'marker' (no hitbox)
        marker.setGravity(false);
        marker.setPersistent(true);
        marker.setCustomName(name);
        marker.setCustomNameVisible(false);

        // Initialize persistent data for HP and name so other systems (like Knights) can treat this marker as a valid target
        try {
            if (!marker.getPersistentDataContainer().has(troopKeys.hpKey, PersistentDataType.FLOAT)) {
                marker.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, 500.0f);
            }
            marker.getPersistentDataContainer().set(troopKeys.troopIDKey, PersistentDataType.STRING, name != null ? name : "TowerMarker");
            marker.getPersistentDataContainer().set(troopKeys.isBuildingKey, PersistentDataType.BOOLEAN, true);
        } catch (Throwable t) {
            CraftRoyal.getInstance().getLogger().warning("Targeting: failed to initialize tower marker persistent data: " + t.getMessage());
        }

        return marker;
    }
    /**
     * Generates an invisible, invulnerable Armor Stand marker at the specified location with the given name.
     * This marker can be used as a target for troops and other systems.
     *
     * @param w      The world where the marker will be spawned.
     * @param target The location where the marker will be placed.
     * @param name   The custom name for the marker.
     * @return The created Armor Stand marker as a LivingEntity.
     */
    public static LivingEntity generateMarker(World w, org.bukkit.Location target, String name) {
        ArmorStand marker = (ArmorStand) w.spawnEntity(target, EntityType.ARMOR_STAND);
        marker.setVisible(false);
        marker.setInvulnerable(true);
        marker.setMarker(false); // DO NOT make it a 'marker' (no hitbox) — mobs can't path to marker armor stands
        marker.setGravity(false);
        marker.setPersistent(true);
        marker.setCustomName(name);
        marker.setCustomNameVisible(false);

        // Initialize persistent data for HP and name so other systems (like Knights) can treat this marker as a valid target
        try {
            if (!marker.getPersistentDataContainer().has(troopKeys.hpKey, PersistentDataType.FLOAT)) {
                marker.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, 100.0f);
            }
            marker.getPersistentDataContainer().set(troopKeys.nameKey, PersistentDataType.STRING, name != null ? name : "Marker");
        } catch (Throwable t) {
            CraftRoyal.getInstance().getLogger().warning("Targeting: failed to initialize marker persistent data: " + t.getMessage());
        }

        return marker;
    }

    public static LivingEntity FindClosestTarget(Troop troop) {
        LivingEntity closestTarget = null;
        double closestDistanceSquared = Double.MAX_VALUE;
        LivingEntity entity = troop.mob;
        Troop.FavoriteTargets favoriteTarget = troop.favoriteTarget;
        Troop.TroopTypes troopType = troop.troopType;
        for (Entity nearbyEntity : entity.getNearbyEntities(20, 4, 20)) {
            String troopID = "";
            try {
                troopID = nearbyEntity.getPersistentDataContainer().get(troopKeys.troopIDKey, PersistentDataType.STRING);
            } catch (Throwable t) {
                // Ignore errors reading PDC
            }

            if (troopID == null || troopID.isEmpty()) {
                continue; // Skip entities that are not troops
            }

            if (favoriteTarget == Troop.FavoriteTargets.Buildings && !Boolean.TRUE.equals(nearbyEntity.getPersistentDataContainer().get(troopKeys.isBuildingKey, PersistentDataType.BOOLEAN))) {
                continue; // Skip non-building targets if FavoriteTargets is Buildings
            }

            if (troopType == Troop.TroopTypes.Melee) {
                // Melee troops ignore flying targets
               Troop potentialTarget = troopManager.getTroop(troopID);
                if (potentialTarget != null && potentialTarget.troopType == Troop.TroopTypes.Flying) {
                     continue; // Skip flying targets for melee troops
                }
            }


            double distanceSquared = entity.getLocation().distanceSquared(nearbyEntity.getLocation());
            if (distanceSquared < closestDistanceSquared) {
                closestDistanceSquared = distanceSquared;
                closestTarget = (LivingEntity) nearbyEntity;
            }
        }
        return closestTarget;
    }
}
