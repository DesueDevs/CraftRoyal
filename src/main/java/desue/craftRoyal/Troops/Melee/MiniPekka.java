package desue.craftRoyal.Troops.Melee;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Mechanics.Leveling;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataType;

public class MiniPekka extends Troop {
    public MiniPekka(int level, Mob entity) {
        super(
                "MiniPekka",
                entity,
                Leveling.StandardLevelMultiplier(520f, level), // health
                FavoriteTargets.None, // favoriteTarget
                TroopTypes.Melee, // troopType
                Leveling.StandardLevelMultiplier(260f, level), // damagePerAttack
                32, // attackSpeed (ticks between attacks)
                1.5f, // movementSpeed (blocks per tick)
                2.2f // attackRange
        );
        target = null;
        try {;
            entity.getPersistentDataContainer().set(troopKeys.nameKey, PersistentDataType.STRING, "MiniPekka");
            entity.getPersistentDataContainer().set(troopKeys.levelKey, PersistentDataType.INTEGER, level);
            // Initialize HP if not present
            if (!entity.getPersistentDataContainer().has(troopKeys.hpKey, PersistentDataType.FLOAT)) {
                entity.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, this.health);
            }
        } catch (Throwable t) {
            CraftRoyal.getInstance().getLogger().warning("MiniPekka: failed to set initial persistent data: " + t.getMessage());
        }
    }
}
