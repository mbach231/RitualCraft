package com.mbach231.ritualcraft.RitualEvent.Teleport;

import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerInteractEvent;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Set;

public class TeleportBed extends RitualEvent {

    @Override
    public void executeEvent(PlayerInteractEvent event, Set<Sacrifice> sacrifices) {

        if (event.getPlayer().getBedSpawnLocation() != null) {
            event.getPlayer().teleport(event.getPlayer().getBedSpawnLocation());
        } else {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Ritual failed, cannot find bed!");
        }
    }
}
