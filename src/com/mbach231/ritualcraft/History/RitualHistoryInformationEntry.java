
package com.mbach231.ritualcraft.History;

import com.mbach231.ritualcraft.Ritual.ValidRituals;
import org.bukkit.Location;


public class RitualHistoryInformationEntry {
    
    private long time;
    private ValidRituals.RitualTypeEn ritualType;
    private Location ritualLocation;
    private String playerName;
    
    
    RitualHistoryInformationEntry(long time, ValidRituals.RitualTypeEn ritualType, Location ritualLocation, String playerName) {
        this.time = time;
        this.ritualType = ritualType;
        this.ritualLocation = ritualLocation;
        this.playerName = playerName;
    }
    
    public long getTime() {
        return time;
    }
    
    public ValidRituals.RitualTypeEn getRitualType() {
        return ritualType;
    }
    
    public Location getRitualLocation() {
        return ritualLocation;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
}
