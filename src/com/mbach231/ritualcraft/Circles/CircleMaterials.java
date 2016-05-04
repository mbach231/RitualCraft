package com.mbach231.ritualcraft.Circles;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;

public class CircleMaterials {

    private Set<Material> validCircleMaterials;

    public CircleMaterials() {
        validCircleMaterials = new HashSet();

        validCircleMaterials.add(Material.CLAY);
        validCircleMaterials.add(Material.COAL_BLOCK);
        validCircleMaterials.add(Material.DIAMOND_BLOCK);
        validCircleMaterials.add(Material.EMERALD_BLOCK);
        validCircleMaterials.add(Material.ENDER_STONE);
        validCircleMaterials.add(Material.GOLD_BLOCK);
        validCircleMaterials.add(Material.ICE);
        validCircleMaterials.add(Material.IRON_BLOCK);
        validCircleMaterials.add(Material.LAPIS_BLOCK);
        validCircleMaterials.add(Material.NETHERRACK);
        validCircleMaterials.add(Material.OBSIDIAN);
        validCircleMaterials.add(Material.REDSTONE_BLOCK);
        validCircleMaterials.add(Material.SOUL_SAND);

    }

    public Set<Material> getValidCircleMaterials() {
        return validCircleMaterials;
    }
}
