package com.mbach231.ritualcraft.RitualEvent.Time;

import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerInteractEvent;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Set;

public class FreezeTime extends RitualEvent {

    @Override
    public void executeEvent(PlayerInteractEvent event, Set<Sacrifice> sacrifices) {

        if (event.getClickedBlock().getWorld().getGameRuleValue("doDaylightCycle").equals("false")) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Ritual failed, time already frozen!");
            return;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doDaylightCycle false");
    }
}
