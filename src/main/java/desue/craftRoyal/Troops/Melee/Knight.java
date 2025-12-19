package desue.craftRoyal.Troops.Melee;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Mechanics.Leveling;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataType;

import javax.inject.Inject;

public class Knight extends Troop {
    public Knight(int level, Mob entity) {
        super(
                "Knight",
                entity,
                Leveling.StandardLevelMultiplier(690f, level), // health
                FavoriteTargets.None, // favoriteTarget
                TroopTypes.Melee, // troopType
                Leveling.StandardLevelMultiplier(79f, level), // damagePerAttack
                24, // attackSpeed (ticks between attacks)
                1f, // movementSpeed (blocks per tick)
                2.25f // attackRange
        );
        target = null;
        // Set persistent data for future reference (texture packs)
        try {
            entity.getPersistentDataContainer().set(troopKeys.nameKey, PersistentDataType.STRING, "Knight");
            entity.getPersistentDataContainer().set(troopKeys.levelKey, PersistentDataType.INTEGER, level);
            // Initialize HP if not present
            if (!entity.getPersistentDataContainer().has(troopKeys.hpKey, PersistentDataType.FLOAT)) {
                entity.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, this.health);
            }
        } catch (Throwable t) {
            CraftRoyal.getInstance().getLogger().warning("Knight: failed to set initial persistent data: " + t.getMessage());
        }
    }
}
