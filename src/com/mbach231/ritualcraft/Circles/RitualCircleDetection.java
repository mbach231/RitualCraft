package com.mbach231.ritualcraft.Circles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RitualCircleDetection {

    private Set<Material> allowedMaterials;
    private Block selectedBlock;
    private Map<CircleSizes.CircleSizeEn, RitualCircle> detectedCircles;
    private Location blockLocation;
    private World currentWorld;
    private boolean hasSmallCircle;
    private boolean hasMediumCircle;
    private boolean hasLargeCircle;
    private boolean hasPillars;
    private RitualCircleRelativeLocation relativeLocations;
    public String detectionString;

    public RitualCircleDetection(Set<Material> allowedMaterials,
            RitualCircleRelativeLocation relativeLocations) {

        this.allowedMaterials = allowedMaterials;
        this.relativeLocations = relativeLocations;

        detectedCircles = new HashMap();

        initializeDetections();

    }

    private void initializeDetections() {

        detectionString = "";

        hasSmallCircle = false;
        hasMediumCircle = false;
        hasLargeCircle = false;
        hasPillars = false;

        detectedCircles.put(CircleSizes.CircleSizeEn.SMALL, null);
        detectedCircles.put(CircleSizes.CircleSizeEn.MEDIUM, null);
        detectedCircles.put(CircleSizes.CircleSizeEn.LARGE, null);
    }

    private Material getMaterial(RelativeLocation relativeLocation) {
        return currentWorld.getBlockAt(blockLocation.getBlockX() + relativeLocation.getX(),
                blockLocation.getBlockY() + relativeLocation.getY(),
                blockLocation.getBlockZ() + relativeLocation.getZ()).getType();
    }

    // Check if material is a valid circle material
    private boolean isValidMaterial(Material material) {
        if (allowedMaterials.contains(material)) {
            return true;
        }
        return false;
    }

    // Check locations for matching block material
    private boolean isValidStructure(Material circleMaterial, List<RelativeLocation> list) {

        RelativeLocation location;
        for (Iterator it = list.iterator(); it.hasNext();) {
            location = (RelativeLocation) it.next();

            //detectionString += location.toString();


            if (getMaterial(location) != circleMaterial) {

                detectionString += "Location: " + location.getX() + ","
                        + location.getY() + "," + location.getZ() + " -- Invalid!\n";
                return false;
            }
            detectionString += "Location: " + location.getX() + ","
                    + location.getY() + "," + location.getZ() + " -- Valid!\n";
        }
        return true;
    }

    private void detectSmallCircle() {

        List<RelativeLocation> smallCircleRelativeLocations = relativeLocations.getSmallCircleLocations();

        //: Initialize comparison material with first material
        Material circleMaterial = getMaterial(smallCircleRelativeLocations.get(0));

        //: If material is invalid, leave circle invalid
        if (!isValidMaterial(circleMaterial)) {
            detectionString += "Small Circle: Invalid material\n";
            return;
        }

        if (!isValidStructure(circleMaterial, smallCircleRelativeLocations)) {
            detectionString += "Small Circle: Invalid structure\n";
            return;
        }

        RitualCircle detectedCircle = new RitualCircle(CircleSizes.CircleSizeEn.SMALL,
                circleMaterial);

        detectedCircles.put(CircleSizes.CircleSizeEn.SMALL, detectedCircle);
        hasSmallCircle = true;
    }

    private void detectMediumCircle() {

        List<RelativeLocation> mediumCircleRelativeLocations = relativeLocations.getMediumCircleLocations();

        //: Initialize comparison material with first material
        Material circleMaterial = getMaterial(mediumCircleRelativeLocations.get(0));

        //: If material is invalid, leave circle invalid
        if (!isValidMaterial(circleMaterial)) {
            detectionString += "Medium Circle: Invalid material\n";
            return;
        }

        if (!isValidStructure(circleMaterial, mediumCircleRelativeLocations)) {
            detectionString += "Medium Circle: Invalid structure\n";
            return;
        }

        RitualCircle detectedCircle = new RitualCircle(CircleSizes.CircleSizeEn.MEDIUM,
                circleMaterial);

        detectedCircles.put(CircleSizes.CircleSizeEn.MEDIUM, detectedCircle);
        hasMediumCircle = true;
    }

    private void detectLargeCircle() {

        List<RelativeLocation> largeCircleRelativeLocations = relativeLocations.getLargeCircleLocations();

        //: Initialize comparison material with first material
        Material circleMaterial = getMaterial(largeCircleRelativeLocations.get(0));

        //: If material is invalid, leave circle invalid
        if (!isValidMaterial(circleMaterial)) {
            detectionString += "Large Circle: Invalid material\n";
            return;
        }

        if (!isValidStructure(circleMaterial, largeCircleRelativeLocations)) {
            detectionString += "Large Circle: Invalid structure\n";
            return;
        }

        RitualCircle detectedCircle = new RitualCircle(CircleSizes.CircleSizeEn.LARGE,
                circleMaterial);

        detectedCircles.put(CircleSizes.CircleSizeEn.LARGE, detectedCircle);
        hasLargeCircle = true;
    }

    private void detectPillars() {

        List<RelativeLocation> pillarRelativeLocations = relativeLocations.getPillarLocations();

        //: Initialize comparison material with redstone block
        Material circleMaterial = Material.REDSTONE_BLOCK;

        if (isValidStructure(circleMaterial, pillarRelativeLocations)) {
            hasPillars = true;
        }
    }

    public void detect(Block block) {

        initializeDetections();

        selectedBlock = block;
        blockLocation = selectedBlock.getLocation();
        currentWorld = selectedBlock.getWorld();

        detectPillars();
        detectSmallCircle();
        detectMediumCircle();
        detectLargeCircle();

    }

    public boolean hasSmallCircle() {
        return hasSmallCircle;
    }

    public boolean hasMediumCircle() {
        return hasMediumCircle;
    }

    public boolean hasLargeCircle() {
        return hasLargeCircle;
    }

    public boolean hasPillars() {
        return hasPillars;
    }

    public boolean hasValidCircle() {

        if ((hasPillars) && (hasSmallCircle || hasMediumCircle || hasLargeCircle)) {
            return true;
        }
        return false;
    }

    public Map<CircleSizes.CircleSizeEn, RitualCircle> getDetectedCircles() {
        return detectedCircles;
    }
}
