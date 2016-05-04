
package com.mbach231.ritualcraft.SuppressMagic;

import org.bukkit.Location;


public class SuppressMagicEntry {
    private Location location;
    private long time;
    private long duration;
    private double distance;
    
    SuppressMagicEntry(Location location, long time, long duration, double distance) {
        this.location = location;
        this.time = time;
        this.duration = duration;
        this.distance = distance;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public long getTime() {
        return time;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public double getDistance() {
        return distance;
    }
}
