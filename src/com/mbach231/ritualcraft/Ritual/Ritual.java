package com.mbach231.ritualcraft.Ritual;

import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import com.mbach231.ritualcraft.Circles.RitualCircle;
import com.mbach231.ritualcraft.RitualEvent.RitualEvent;
import java.util.HashMap;
import com.mbach231.ritualcraft.Circles.CircleSizes.*;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.event.player.PlayerInteractEvent;

public class Ritual {

    private Map<CircleSizeEn, RitualCircle> requiredCircles;
    private Set<Sacrifice> requiredSacrifices;
    private RitualEvent ritualEvent;
    private Set<Biome> requiredBiomes;
    private Set<TimeRangeEntry> requiredTime;
    private int requiredMoonPhase;
    private int requiredNumPlayers;

    public Ritual(RitualCircle smallRitualCircle,
            RitualCircle mediumRitualCircle,
            RitualCircle largeRitualCircle,
            Set<Sacrifice> requiredSacrifices,
            Set<Biome> requiredBiomes,
            Set<TimeRangeEntry> requiredTime,
            int requiredMoonPhase,
            int requiredNumPlayers,
            RitualEvent ritualEvent) {

        requiredCircles = new HashMap();
        requiredCircles.put(CircleSizeEn.SMALL, smallRitualCircle);
        requiredCircles.put(CircleSizeEn.MEDIUM, mediumRitualCircle);
        requiredCircles.put(CircleSizeEn.LARGE, largeRitualCircle);
        this.requiredSacrifices = requiredSacrifices;
        this.requiredBiomes = requiredBiomes;
        this.requiredTime = requiredTime;
        this.requiredMoonPhase = requiredMoonPhase;
        this.requiredNumPlayers = requiredNumPlayers;
        this.ritualEvent = ritualEvent;
    }

    public Ritual(Map<CircleSizeEn, RitualCircle> requiredCircles,
            Set<Sacrifice> requiredSacrifices,
            Set<Biome> requiredBiomes,
            Set<TimeRangeEntry> requiredTime,
            int requiredMoonPhase,
            int requiredNumPlayers,
            RitualEvent ritualEvent) {

        this.requiredCircles = requiredCircles;
        this.requiredSacrifices = requiredSacrifices;
        this.requiredBiomes = requiredBiomes;
        this.requiredTime = requiredTime;
        this.requiredMoonPhase = requiredMoonPhase;
        this.requiredNumPlayers = requiredNumPlayers;
        this.ritualEvent = ritualEvent;
    }

    public boolean usesCircles(Map<CircleSizeEn, RitualCircle> circles) {
        
        for (Map.Entry<CircleSizeEn, RitualCircle> entry : circles.entrySet()) {
            // If this circle isn't set, ignore what is set there (if anything)
            if (requiredCircles.get(entry.getKey()) == null) {
                continue;
            }
            if (!requiredCircles.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean usesSacrifices(Set<Sacrifice> sacrifices) {

        if (sacrifices.isEmpty()) {
            return false;
        }

        if (sacrifices.size() != requiredSacrifices.size()) {
            return false;
        }

        for (Sacrifice foundSacrifice : sacrifices) {
            if (!requiredSacrifices.contains(foundSacrifice)) {
                return false;
            }
        }
        return true;
    }

    public boolean castableInBiome(Biome biome) {

        // If empty, all biomes valid
        if (requiredBiomes.isEmpty()) {
            return true;
        }

        return requiredBiomes.contains(biome);
    }

    public boolean castableInMoonPhase(int phase) {

        // If -1, all moon phases
        if (requiredMoonPhase == MoonPhase.NO_PHASE) {
            return true;
        }

        return requiredMoonPhase == phase;

    }

    public boolean castableAtTime(long time) {

        // If empty, all times valid
        if (requiredTime.isEmpty()) {
            return true;
        }

        for (TimeRangeEntry entry : requiredTime) {

            if (entry.getStartTime() < entry.getEndTime()) {

                if (time > entry.getStartTime() && time < entry.getEndTime()) {
                    return true;
                }
            } else {
                if (time > entry.getStartTime() || time < entry.getEndTime()) {
                    return true;
                }
            }
        }

        return false;
    }
    
    public boolean castableWithPlayerCount(int numPlayersPresent) {
        return numPlayersPresent >= requiredNumPlayers;
    }

    public RitualEvent getRitualEvent() {
        return ritualEvent;
    }

    public void execute(PlayerInteractEvent event, Set<Sacrifice> sacrifices) {
        ritualEvent.executeEvent(event,sacrifices);
    }

    public Set<Sacrifice> getRequiredSacrifices() {
        return requiredSacrifices;
    }

    public String getRitualString() {
        String string = ChatColor.BLACK + "";

        if (requiredCircles.containsKey(CircleSizeEn.SMALL)) {
            string += "S: " + adjStr(requiredCircles.get(CircleSizeEn.SMALL).getCircleMaterial().toString()) + "\n";
        }
        if (requiredCircles.containsKey(CircleSizeEn.MEDIUM)) {
            string += "M: " + adjStr(requiredCircles.get(CircleSizeEn.MEDIUM).getCircleMaterial().toString()) + "\n";
        }
        if (requiredCircles.containsKey(CircleSizeEn.LARGE)) {
            string += "L: " + adjStr(requiredCircles.get(CircleSizeEn.LARGE).getCircleMaterial().toString()) + "\n";
        }

        string += "Sacrifices:\n";
        for (Sacrifice sacrifice : requiredSacrifices) {
            if (sacrifice.isItem()) {
                string += "-" + adjStr(sacrifice.getMaterial().toString()) + " x" + sacrifice.getAmount() + "\n";
            } else if(sacrifice.isCreature()) {
                string += "-" + adjStr(sacrifice.getCreatureType().toString()) + " x" + sacrifice.getAmount() + "\n";
            } 
        }
        
        if(requiredNumPlayers > 1) {
            string += "# Players Needed: " + requiredNumPlayers + "\n";
        }

        if (!requiredBiomes.isEmpty()) {
            //string += "Required Biomes:\n";
            for (Biome biome : requiredBiomes) {
                string += "Biome: " + adjStr(biome.toString()) + "\n";
            }
        }

        if (!requiredTime.isEmpty()) {
            //string += "Required Times:\n";
            for (TimeRangeEntry timeEntry : requiredTime) {
                string += "Time: " + timeEntry.getStartTime() + " - " + timeEntry.getEndTime() + "\n";
            }
        }

        if (requiredMoonPhase != MoonPhase.NO_PHASE) {
            string += "Phase: " + adjStr(MoonPhase.getMoonPhaseEn(requiredMoonPhase).toString()) + "\n";
        }

        return string;
    }
    
    private String adjStr(String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
    }
}
