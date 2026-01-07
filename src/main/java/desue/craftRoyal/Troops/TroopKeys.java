package desue.craftRoyal.Troops;

import desue.craftRoyal.CraftRoyal;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class TroopKeys {
    private static TroopKeys Instance;
    public static TroopKeys getInstance() {
        if (Instance == null) {
            Instance = new TroopKeys();
        }
        return Instance;
    }
    private JavaPlugin plugin = CraftRoyal.getInstance().plugin;


    public NamespacedKey hpKey = new NamespacedKey(plugin, "TroopHP");
    public NamespacedKey nameKey = new NamespacedKey(plugin, "TroopName");
    public NamespacedKey levelKey = new NamespacedKey(plugin, "TroopLevel");
    public NamespacedKey isTroopKey = new org.bukkit.NamespacedKey(plugin, "isTroop");
    public NamespacedKey troopIDKey = new org.bukkit.NamespacedKey(plugin, "troopID");
    public NamespacedKey isBuildingKey = new org.bukkit.NamespacedKey(plugin, "isBuildingKey");
}
