package com.mbach231.ritualcraft.Circles;

import com.mbach231.ritualcraft.Circles.CircleSizes.*;

import org.bukkit.Material;

public class RitualCircle {

    boolean validCenter;
    CircleSizeEn circleSize;
    Material circleMaterial;

    public RitualCircle(CircleSizeEn circleSize, Material circleMaterial) {

        this.circleSize = circleSize;
        this.circleMaterial = circleMaterial;
    }

    public CircleSizeEn getCircleSize() {
        return circleSize;
    }

    public Material getCircleMaterial() {
        return circleMaterial;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null) {
            return false;
        }
        
        if(obj.getClass() != this.getClass()) {
            return false;
        }
        
        RitualCircle comparisonCircle = (RitualCircle)obj;
        
        if(this.circleSize != comparisonCircle.getCircleSize() ||
           this.circleMaterial != comparisonCircle.getCircleMaterial()) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.circleSize != null ? this.circleSize.hashCode() : 0);
        hash = 41 * hash + (this.circleMaterial != null ? this.circleMaterial.hashCode() : 0);
        return hash;
    }


}
