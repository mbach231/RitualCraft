package com.mbach231.ritualcraft.RitualEvent.Time;

import com.mbach231.ritualcraft.RitualCraft;
import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Set;

public class Daytime extends RitualEvent {

    long delaySeconds = 1;

    @Override
    public void executeEvent(final PlayerInteractEvent event, Set<Sacrifice> sacrifices) {
        World world = event.getClickedBlock().getWorld();

        if (world.getGameRuleValue("doDaylightCycle").equals("false")) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Ritual failed, time is frozen!");
            return;
        }
        /*
        while (world.getTime() < 500 || world.getTime() > 12500) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RitualCraft.getPlugin(RitualCraft.class), new Runnable() {
                @Override
                public void run() {
                    event.getClickedBlock().getWorld().setTime(event.getClickedBlock().getWorld().getTime() + 100);
                }
            }, 20 * delaySeconds);
        }
        */
        world.setTime(500);
    }
}
