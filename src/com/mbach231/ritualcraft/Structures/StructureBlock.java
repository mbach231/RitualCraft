package com.mbach231.ritualcraft.Structures;




import org.bukkit.Material;


class StructureBlock {
    
    final private Material blockType;
    final private RelativeLocation relativeLocation;
    
    StructureBlock(Material blockMaterial, RelativeLocation relativeLocation) {
        this.blockType = blockMaterial;
        this.relativeLocation = relativeLocation;
    }
    
    StructureBlock(Material blockMaterial, int x, int y, int z) {
        this.blockType = blockMaterial;
        this.relativeLocation = new RelativeLocation(x, y, z);
    }
    
    Material getBlockType() {
        return blockType;
    }
    
    RelativeLocation getRelativeLocation() {
        return relativeLocation;
    }
}
