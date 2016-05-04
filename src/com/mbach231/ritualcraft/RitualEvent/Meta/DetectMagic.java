package com.mbach231.ritualcraft.RitualEvent.Meta;

import com.mbach231.ritualcraft.RitualCraft;
import com.mbach231.ritualcraft.History.RitualHistory;
import com.mbach231.ritualcraft.History.RitualHistoryInformationEntry;
import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.bukkit.event.player.PlayerInteractEvent;

public class DetectMagic extends RitualEvent {

    private long msRange;
    private double proximityRange;

    public DetectMagic(long msRange, double proximityRange) {
        this.msRange = msRange;
        this.proximityRange = proximityRange;
    }

    @Override
    public void executeEvent(PlayerInteractEvent event, Set<Sacrifice> sacrifices) {

        List<RitualHistoryInformationEntry> historyList = RitualHistory.getHistory(msRange, proximityRange, event.getClickedBlock().getLocation());

        if (historyList.isEmpty()) {
            event.getPlayer().sendMessage(RitualCraft.msgColor + "No rituals found!");
        } else {
            //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm");
            String ritualEventStr = "";
            Date date;
            for (RitualHistoryInformationEntry entry : historyList) {
                date = new Date(entry.getTime());
                ritualEventStr = sdf.format(date) + ": ";
                ritualEventStr += entry.getPlayerName() + " cast ";
                ritualEventStr += entry.getRitualType().toString() + " at ";
                ritualEventStr += (int)entry.getRitualLocation().getX() + ", " + (int)entry.getRitualLocation().getY() + ", " + (int)entry.getRitualLocation().getZ();
                event.getPlayer().sendMessage(RitualCraft.msgColor + ritualEventStr);
            }
        }
    }
}
