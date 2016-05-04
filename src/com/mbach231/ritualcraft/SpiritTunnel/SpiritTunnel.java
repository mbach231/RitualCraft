
package com.mbach231.ritualcraft.SpiritTunnel;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;

public class SpiritTunnel {

    static Map<Material, Map<Material, SpiritTunnelLocationEntry>> materialMap;

    public SpiritTunnel() {
        materialMap = new HashMap();
    }

    public static void addEntry(Material small, Material medium, long duration, Location location) {

        SpiritTunnelLocationEntry locationEntry = new SpiritTunnelLocationEntry(duration, location);

        Map<Material, SpiritTunnelLocationEntry> matMap = materialMap.get(small);
        if (matMap == null) {
            matMap = new HashMap();
        }
        matMap.put(medium, locationEntry);
        materialMap.put(small, matMap);
    }

    // Return null if no valid tunnel exists
    public static Location getLocation(Material small, Material medium) {

        SpiritTunnelLocationEntry locationEntry;

        Map<Material, SpiritTunnelLocationEntry> matMap = materialMap.get(small);
        if (matMap == null) {
            return null;
        }
        
        locationEntry = matMap.get(medium);
        if (locationEntry == null) {
            return null;
        }
        // Tunnel considered valid if activated in past TIME_ACTIVE seconds
        if ((System.currentTimeMillis() - locationEntry.getTime()) / (long) 1000 < locationEntry.getDuration()) {
            return locationEntry.getLocation();
        }
        return null;
    }
}
