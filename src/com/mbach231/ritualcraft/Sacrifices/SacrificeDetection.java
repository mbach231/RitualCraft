package com.mbach231.ritualcraft.Sacrifices;

import com.mbach231.ritualcraft.Circles.CircleSizes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class SacrificeDetection {

    private final int DETECTION_RADIUS = CircleSizes.PILLAR_RADIUS;
    public String detectionString;
    Map<Material, Integer> materialSacrificeMap;
    Map<EntityType, Integer> creatureSacrificeMap;

    Map<Material, Set<Entity>> itemMap;
    Map<EntityType, Set<Entity>> creatureMap;

    Set<Sacrifice> detectedSacrifices;
    Set<Item> detectedItems;
    Set<Creature> detectedCreatures;
    Set<Item> purgeItems;
    Set<Creature> purgeCreatures;

    public SacrificeDetection() {

        detectionString = "";

        materialSacrificeMap = new HashMap();
        creatureSacrificeMap = new HashMap();

        itemMap = new HashMap();
        creatureMap = new HashMap();

        detectedSacrifices = new HashSet();
        detectedItems = new HashSet();
        detectedCreatures = new HashSet();

        purgeItems = new HashSet();
        purgeCreatures = new HashSet();

    }

    public Set<Sacrifice> getSacrifices() {
        return detectedSacrifices;
    }

    private void initializeDetections() {

        detectionString = "";

        materialSacrificeMap.clear();
        creatureSacrificeMap.clear();
        detectedSacrifices.clear();
        detectedItems.clear();
        detectedCreatures.clear();
    }

    private void updateSacrifices(Entity entity) {
        if (entity instanceof Creature) {
            updateSacrifices((Creature) entity);
        } else if (entity instanceof Item) {
            updateSacrifices((Item) entity);
        }
    }

    private void updateSacrifices(Creature creature) {

        detectedCreatures.add(creature);

        EntityType creatureType = creature.getType();

        Set<Entity> creatureSet;
        if (creatureMap.containsKey(creatureType)) {
            creatureSet = creatureMap.get(creatureType);
        } else {
            creatureSet = new HashSet();
        }

        creatureSet.add(creature);
        creatureMap.put(creatureType, creatureSet);

        if (!creatureSacrificeMap.containsKey(creature.getType())) {
            creatureSacrificeMap.put(creature.getType(), 1);
        } else {
            creatureSacrificeMap.put(creature.getType(), 1 + creatureSacrificeMap.get(creature.getType()));
        }
    }

    private void updateSacrifices(Item item) {

        ItemStack itemStack = item.getItemStack();
        detectedItems.add(item);

        Set<Entity> itemSet;
        if (itemMap.containsKey(itemStack.getType())) {
            itemSet = itemMap.get(itemStack.getType());
        } else {
            itemSet = new HashSet();
        }
        itemSet.add(item);
        itemMap.put(itemStack.getType(), itemSet);

        if (!materialSacrificeMap.containsKey(itemStack.getType())) {
            materialSacrificeMap.put(itemStack.getType(), itemStack.getAmount());
        } else {
            materialSacrificeMap.put(itemStack.getType(), itemStack.getAmount() + materialSacrificeMap.get(itemStack.getType()));
        }

    }

    private void updateSacrifices() {
        for(Map.Entry<Material,Set<Entity>> entry : itemMap.entrySet()) {
            detectedSacrifices.add(new Sacrifice(entry.getKey(), entry.getValue()));
        }
        itemMap.clear();
        
        for(Map.Entry<EntityType,Set<Entity>> entry : creatureMap.entrySet()) {
            detectedSacrifices.add(new Sacrifice(entry.getKey(), entry.getValue()));
        }
        creatureMap.clear();
        /*
        for (Map.Entry<Material, Integer> entry : materialSacrificeMap.entrySet()) {
            detectedSacrifices.add(new Sacrifice(entry.getKey(), entry.getValue()));
        }

        for (Map.Entry<EntityType, Integer> entry : creatureSacrificeMap.entrySet()) {
            detectedSacrifices.add(new Sacrifice(entry.getKey(), entry.getValue()));
        }
        */
    }

    public void detect(Block block) {
        initializeDetections();

        Location blockLocation = block.getLocation();
        Entity[] nearbyEntities = getNearbyEntities(blockLocation, DETECTION_RADIUS);

        double distance;

        for (Entity entity : nearbyEntities) {

            // If not an item, skip
            if (!(entity instanceof Item) && !(entity instanceof Creature)) {
                continue;
            }

            distance = Math.sqrt(Math.pow(blockLocation.getBlockX() - entity.getLocation().getBlockX(), 2)
                    + Math.pow(blockLocation.getBlockZ() - entity.getLocation().getBlockZ(), 2));
            // If entity is in range, add as sacrifices
            if (distance < (double) DETECTION_RADIUS) {
                updateSacrifices(entity);
            }
        }

        updateSacrifices();
    }

    private Entity[] getNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        HashSet<Entity> radiusEntities = new HashSet<>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {

                    if (l.getWorld() != e.getWorld()) {
                        continue;
                    }

                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    public boolean hasSacrifices() {
        return !detectedSacrifices.isEmpty();
    }

    public void purgeDetectedEntities(Set<Sacrifice> sacrifices) {

        purgeDetectedItems(sacrifices);

        purgeDetectedCreatures(sacrifices);

    }

    private void purgeDetectedItems(Set<Sacrifice> sacrifices) {
        Set<Item> tempDetectedItems = detectedItems;

        // Iterate through all valid sacrifices
        for (Sacrifice sacrifice : sacrifices) {

            if (!materialSacrificeMap.containsKey(sacrifice.getMaterial())) {
                continue;
            }

            int numItemsNeeded = materialSacrificeMap.get(sacrifice.getMaterial());
            Item foundItem;
            while (numItemsNeeded > 0) {

                foundItem = null;
                for (Item item : tempDetectedItems) {
                    if (item.getItemStack().getType() == sacrifice.getMaterial()) {
                        foundItem = item;
                        break;
                    }
                }
                // Don't think this should be reachable
                if (foundItem == null) {
                    break;
                }
                purgeItems.add(foundItem);
                detectedItems.remove(foundItem);
                numItemsNeeded--;
            }

        }
        for (Item item : purgeItems) {
            item.remove();
        }
        purgeItems.clear();

    }

    private void purgeDetectedCreatures(Set<Sacrifice> sacrifices) {
        Set<Creature> tempDetectedCreatures = detectedCreatures;

        // Iterate through all valid sacrifices
        for (Sacrifice sacrifice : sacrifices) {

            if (!creatureSacrificeMap.containsKey(sacrifice.getCreatureType())) {
                continue;
            }

            int numNeeded = creatureSacrificeMap.get(sacrifice.getCreatureType());
            Creature foundCreature;
            while (numNeeded > 0) {

                foundCreature = null;
                for (Creature creature : tempDetectedCreatures) {
                    if (creature.getType() == sacrifice.getCreatureType()) {
                        foundCreature = creature;
                        break;
                    }
                }
                // Don't think this should be reachable
                if (foundCreature == null) {
                    break;
                }
                purgeCreatures.add(foundCreature);
                detectedCreatures.remove(foundCreature);
                numNeeded--;
            }

        }
        for (Creature creature : purgeCreatures) {
            creature.remove();
        }
        purgeCreatures.clear();

    }

}
