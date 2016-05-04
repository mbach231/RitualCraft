package com.mbach231.ritualcraft.Ritual;

import com.mbach231.ritualcraft.Sacrifices.SacrificeDetection;
import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import com.mbach231.ritualcraft.Circles.CircleMaterials;
import com.mbach231.ritualcraft.Circles.RitualCircleRelativeLocation;
import com.mbach231.ritualcraft.Circles.CircleSizes;
import com.mbach231.ritualcraft.Circles.RitualCircle;
import com.mbach231.ritualcraft.Circles.RitualCircleDetection;
import com.mbach231.ritualcraft.RitualCraft;
import static com.mbach231.ritualcraft.RitualCraft.msgColor;
import com.mbach231.ritualcraft.SuppressMagic.SuppressMagic;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RitualDetection {

    private final int DETECTION_RADIUS = CircleSizes.PILLAR_RADIUS;
    CircleMaterials validCircleMaterials;
    RitualCircleRelativeLocation validCircleRelativeLocations;
    ValidRituals validRituals;
    RitualCircleDetection circleDetection;
    SacrificeDetection sacrificeDetection;
    public String detectionString = "";
    public ValidRituals.RitualTypeEn foundRitualType;
    public static Map<CircleSizes.CircleSizeEn, RitualCircle> detectedCircles;

    public RitualDetection() {
        validCircleRelativeLocations = new RitualCircleRelativeLocation();
        validCircleMaterials = new CircleMaterials();

        circleDetection = new RitualCircleDetection(validCircleMaterials.getValidCircleMaterials(),
                validCircleRelativeLocations);

        sacrificeDetection = new SacrificeDetection();

        validRituals = new ValidRituals();
    }

    public boolean detectRitual(PlayerInteractEvent event) {
        detectionString = "";
        circleDetection.detect(event.getClickedBlock());
        sacrificeDetection.detect(event.getClickedBlock());

        foundRitualType = validRituals.getRitualType(circleDetection.getDetectedCircles(),
                sacrificeDetection.getSacrifices());


        detectionString += validRituals.validString;


        // If circles and sacrifices indicate player is trying to cast a ritual,
        // check the other requirements necessary to cast the ritual.
        if (foundRitualType != null) {
            Ritual foundRitual = validRituals.getRitual(foundRitualType);

            // Check if area is being suppressed, if so, cancel ritual
            if (SuppressMagic.areaSuppressed(event.getClickedBlock().getLocation())) {
                event.getPlayer().sendMessage(msgColor + "Magic is being suppressed in this area!");
                return false;
            }

            if (foundRitual.castableWithPlayerCount(getNumPlayersInCircle(event.getClickedBlock().getLocation())) == false) {
                event.getPlayer().sendMessage(RitualCraft.msgColor + "You need more players present to cast this ritual!");
                return false;
            }

            // If not castable in biome
            if (foundRitual.castableInBiome(event.getClickedBlock().getBiome()) == false) {
                event.getPlayer().sendMessage(RitualCraft.msgColor + "Cannot cast this ritual in this biome!");
                return false;
            }

            // If not castable in this moon phase
            if (foundRitual.castableInMoonPhase((int) (event.getClickedBlock().getWorld().getFullTime() / 24000) % 8) == false) {
                event.getPlayer().sendMessage(RitualCraft.msgColor + "Cannot cast this ritual with this phase of the moon!");
                return false;
            }

            // If not castable at this IG time
            if (foundRitual.castableAtTime(event.getClickedBlock().getWorld().getTime()) == false) {
                event.getPlayer().sendMessage(RitualCraft.msgColor + "Cannot cast this ritual at this hour!");
                return false;
            }


            RitualCraft.outputString += "Found valid ritual!\n";
            detectedCircles = circleDetection.getDetectedCircles();
            return true;
        }
        RitualCraft.outputString += "No valid ritual found!\n";
        return false;
    }

    public void removeSacrifices() {
        sacrificeDetection.purgeDetectedEntities(validRituals.getRitual(foundRitualType).getRequiredSacrifices());
    }
    
    public Set<Sacrifice> getSacrifices() {
        return sacrificeDetection.getSacrifices();
    }

    public void executeRitual(PlayerInteractEvent event) {
        Ritual ritual = validRituals.getRitual(foundRitualType);
        if(ritual != null) {
            ritual.execute(event, sacrificeDetection.getSacrifices());
            
        }
        //validRituals.getRitual(foundRitualType).execute(event);
    }
    
    // Unused 
    public void refundSacrifices(Location location) {
        for (Sacrifice sacrifice : validRituals.getRitual(foundRitualType).getRequiredSacrifices()) {
            sacrifice.refundSacrifice(location);
        }
    }

    public ValidRituals.RitualTypeEn getRitualType() {
        return foundRitualType;
    }

    private int getNumPlayersInCircle(Location location) {
        int numPlayers = 0;
        for (Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().distance(location) <= DETECTION_RADIUS) {
                numPlayers++;
            }
        }

        return numPlayers;
    }
}
