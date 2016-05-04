package com.mbach231.ritualcraft.Ritual;

import com.mbach231.ritualcraft.RitualEvent.Time.*;
import com.mbach231.ritualcraft.RitualEvent.Weather.*;
import com.mbach231.ritualcraft.RitualEvent.Teleport.*;
import com.mbach231.ritualcraft.RitualEvent.Spawn.*;
import com.mbach231.ritualcraft.RitualEvent.Enchant.*;
import com.mbach231.ritualcraft.RitualEvent.Meta.*;

import com.mbach231.ritualcraft.Sacrifices.Sacrifice;
import com.mbach231.ritualcraft.Circles.CircleSizes;
import com.mbach231.ritualcraft.Circles.RitualCircle;
import com.mbach231.ritualcraft.RitualCraft;
import com.mbach231.ritualcraft.RitualEvent.Shout.Shout;
import com.mbach231.ritualcraft.RitualEvent.Stucture.CreateStructure;
import com.mbach231.ritualcraft.Structures.Structure;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.ItemStack;

public class ValidRituals {

    public enum RitualTypeEn {

        CRAFT_CHICKEN,
        CRAFT_SHEEP,
        CRAFT_PIG,
        CRAFT_COW,
        CRAFT_HORSE,
        CRAFT_SKELETON_HORSE,
        CRAFT_ZOMBIE_HORSE,
        EGG_CHICKEN,
        EGG_SHEEP,
        EGG_PIG,
        EGG_COW,
        EGG_HORSE,
        SMELT_IRON,
        SMELT_GOLD,
        ENCHANT_PICKAXE_SILK_TOUCH,
        ENCHANT_PICKAXE_EFFICIENCY,
        ENCHANT_SWORD_SMITE,
        ENCHANT_ROD_LUCK,
        TELEPORT_RANDOM,
        TELEPORT_BED,
        SPIRIT_TUNNEL_OPEN_30S,
        SPIRIT_TUNNEL_TRAVEL,
        CAUSE_STORM,
        CLEAR_STORM,
        SET_DAYTIME,
        SET_NIGHTTIME,
        ETERNAL_NIGHT,
        RESUME_TIME,
        DETECT_MAGIC_1000M,
        SUPPRESS_MAGIC_1000M_1MIN,
        SHOUT_1PAGE_2000M,
        CREATE_END_PORTAL
    }
    RitualFactory ritualFactory;
    // Used to retrieve a ritual to perform event
    static Map<RitualTypeEn, Ritual> ritualMap;
    String validString = "";

    public ValidRituals() {

        validString += "Created new valid string!\n";
        ritualFactory = new RitualFactory();

        ritualMap = new HashMap();

        //********* FLESH CRAFTING ******************
        addRitual(RitualTypeEn.CRAFT_CHICKEN, createChickenRitual());
        addRitual(RitualTypeEn.CRAFT_SHEEP, createSheepRitual());
        addRitual(RitualTypeEn.CRAFT_PIG, createPigRitual());
        addRitual(RitualTypeEn.CRAFT_COW, createCowRitual());
        addRitual(RitualTypeEn.CRAFT_HORSE, createHorseRitual());
        addRitual(RitualTypeEn.CRAFT_SKELETON_HORSE, createSkeletonHorseRitual());
        addRitual(RitualTypeEn.CRAFT_ZOMBIE_HORSE, createZombieHorseRitual());

        //************** TIME ******************
        addRitual(RitualTypeEn.SET_DAYTIME, createDaytimeRitual());
        addRitual(RitualTypeEn.SET_NIGHTTIME, createNighttimeRitual());
        addRitual(RitualTypeEn.ETERNAL_NIGHT, eternalNightRitual());
        addRitual(RitualTypeEn.RESUME_TIME, resumeTimeRitual());

        //************** WEATHER ******************
        addRitual(RitualTypeEn.CAUSE_STORM, causeStormRitual());
        addRitual(RitualTypeEn.CLEAR_STORM, clearStormRitual());

        //*************** ENCHANTING ******************
        addRitual(RitualTypeEn.ENCHANT_PICKAXE_EFFICIENCY, enchantPickaxeEfficiencyRitual());
        addRitual(RitualTypeEn.ENCHANT_PICKAXE_SILK_TOUCH, enchantPickaxeSilkTouchRitual());
        addRitual(RitualTypeEn.ENCHANT_SWORD_SMITE, enchantSwordSmiteRitual());
        addRitual(RitualTypeEn.ENCHANT_ROD_LUCK, enchantRodLuckRitual());

        //************** TELEPORTING ******************
        addRitual(RitualTypeEn.SPIRIT_TUNNEL_OPEN_30S, openSpiritTunnel30sRitual());
        addRitual(RitualTypeEn.SPIRIT_TUNNEL_TRAVEL, travelSpiritTunnelRitual());
        addRitual(RitualTypeEn.TELEPORT_RANDOM, randomTeleportRitual());
        addRitual(RitualTypeEn.TELEPORT_BED, teleportBedRitual());

        //************** DETECT MAGIC ******************
        addRitual(RitualTypeEn.DETECT_MAGIC_1000M, detectMagic1000m());

        //************** SUPPRESS MAGIC ******************
        addRitual(RitualTypeEn.SUPPRESS_MAGIC_1000M_1MIN, suppressMagic1000m1min());

        //*************** CREATE ITEM ******************
        addRitual(RitualTypeEn.SMELT_IRON, smeltIronRitual());
        addRitual(RitualTypeEn.SMELT_GOLD, smeltGoldRitual());
        addRitual(RitualTypeEn.EGG_CHICKEN, eggChickenRitual());
        addRitual(RitualTypeEn.EGG_SHEEP, eggSheepRitual());
        addRitual(RitualTypeEn.EGG_PIG, eggPigRitual());
        addRitual(RitualTypeEn.EGG_COW, eggCowRitual());
        addRitual(RitualTypeEn.EGG_HORSE, eggHorseRitual());
        
        //************** SHOUT MAGIC ******************
        addRitual(RitualTypeEn.SHOUT_1PAGE_2000M, shout1page2000m());
        
        //************** STRUCTURE MAGIC ******************
        addRitual(RitualTypeEn.CREATE_END_PORTAL, createEndPortal());

    }

    private void addRitual(RitualTypeEn ritualType, Ritual ritual) {

        ritualMap.put(ritualType, ritual);

        validString += "Adding Ritual: " + ritualType.toString() + "\n";

    }

    public static Ritual getRitual(RitualTypeEn type) {
        return ritualMap.get(type);
    }

    // Check if found circles and sacrifices correspond to a ritual
    public RitualTypeEn getRitualType(Map<CircleSizes.CircleSizeEn, RitualCircle> foundCircles,
            Set<Sacrifice> foundSacrifices) {

        //validString = "";
        RitualTypeEn type;
        Ritual ritual;

        // Iterate through rituals
        for (Map.Entry<RitualTypeEn, Ritual> entry : ritualMap.entrySet()) {

            type = entry.getKey();
            ritual = entry.getValue();

            RitualCraft.outputString += "Testing ritual: " + type.toString() + "\n";

            if (ritual.usesCircles(foundCircles)) {
                RitualCraft.outputString += "HAS CORRECT CIRCLES\n";
            } else {
                RitualCraft.outputString += "CIRCLES INVALID\n";
                continue;
            }

            for (Sacrifice sacrifice : foundSacrifices) {
                RitualCraft.outputString += sacrifice.getMaterial() + ", " + sacrifice.getAmount() + "\n";

            }

            if (ritual.usesSacrifices(foundSacrifices)) {
                RitualCraft.outputString += "HAS CORRECT SACRIFICES\n";
            } else {
                RitualCraft.outputString += "SACRIFICES INVALID\n";
            }

            // If a ritual exists that uses circles and sacrifices, return ritual type
            if (ritual.usesCircles(foundCircles) && ritual.usesSacrifices(foundSacrifices)) {
                return type;
            }

        }

        // No ritual exists that uses these circles and sacrifices together
        return null;
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                          TIME RITUALS                               //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////
    private Ritual createDaytimeRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.GOLD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.DIAMOND_BLOCK);

        ritualFactory.addSacrifice(Material.BLAZE_POWDER, 1);

        ritualFactory.addTimeRange(12500, 23500);

        ritualFactory.addEvent(new Daytime());

        validString += ritualFactory.factoryString;

        return ritualFactory.constructRitual();

    }

    private Ritual createNighttimeRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.IRON_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.DIAMOND_BLOCK);

        ritualFactory.addSacrifice(Material.INK_SACK, 1);
        ritualFactory.addSacrifice(Material.ENDER_PEARL, 1);

        ritualFactory.addEvent(new Nighttime());
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual resumeTimeRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.EMERALD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.REDSTONE_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.COAL, 1);
        ritualFactory.addSacrifice(Material.BLAZE_POWDER, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);

        ritualFactory.addRequiredBiome(Biome.DESERT);

        ritualFactory.addEvent(new ResumeTime());
        return ritualFactory.constructRitual();
    }

    private Ritual eternalNightRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.REDSTONE_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.DIAMOND_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.EMERALD_BLOCK);

        ritualFactory.addSacrifice(EntityType.VILLAGER, 10);
        ritualFactory.addSacrifice(Material.NETHER_STAR, 1);

        ritualFactory.addRequiredBiome(Biome.ICE_PLAINS_SPIKES);

        ritualFactory.addTimeRange(17750, 18250);

        ritualFactory.addRequiredMoonPhase(MoonPhase.FULL_MOON);

        ritualFactory.addNumPlayersRequirement(5);

        ritualFactory.addEvent(new FreezeTime());
        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                    FLESHCRAFTING RITUALS                            //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////    
    private Ritual createCowRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.CLAY);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.BONE, 8);
        ritualFactory.addSacrifice(Material.ROTTEN_FLESH, 8);
        ritualFactory.addSacrifice(Material.INK_SACK, 1);
        ritualFactory.addSacrifice(Material.SUGAR, 1);
        ritualFactory.addSacrifice(Material.WHEAT, 2);

        ritualFactory.addEvent(new SpawnEntity(EntityType.COW));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual createHorseRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.CLAY);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.BONE, 10);
        ritualFactory.addSacrifice(Material.ROTTEN_FLESH, 8);
        ritualFactory.addSacrifice(Material.SUGAR, 3);

        ritualFactory.addEvent(new SpawnEntity(EntityType.HORSE));
        
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }
    
    private Ritual createSkeletonHorseRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.CLAY);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.BONE, 20);
        ritualFactory.addSacrifice(Material.ROTTEN_FLESH, 2);
        ritualFactory.addSacrifice(Material.SUGAR, 3);

        ritualFactory.addEvent(new SpawnEntity(EntityType.HORSE, Variant.SKELETON_HORSE));
        
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }
    
    private Ritual createZombieHorseRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.CLAY);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.BONE, 20);
        ritualFactory.addSacrifice(Material.ROTTEN_FLESH, 10);
        ritualFactory.addSacrifice(Material.SUGAR, 3);

        ritualFactory.addEvent(new SpawnEntity(EntityType.HORSE, Variant.UNDEAD_HORSE));
        
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual createPigRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.CLAY);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.BONE, 3);
        ritualFactory.addSacrifice(Material.ROTTEN_FLESH, 3);
        ritualFactory.addSacrifice(Material.CARROT_ITEM, 1);

        ritualFactory.addEvent(new SpawnEntity(EntityType.PIG));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual createChickenRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.CLAY);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.BONE, 3);
        ritualFactory.addSacrifice(Material.ROTTEN_FLESH, 3);

        ritualFactory.addEvent(new SpawnEntity(EntityType.CHICKEN));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual createSheepRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.CLAY);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.BONE, 1);
        ritualFactory.addSacrifice(Material.ROTTEN_FLESH, 1);
        ritualFactory.addSacrifice(Material.WHEAT, 2);

        ritualFactory.addEvent(new SpawnEntity(EntityType.SHEEP));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                       ENCHANTING RITUALS                            //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////   
    private Ritual enchantPickaxeSilkTouchRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.DIAMOND_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.GOLD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 4);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.DIAMOND_PICKAXE, 1);
        ritualFactory.addSacrifice(Material.GOLD_INGOT, 1);
        ritualFactory.addSacrifice(EntityType.CHICKEN, 2);

        ritualFactory.addEvent(new Enchant(Material.DIAMOND_PICKAXE, Enchantment.SILK_TOUCH, 1));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual enchantPickaxeEfficiencyRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.DIAMOND_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.GOLD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.DIAMOND_PICKAXE, 1);
        ritualFactory.addSacrifice(Material.GOLD_INGOT, 1);
        ritualFactory.addSacrifice(Material.SUGAR, 1);

        ritualFactory.addEvent(new Enchant(Material.DIAMOND_PICKAXE, Enchantment.DIG_SPEED, 4));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual enchantSwordSmiteRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.DIAMOND_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.IRON_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.DIAMOND_SWORD, 1);
        ritualFactory.addSacrifice(Material.GOLD_INGOT, 1);
        ritualFactory.addSacrifice(Material.GHAST_TEAR, 1);

        ritualFactory.addEvent(new Enchant(Material.DIAMOND_SWORD, Enchantment.DAMAGE_UNDEAD, 4));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual enchantRodLuckRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.DIAMOND_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.IRON_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.FISHING_ROD, 1);
        ritualFactory.addSacrifice(Material.GOLD_INGOT, 1);
        ritualFactory.addSacrifice(Material.GHAST_TEAR, 1);

        ritualFactory.addEvent(new Enchant(Material.FISHING_ROD, Enchantment.LUCK, 2));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                     SPAWN ITEMS RITUALS                             //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////
    private Ritual smeltIronRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.IRON_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.COAL_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.IRON_ORE, 16);
        ritualFactory.addSacrifice(Material.COAL, 2);

        ritualFactory.addEvent(new SpawnItem(new ItemStack(Material.IRON_INGOT, 17)));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual smeltGoldRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.GOLD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.COAL_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.GOLD_ORE, 16);
        ritualFactory.addSacrifice(Material.COAL, 2);

        ritualFactory.addNumPlayersRequirement(2);

        ritualFactory.addEvent(new SpawnItem(new ItemStack(Material.GOLD_INGOT, 17)));
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual eggChickenRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.EMERALD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.COAL_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.EGG, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.CLAY_BALL, 8);
        ritualFactory.addSacrifice(EntityType.CHICKEN, 1);

        ritualFactory.addEvent(new SpawnItem(new ItemStack(Material.MONSTER_EGG, 1, EntityType.CHICKEN.getTypeId())));

        return ritualFactory.constructRitual();
    }

    private Ritual eggSheepRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.EMERALD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.COAL_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.EGG, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.CLAY_BALL, 8);
        ritualFactory.addSacrifice(EntityType.SHEEP, 1);

        ritualFactory.addEvent(new SpawnItem(new ItemStack(Material.MONSTER_EGG, 1, EntityType.SHEEP.getTypeId())));

        return ritualFactory.constructRitual();
    }

    private Ritual eggPigRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.EMERALD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.COAL_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.EGG, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.CLAY_BALL, 8);
        ritualFactory.addSacrifice(EntityType.PIG, 1);

        ritualFactory.addEvent(new SpawnItem(new ItemStack(Material.MONSTER_EGG, 1, EntityType.PIG.getTypeId())));

        return ritualFactory.constructRitual();
    }

    private Ritual eggCowRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.EMERALD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.COAL_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.EGG, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.CLAY_BALL, 8);
        ritualFactory.addSacrifice(EntityType.COW, 1);

        ritualFactory.addEvent(new SpawnItem(new ItemStack(Material.MONSTER_EGG, 1, EntityType.COW.getTypeId())));

        return ritualFactory.constructRitual();
    }

    private Ritual eggHorseRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.EMERALD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.COAL_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.CLAY);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.EGG, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);
        ritualFactory.addSacrifice(Material.CLAY_BALL, 8);
        ritualFactory.addSacrifice(EntityType.HORSE, 1);

        ritualFactory.addEvent(new SpawnItem(new ItemStack(Material.MONSTER_EGG, 1, EntityType.HORSE.getTypeId())));

        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                    TELEPORTATION RITUALS                            //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////   
    private Ritual teleportBedRitual() {
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.ENDER_STONE);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.ENDER_STONE);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.DIAMOND_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.ENDER_PEARL, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);

        ritualFactory.addEvent(new TeleportBed());

        return ritualFactory.constructRitual();
    }

    private Ritual randomTeleportRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.ENDER_STONE);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.ENDER_STONE);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.ENDER_PEARL, 1);

        ritualFactory.addEvent(new RandomTeleport());
        validString += ritualFactory.factoryString;
        return ritualFactory.constructRitual();
    }

    private Ritual openSpiritTunnel30sRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.ENDER_STONE);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.ENDER_PEARL, 1);
        ritualFactory.addSacrifice(Material.IRON_INGOT, 1);

        ritualFactory.addEvent(new SpiritTunnelOpen(1000 * 30));

        return ritualFactory.constructRitual();
    }

    private Ritual travelSpiritTunnelRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.ENDER_STONE);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.ENDER_PEARL, 1);
        ritualFactory.addSacrifice(Material.DIAMOND, 1);

        ritualFactory.addEvent(new SpiritTunnelTravel());

        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                          WEATHER RITUALS                            //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////   
    private Ritual causeStormRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.LAPIS_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.GOLD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.IRON_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.GHAST_TEAR, 1);
        ritualFactory.addSacrifice(Material.WATER_BUCKET, 1);

        ritualFactory.addEvent(new StormyWeather());
        return ritualFactory.constructRitual();
    }

    private Ritual clearStormRitual() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.LAPIS_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.GOLD_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.LAVA_BUCKET, 1);
        ritualFactory.addSacrifice(Material.BLAZE_POWDER, 1);

        ritualFactory.addEvent(new ClearWeather());
        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                  MAGIC DETECTION RITUALS                            //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////   
    private Ritual detectMagic1000m() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.REDSTONE_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.DIAMOND_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.EMERALD_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.SKULL_ITEM, 1);

        ritualFactory.addEvent(new DetectMagic(1000, 0));
        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                   MAGIC SUPPRESSION RITUALS                         //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////   
    private Ritual suppressMagic1000m1min() {

        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.EMERALD_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.REDSTONE_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.EMERALD_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.EMERALD, 1);

        ritualFactory.addEvent(new SuppressArea(1000 * 60, 1000));

        return ritualFactory.constructRitual();
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                          SHOUT RITUALS                              //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////
    private Ritual shout1page2000m() {
        
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.ENDER_STONE);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.LAPIS_BLOCK);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.EMERALD_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.ENDER_PEARL, 1);
        ritualFactory.addSacrifice(Material.WRITTEN_BOOK, 1);

        ritualFactory.addEvent(new Shout(1, 2000));
        return ritualFactory.constructRitual();
    }
    
    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                        STRUCTURE RITUALS                            //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////
    private Ritual createEndPortal() {
        
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.SMALL, Material.ENDER_STONE);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.MEDIUM, Material.ENDER_STONE);
        ritualFactory.addCircle(CircleSizes.CircleSizeEn.LARGE, Material.EMERALD_BLOCK);

        ritualFactory.addSacrifice(Material.REDSTONE, 1);
        ritualFactory.addSacrifice(Material.ENDER_PEARL, 1);
        ritualFactory.addSacrifice(Material.NETHER_STAR, 1);
        
        Structure structure = new Structure();
        structure.addStructureBlock(Material.ENDER_PORTAL, 0, 1, 0);
        
        ritualFactory.addEvent(new CreateStructure(structure, true));
        return ritualFactory.constructRitual();
    }
}
