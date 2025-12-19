package desue.craftRoyal.Troops;

import desue.craftRoyal.CraftRoyal;
import desue.craftRoyal.Mechanics.Targeting;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataType;

import java.util.logging.Logger;

public class Troop {
    protected Logger logger = CraftRoyal.getInstance().getLogger();

    public Mob mob;
    protected float health = 0;
    public FavoriteTargets favoriteTarget = FavoriteTargets.NA;
    public TroopTypes troopType = TroopTypes.NA;
    protected float damagePerAttack = 0;
    protected int attackSpeed = 0; // Ticks between attacks
    protected float movementSpeed = 0; // Blocks per tick
    protected float attackRange = 0;
    protected LivingEntity target;
    public String troopName = null; // lazy-loaded from PDC

    protected int ticksTillNextAttack = 0; // make this instance-level so different troops have independent cooldowns

    protected TroopKeys troopKeys = TroopKeys.getInstance();

    public Troop(String troopName, Mob mob, float health , FavoriteTargets favoriteTarget, TroopTypes troopType, float damagePerAttack, int attackSpeed, float movementSpeed, float attackRange) {
        this.troopName = troopName;
        this.mob = mob;
        this.health = health;
        this.favoriteTarget = favoriteTarget;
        this.troopType = troopType;
        this.damagePerAttack = damagePerAttack;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
        this.attackRange = attackRange;

        troopCreation();
    }

    /**
     * Placeholder for any additional initialization logic for subclasses
     */
    protected void troopCreation() {
        // Placeholder for any additional initialization logic
    }

    // Lazily read the troop name from the entity's PersistentDataContainer (falls back to entity type)
    private String getTroopName() {
        if (this.troopName == null || this.troopName.isEmpty()) {
            try {
                String pdcName = this.mob.getPersistentDataContainer().get(troopKeys.nameKey, PersistentDataType.STRING);
                if (pdcName != null && !pdcName.isEmpty()) {
                    this.troopName = pdcName;
                } else {
                    this.troopName = this.mob.getType().toString();
                }
            } catch (Throwable t) {
                this.troopName = this.mob.getType().toString();
            }
        }
        return this.troopName;
    }

    // If true, troop is actively targeting/attacking, If false, troop is unable to find target and will be terminated

    /**
     * Main targeting logic for the troop.
     *
     * @return boolean - true if troop is actively targeting/attacking, false if troop is unable to find target
     */
    public boolean Targeting() {
        if (!TroopIntegrityCheck()){
            //logger.info(getTroopName()+"-"+this.mob.getEntityId()+": failed integrity check.");
            return false;
        }
        if (this.target == null || this.target.isDead() || !this.target.isValid()) {
            LivingEntity newTarget = FindNewTarget();
            if (newTarget == null) {
                // No valid target found
                return false;
            }
        }
        this.mob.lookAt(this.target);
        // Target is valid, OR the function has returned false above
        if (this.mob.getLocation().distance(this.target.getLocation()) <= this.attackRange) {
            Attack();
        }else {
            // Target is valid but out of attack range, find a closer target or move towards current target
            FindNewTarget();
            if (this.target == null) {
                return false; // no valid target found
            }
            doPathfindingToTarget();
        }
        return true;
    }

    /**
     * Finds a new target for the troop using the Targeting utility.
     *
     * @return LivingEntity - the new target found, or null if no target is found
     */
    protected LivingEntity FindNewTarget() {
        LivingEntity newTarget = Targeting.FindClosestTarget(this);
        if (newTarget != null) {
            logger.info(getTroopName()+"-"+this.mob.getEntityId()+": found new target "+newTarget.getEntityId());
            this.target = newTarget;
        }
        return newTarget;
    }

    /**
     * Checks the integrity of the troop's mob entity.
     *
     * @return boolean - true if the mob is valid and alive, false otherwise
     */
    protected boolean TroopIntegrityCheck() {
        if (this.mob == null || this.mob.isDead() || !this.mob.isValid()) {
            if (this.mob != null) {
                this.mob.remove();
            }
            this.mob = null;
            return false;
        }
        return true;
    }

    /**
     * Performs the damage animation for the troop's attack.
     *
     */
    protected void doDamageAnimation() {
        this.mob.setAI(false);
        this.mob.swingMainHand();
        // Need to visualize ranged attack animation/projectile here
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

    /**
        * Performs pathfinding towards the current target.
     */
    protected void doPathfindingToTarget() {
        this.mob.lookAt(this.target);
        this.mob.setAI(true);
        this.mob.getPathfinder().moveTo(this.target, this.movementSpeed);
    }

    /**
     * Performs an attack on the current target if within range and cooldown allows.
     */
    protected void Attack() {
        if (this.ticksTillNextAttack > 0) {
            this.ticksTillNextAttack--;
            return;
        }
        this.mob.lookAt(this.target);
        this.mob.getPathfinder().stopPathfinding();
        this.mob.getPathfinder().moveTo(this.mob, 0); // stop from moving away/towards target while attacking

        // Damage Animation
        doDamageAnimation();

        // within attack range
        // Deal damage to target
        float targetHP = -1;
        try {
            if (!this.target.getPersistentDataContainer().has(troopKeys.hpKey, PersistentDataType.FLOAT)) {
                // initialize if missing
                this.target.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, 100.0f);
            }
            Float hpVal = this.target.getPersistentDataContainer().get(troopKeys.hpKey, PersistentDataType.FLOAT);
            if (hpVal == null) {
                logger.info(getTroopName()+"-"+this.mob.getEntityId()+": target has no TroopHP data (null), cannot attack.");
            }
            targetHP = hpVal;
        } catch (Throwable e) {
            logger.info(getTroopName()+"-"+this.mob.getEntityId()+": target has no TroopHP data, cannot attack.");
        }
        //Attack Target
        this.ticksTillNextAttack = this.attackSpeed;

        targetHP -= this.damagePerAttack;
        this.target.getPersistentDataContainer().set(troopKeys.hpKey, PersistentDataType.FLOAT, targetHP);
        logger.info(getTroopName()+"-"+this.mob.getEntityId()+": attacked target for "+this.damagePerAttack+" damage. Target HP now "+targetHP + ". and cooldown set to "+this.ticksTillNextAttack+" ticks.");
        if (targetHP <= 0) {
            logger.info(getTroopName()+"-"+this.mob.getEntityId()+": target("+this.target.getEntityId()+") has died, removing target.");
            this.target.remove(); // remove target if HP is 0 or below
            this.target = null; // clear target after it dies
        }
    }


    public enum FavoriteTargets {
        NA,
        None,
        Buildings
    }
    public enum TroopTypes {
        NA,
        Melee,
        Ranged,
        Flying,
        Building
    }
}
