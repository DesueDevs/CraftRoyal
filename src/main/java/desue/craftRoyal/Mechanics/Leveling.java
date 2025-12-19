package desue.craftRoyal.Mechanics;

public class Leveling {
    /**
     * Standard level multiplier function.
     * Increases the base value by 10% for each level.
     *
     * @param baseValue The base value to be multiplied.
     * @param level     The level to apply the multiplier for.
     * @return The modified value after applying the level multiplier.
     */
    public static float StandardLevelMultiplier(float baseValue, int level) {
        return (float) (baseValue + (baseValue * 0.1) * level);
    }
}
