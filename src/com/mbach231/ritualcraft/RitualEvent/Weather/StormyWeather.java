package com.mbach231.ritualcraft.RitualEvent.Weather;

import com.mbach231.ritualcraft.RitualCraft;
import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Set;

public class StormyWeather extends RitualEvent {

    @Override
    public void executeEvent(PlayerInteractEvent event, Set<Sacrifice> sacrifices) {

        //If already storming, cancel
        if (event.getClickedBlock().getWorld().hasStorm()) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Ritual failed, weather is already stormy!");
            return;
        }
        event.getClickedBlock().getWorld().setStorm(true);
        // 10 minute storm
        event.getClickedBlock().getWorld().setWeatherDuration(20 * 60 * 10);
        for(Player player : event.getClickedBlock().getWorld().getPlayers()) {
            player.sendMessage(RitualCraft.msgColor + "A storm quickly approaches!");
        }
    }
}
