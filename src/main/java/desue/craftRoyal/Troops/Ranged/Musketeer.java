package desue.craftRoyal.Troops.Ranged;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataType;

public class Musketeer extends Troop {

    public Musketeer(int level, Mob entity) {
        super(
                "Musketeer",
                entity,
                desue.craftRoyal.Mechanics.Leveling.StandardLevelMultiplier(341, level),
                FavoriteTargets.None,
                TroopTypes.Ranged, // troopType
                desue.craftRoyal.Mechanics.Leveling.StandardLevelMultiplier(102, level),
                20,
                1f,
                12.0f
        );
        target = null;
        try {
            entity.getPersistentDataContainer().set(troopKeys.nameKey, PersistentDataType.STRING, "Musketeer");
            entity.getPersistentDataContainer().set(troopKeys.levelKey, PersistentDataType.INTEGER, level);
            // Initialize HP if not present
            if (!entity.getPersistentDataContainer().has(troopKeys.hpKey, PersistentDataType.FLOAT)) {
                entity.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, this.health);
            }
        } catch (Throwable t) {
            CraftRoyal.getInstance().getLogger().warning("Musketeer: failed to set initial persistent data: " + t.getMessage());
        }
    }

    @Override
    protected void doDamageAnimation() {
        this.mob.setAI(false);
        // Need to visualize ranged attack animation/projectile here
        this.target.getWorld().spawnArrow(
                this.mob.getEyeLocation().add(this.mob.getLocation().getDirection().multiply(0.5)),
                this.mob.getLocation().getDirection(),
                2.0f,
                0.0f
        );
        this.target.playHurtAnimation(this.target.getBodyYaw());
        this.mob.getWorld().spawnParticle(
                org.bukkit.Particle.CRIT,
                this.target.getLocation().add(0,1,0),
                10,
                0.5,
                0.5,
                0.5,
                0.1
        );
    }
}
