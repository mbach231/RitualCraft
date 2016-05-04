package com.mbach231.ritualcraft.Sacrifices;

import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class Sacrifice {

    private enum SacrificeTypeEn {

        ITEM,
        CREATURE
    }
    private Sacrifice.SacrificeTypeEn type;
    private Material material;
    private EntityType creatureType;
    private int amount;
    private Set<Entity> entitySet;

    public Sacrifice(Material material, int amount) {
        this.type = Sacrifice.SacrificeTypeEn.ITEM;
        this.material = material;
        this.creatureType = null;
        this.amount = amount;
        this.entitySet = null;
    }

    public Sacrifice(EntityType creatureType, int amount) {
        this.type = Sacrifice.SacrificeTypeEn.CREATURE;
        this.material = null;
        this.creatureType = creatureType;
        this.amount = amount;
        this.entitySet = null;
    }
    
        public Sacrifice(Material material, Set<Entity> entitySet) {
        this.type = Sacrifice.SacrificeTypeEn.ITEM;
        this.material = material;
        this.creatureType = null;
        
        this.amount = 0;
        for(Entity entity : entitySet) {
            this.amount += ((Item)entity).getItemStack().getAmount();
        }

        this.entitySet = entitySet;
    }

    public Sacrifice(EntityType creatureType, Set<Entity> entitySet) {
        this.type = Sacrifice.SacrificeTypeEn.CREATURE;
        this.material = null;
        this.creatureType = creatureType;
        this.amount = entitySet.size();
        this.entitySet = entitySet;
    }

    public Material getMaterial() {
        return material;
    }

    public EntityType getCreatureType() {
        return creatureType;
    }

    public int getAmount() {
        return amount;
    }
    
    public Set<Entity> getEntitySet() {
        return entitySet;
    }

    public boolean isItem() {
        return type == Sacrifice.SacrificeTypeEn.ITEM;
    }

    public boolean isCreature() {
        return type == Sacrifice.SacrificeTypeEn.CREATURE;
    }

    public void refundSacrifice(Location location) {
        if (type == Sacrifice.SacrificeTypeEn.ITEM) {
            ItemStack item = new ItemStack(material, amount);
            location.getWorld().dropItem(location, item);
        } 
    }

    public boolean fulfillsSacrifice(Sacrifice sacrifice) {
        if (type == sacrifice.type
                && sacrifice.creatureType == creatureType
                && sacrifice.material == material
                && sacrifice.getAmount() <= amount) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Sacrifice sacrifice = (Sacrifice) obj;

        if (type == sacrifice.type
                && material == sacrifice.getMaterial()
                && creatureType == sacrifice.creatureType
                && amount == sacrifice.getAmount()) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.type.hashCode();
        hash = 29 * hash + (this.creatureType != null ? this.creatureType.hashCode() : 0);
        hash = 29 * hash + (this.material != null ? this.material.hashCode() : 0);
        hash = 29 * hash + this.amount;
        return hash;
    }
}
