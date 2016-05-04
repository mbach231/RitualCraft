
package com.mbach231.ritualcraft.RitualEvent.Meta;

import com.mbach231.ritualcraft.RitualCraft;
import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Set;
import com.mbach231.ritualcraft.SuppressMagic.SuppressMagic;
import org.bukkit.event.player.PlayerInteractEvent;


public class SuppressArea extends RitualEvent {

    long duration;
    double distance;
    
    public SuppressArea(long duration, double distance) {
        this.duration = duration;
        this.distance = distance;
    }
    
    @Override
    public void executeEvent(PlayerInteractEvent event, Set<Sacrifice> sacrifices) {
        SuppressMagic.addEntry(event.getClickedBlock().getLocation(), duration, distance);
        
        //event.getPlayer().sendMessage(RitualCraft.msgColor + "Added suppress magic entry: " + duration + ", " + distance);
        RitualCraft.outputString += "Added suppress magic entry: " + duration + ", " + distance + "\n";
    }
    
}
