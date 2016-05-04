
package com.mbach231.ritualcraft.RitualEvent;

import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Set;
import org.bukkit.event.player.PlayerInteractEvent;


public abstract class RitualEvent {
    
    abstract public void executeEvent(PlayerInteractEvent event, Set<Sacrifice> sacrifices);
    
}
