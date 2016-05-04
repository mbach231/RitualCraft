package com.mbach231.ritualcraft.SuppressMagic;

import com.mbach231.ritualcraft.RitualCraft;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class SuppressMagic {

    private static List<SuppressMagicEntry> suppressMagic;
    private static long MAX_SUPPRESSION_TIME;

    public SuppressMagic() {
        suppressMagic = new ArrayList();
        MAX_SUPPRESSION_TIME = 1000 * 60 * 60 * 24 * 7;
    }

    public static void purgeOldEntries() {
        List<SuppressMagicEntry> tempList = suppressMagic;
        long currentTime = System.currentTimeMillis();

        for (SuppressMagicEntry entry : tempList) {

            // IF duration exceeds max suppression time, remove from list
            if (entry.getDuration() - currentTime > MAX_SUPPRESSION_TIME) {
                suppressMagic.remove(entry);
            } else {
                // Because it's a list, when first valid entry is found, all other entries are valid
                break;
            }
        }
    }

    public static void addEntry(Location location, long duration, double distance) {
        long currentTime = System.currentTimeMillis();
        SuppressMagicEntry entry = new SuppressMagicEntry(location, currentTime, duration, distance);
        suppressMagic.add(entry);
    }

    public static boolean areaSuppressed(Location location) {

        purgeOldEntries();

        long currentTime = System.currentTimeMillis();

        if (suppressMagic.isEmpty()) {
            RitualCraft.outputString += "suppressMagic is empty!\n";
        }

        for (SuppressMagicEntry entry : suppressMagic) {
            /*
            RitualCraft.outputString += entry.getDuration() + "\n";
            RitualCraft.outputString += entry.getDistance() + "\n";
            RitualCraft.outputString += entry.getTime() + "\n";
            RitualCraft.outputString += location.distance(entry.getLocation()) + "\n";
            */
            if (currentTime - entry.getTime() < entry.getDuration()) {
                RitualCraft.outputString += "currentTime - entry.getTime() < entry.getDuration()";
            }

            if (entry.getDistance() > location.distance(entry.getLocation())) {
                RitualCraft.outputString += "entry.getDistance() < location.distance(entry.getLocation())";
            }


            // IF within correct time span and within proximity, suppression occurring
            if (currentTime - entry.getTime() < entry.getDuration()
                    && entry.getDistance() > location.distance(entry.getLocation())) {
                return true;
            }
        }
        return false;
    }
}
