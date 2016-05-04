package com.mbach231.ritualcraft;

import com.mbach231.ritualcraft.Ritual.RitualDetection;
import com.mbach231.ritualcraft.Ritual.ValidRituals;
import com.mbach231.ritualcraft.SuppressMagic.SuppressMagic;
import com.mbach231.ritualcraft.SpiritTunnel.SpiritTunnel;
import com.mbach231.ritualcraft.History.RitualHistory;
import com.mbach231.ritualcraft.Sacrifices.SacrificeDetection;
import com.mbach231.ritualcraft.Circles.CircleMaterials;
import com.mbach231.ritualcraft.Circles.RitualCircleRelativeLocation;
import com.mbach231.ritualcraft.Circles.RitualCircleDetection;
import com.mbach231.ritualcraft.MagicItems.MagicItems;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.v1_7_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RitualCraft extends JavaPlugin implements Listener {

    ValidRituals validRituals;
    Material validCenterMaterial;
    CircleMaterials validCircleMaterials;
    RitualCircleRelativeLocation validCircleRelativeLocations;

    RitualDetection ritualDetection;
    RitualCircleDetection circleDetection;
    SacrificeDetection sacrificeDetection;

    SpiritTunnel spiritTunnel;
    RitualHistory ritualHistory;
    SuppressMagic suppressMagic;

    Map<Location, Long> ritualLocationMap;

    public static String outputString = "";
    long delaySeconds = 1;
    public static ChatColor msgColor;

    @Override
    public void onEnable() {
        ritualDetection = new RitualDetection();
        validCenterMaterial = Material.REDSTONE_BLOCK;
        spiritTunnel = new SpiritTunnel();
        ritualHistory = new RitualHistory();
        suppressMagic = new SuppressMagic();
        msgColor = ChatColor.GOLD;
        ritualLocationMap = new ConcurrentHashMap();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("ritualbook")) {

            if (!(sender instanceof Player)) {
                return false;
            }

            Player player = (Player) sender;
            player.getInventory().addItem(MagicItems.getRitualBook());
        }

        return true;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(final PlayerInteractEvent event) {

        // If right-clicked valid center block, may be activating ritual, ignore other cases
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getClickedBlock().getType() != validCenterMaterial) {
            return;
        }

        // If the last time a ritual was performed here was less than 1 second ago, cancel click.
        // This is to ensure players cannot activate a ritual multiple times with one set of sacrifices.
        if (ritualLocationMap.containsKey(event.getClickedBlock().getLocation())) {
            if (ritualLocationMap.get(event.getClickedBlock().getLocation()) + 1000 > System.currentTimeMillis()) {
                return;
            }
        }

        Location eventLocation = event.getClickedBlock().getLocation();
        eventLocation.setY(eventLocation.getY() + 1);
        final Location loc = eventLocation;

        boolean foundRitual = ritualDetection.detectRitual(event);

        // If true, ritual found
        if (foundRitual) {
            // Log the time this ritual was activated at this location
            ritualLocationMap.put(event.getClickedBlock().getLocation(), System.currentTimeMillis());

            // Remove sacrifices for the ritual
            ritualDetection.removeSacrifices();

            for (Sacrifice sacrifice : ritualDetection.getSacrifices()) {
                if (sacrifice.getMaterial() != null) {
                    getLogger().info(sacrifice.getMaterial().toString() + "\n");
                }
                if (sacrifice.getCreatureType()!= null) {
                    getLogger().info(sacrifice.getCreatureType().toString() + "\n");
                }
            }

            event.getClickedBlock().getWorld().playSound(eventLocation, Sound.FUSE, 2, 0);
            createHelix(event.getClickedBlock().getLocation());
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    ritualDetection.executeRitual(event);
                    RitualHistory.addEntry(ritualDetection.getRitualType(), loc, event.getPlayer().getName());

                    /*
                     // If ritual did not complete, refund sacrifices
                     if (ritualDetection.executeRitual(event) == false) {
                     //event.getPlayer().sendMessage(ChatColor.GOLD + "Unable to complete ritual!");
                     ritualDetection.refundSacrifices(loc);
                     } else {
                     RitualHistory.addEntry(ritualDetection.getRitualType(), loc, event.getPlayer().getName());
                     }*/
                }
            }, 20 * delaySeconds);

        }
        //getLogger().info(validRituals.validString);
        getLogger().info(outputString);
        outputString = "";

    }

    public void createHelix(Location loc) {
        double radius = 1.5;
        for (double y = 0; y <= 50; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            //radius += 0.2;
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles("fireworksSpark", (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            for (Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }
}
