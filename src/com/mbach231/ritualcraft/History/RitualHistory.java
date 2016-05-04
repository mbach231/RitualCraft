package com.mbach231.ritualcraft.History;

import com.mbach231.ritualcraft.Ritual.ValidRituals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;

public class RitualHistory {

    private static Map<Long, RitualHistoryInformationEntry> ritualHistory;
    // 7 days
    //private static long MAX_DETECTION_TIME = 604800000;
    
    // 30 days in seconds
    private static long MAX_DETECTION_TIME = 2592000;
    
    
    
    public RitualHistory() {
        ritualHistory = new HashMap();
    }

    public static List<RitualHistoryInformationEntry> getHistory(long msRange, double proximityRange, Location historyLocation) {
        List<RitualHistoryInformationEntry> history = new ArrayList();
        long currentTime = System.currentTimeMillis();
        Map<Long, RitualHistoryInformationEntry> tempMap = ritualHistory;

        for (Map.Entry<Long, RitualHistoryInformationEntry> entry : tempMap.entrySet()) {

            // IF older than can ever be checked, remove from history
            if (entry.getKey() < (currentTime / 1000 - MAX_DETECTION_TIME)) {
                ritualHistory.remove(entry.getKey());
            } // IF within correct time range
            else if (entry.getKey() <= (currentTime - msRange)) {
                
                
                // IF within range, add entry to 
                if (proximityRange == 0
                        || entry.getValue().getRitualLocation().distance(historyLocation) < proximityRange) {
                    history.add(entry.getValue());
                }
            }
        }
        return history;
    }
    
    public static void addEntry(ValidRituals.RitualTypeEn ritualType, Location location, String name) {
        long currentTime = System.currentTimeMillis();
        RitualHistoryInformationEntry entry = new RitualHistoryInformationEntry(currentTime, ritualType, location, name);
        ritualHistory.put(currentTime, entry);
    }
}
