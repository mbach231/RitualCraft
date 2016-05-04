
package com.mbach231.ritualcraft.SpiritTunnel;

import org.bukkit.Location;


public class SpiritTunnelLocationEntry {
    
    private long time;
    long duration;
    private Location location;
    
    SpiritTunnelLocationEntry(long duration, Location location) {
        this.duration = duration;
        time = System.currentTimeMillis();
        this.location = location;
    }
    
    long getDuration() {
        return duration;
    }
    
    long getTime() {
        return time;
    }
    
    Location getLocation() {
        return location;
    }
    
}
