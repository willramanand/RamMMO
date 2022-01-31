package com.gmail.willramanand.RamMMO.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.*;

public class BuilderWandUtils {

    private static final Set<Material> replaceables = Set.of(Material.LAVA, Material.WATER, Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.TALL_GRASS, Material.TALL_SEAGRASS);

    public static List<Location> getValidLocations(Location loc, BlockFace face, Material material) {
        List<Location> locations = new ArrayList<>();

        boolean isX = face.getModX() != 0, isY = face.getModY() != 0, isZ = face.getModZ() != 0;
        locations.add(loc);

        Set<Location> checked = new HashSet<>();
        Queue<Location> checking = new ArrayDeque<>();

        checking.add(loc.clone());
        while (locations.size() < 250) {
            Location check = checking.poll();
            checked.add(check);
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    if (x == 0 && z == 0) {
                        continue;
                    }
                    Location clone = check.clone();
                    if (isX) {
                        clone.add(0, x, z);
                    } else if (isY) {
                        clone.add(x, 0, z);
                    } else if (isZ) {
                        clone.add(x, z, 0);
                    }
                    if (checked.contains(clone)) {
                        continue;
                    } else {
                        checked.add(clone);
                    }

                    Block cloneBlock = clone.getBlock();
                    Block relative = cloneBlock.getRelative(face.getOppositeFace());
                    if (!replaceables.contains(cloneBlock.getType()) || relative.getType() != material) {
                        continue;
                    }

                    if (locations.size() >= 250) {
                        break;
                    }

                    locations.add(clone);
                    checking.add(clone);
                    checked.add(clone);
                }
            }
            if (checking.isEmpty()) {
                break;
            }
        }
        return locations;
    }

    public static BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(replaceables, 250);
        if (lastTwoTargetBlocks.size() != 2) { //|| !lastTwoTargetBlocks.get( 1 ).getType().isOccluding() ) {
            return null;
        }
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }

    public static Set<Material> getReplaceables() {
        return replaceables;
    }
}
