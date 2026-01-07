package desue.craftRoyal.Troops.Melee;

import desue.craftRoyal.Troops.Troop;

public class HogRider extends Troop {
    public HogRider(int level, org.bukkit.entity.Mob entity) {
        super(
                "HogRider",
                entity,
                desue.craftRoyal.Mechanics.Leveling.StandardLevelMultiplier(800f, level), // health
                FavoriteTargets.Buildings, // favoriteTarget
                TroopTypes.Melee, // troopType
                desue.craftRoyal.Mechanics.Leveling.StandardLevelMultiplier(100f, level), // damagePerAttack
                20, // attackSpeed (ticks between attacks)
                1.2f, // movementSpeed (blocks per tick)
                2.0f // attackRange
        );
        target = null;
        // Set persistent data for future reference (texture packs)
        try {
            entity.getPersistentDataContainer().set(troopKeys.nameKey, org.bukkit.persistence.PersistentDataType.STRING, "HogRider");
            entity.getPersistentDataContainer().set(troopKeys.levelKey, org.bukkit.persistence.PersistentDataType.INTEGER, level);
            // Initialize HP if not present
            if (!entity.getPersistentDataContainer().has(troopKeys.hpKey, org.bukkit.persistence.PersistentDataType.FLOAT)) {
                entity.getPersistentDataContainer().set(troopKeys.hpKey, org.bukkit.persistence.PersistentDataType.FLOAT, this.health);
            }
        } catch (Throwable t) {
            desue.craftRoyal.CraftRoyal.getInstance().getLogger().warning("HogRider: failed to set initial persistent data: " + t.getMessage());
        }
    }
}
