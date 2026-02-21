package desue.craftRoyal.Troops.Flying;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Troops.Troop;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataType;

public class Minion extends Troop {

    public Minion(int level, Mob entity) {
        super(
                "Minion",
                entity,
                desue.craftRoyal.Mechanics.Leveling.StandardLevelMultiplier(341, level),
                FavoriteTargets.None,
                TroopTypes.Flying, // troopType
                desue.craftRoyal.Mechanics.Leveling.StandardLevelMultiplier(102, level),
                20,
                0.3f,
                5.0f
        );
        target = null;
        entity.setGravity(false);

        try {
            entity.getPersistentDataContainer().set(troopKeys.nameKey, PersistentDataType.STRING, "Minion");
            entity.getPersistentDataContainer().set(troopKeys.levelKey, PersistentDataType.INTEGER, level);
            // Initialize HP if not present
            if (!entity.getPersistentDataContainer().has(troopKeys.hpKey, PersistentDataType.FLOAT)) {
                entity.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, this.health);
            }
        } catch (Throwable t) {
            CraftRoyal.getInstance().getLogger().warning("Minion: failed to set initial persistent data: " + t.getMessage());
        }
    }

    @Override
    protected void doPathfindingToTarget() {
        this.mob.setAI(true);
        // Flying troops can ignore obstacles and move directly toward the target
        if (this.target != null) {
            this.mob.setVelocity(this.target.getLocation().add(0,2,0).toVector().subtract(this.mob.getLocation().toVector()).normalize().multiply(this.movementSpeed));
        }
    }

    @Override
    protected void doDamageAnimation() {
        this.mob.setAI(false);
        this.mob.setFreezeTicks(1);
        this.mob.setVelocity(this.mob.getVelocity().multiply(0)); // stops movement when attacking
        // Need to visualize ranged attack animation/projectile here
        this.target.getWorld().spawnArrow(
                this.mob.getLocation().add(this.mob.getLocation().getDirection().multiply(0.5)),
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
