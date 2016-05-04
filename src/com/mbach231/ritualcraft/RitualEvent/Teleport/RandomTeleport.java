package com.mbach231.ritualcraft.RitualEvent.Teleport;

import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEvent;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Set;

public class RandomTeleport extends RitualEvent {

    final int TELEPORT_DISTANCE = 500;

    @Override
    public void executeEvent(PlayerInteractEvent event, Set<Sacrifice> sacrifices) {
        Location loc = event.getClickedBlock().getLocation();

        int adjustedX = (int) (Math.random() * (double) TELEPORT_DISTANCE);
        int adjustedZ = (int) Math.sqrt(Math.pow(TELEPORT_DISTANCE, 2) - Math.pow(adjustedX, 2));

        if (Math.random() > 0.5) {
            loc.setX(loc.getBlockX() + adjustedX);
        } else {
            loc.setX(loc.getBlockX() - adjustedX);
        }
        if (Math.random() > 0.5) {
            loc.setZ(loc.getBlockZ() + adjustedZ);
        } else {
            loc.setZ(loc.getBlockZ() - adjustedZ);
        }
        
        loc.setY(loc.getWorld().getHighestBlockYAt(loc));      
        event.getPlayer().teleport(loc);
    }
}
