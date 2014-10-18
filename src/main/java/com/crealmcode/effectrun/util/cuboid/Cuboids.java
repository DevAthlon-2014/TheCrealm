package com.crealmcode.effectrun.util.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class Cuboids {

    public static boolean intersectsOneOrMoreOfAllPlayers(Cuboid cuboid){

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(cuboid.intersects(player)){
                return true;
            }
        }

        return false;
    }

    public static boolean intersectsAllOfAllPlayers(Cuboid cuboid){

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(cuboid.intersects(player)) {
                return false;
            }
        }

        return true;
    }

    public static List<Block> getBlocks(Cuboid cuboid, World world){

        List<Block> blocks = new ArrayList<Block>();

        for(int x = cuboid.getVectorMin().getBlockX(); x <= cuboid.getVectorMax().getBlockX(); x++){
            for(int y = cuboid.getVectorMin().getBlockY(); y <= cuboid.getVectorMax().getBlockY(); y++){
                for(int z = cuboid.getVectorMin().getBlockZ(); z <= cuboid.getVectorMax().getBlockZ(); z++){
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }
}
