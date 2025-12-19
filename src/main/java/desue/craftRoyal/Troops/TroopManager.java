package desue.craftRoyal.Troops;

import java.util.HashMap;

public class TroopManager {
    private static TroopManager Instance;
    public static TroopManager getInstance() {
        if (Instance == null) {
            Instance = new TroopManager();
        }
        return Instance;
    }

    private HashMap<String, Troop> activeTroops = new HashMap<>();
    // Setters
    public void addTroop(String troopID, Troop troop) {
        activeTroops.put(troopID, troop);
    }
    public void removeTroop(String troopID) {
        activeTroops.remove(troopID);
    }
    // Getters
    public Troop getTroop(String troopID) {
        return activeTroops.get(troopID);
    }
}
