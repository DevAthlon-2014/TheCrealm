package com.crealmcode.effectrun.game.platform.def;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles;
import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.game.platform.Platform;
import com.crealmcode.effectrun.util.ConfigurationLoader;
import com.crealmcode.effectrun.util.ParticleUtil;
import com.crealmcode.effectrun.util.Util;
import com.crealmcode.effectrun.util.cuboid.Cuboid;
import com.crealmcode.effectrun.util.cuboid.Cuboids;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class DelayedPlayerWeightedPlatformAction extends Platform {

    private BukkitTask task;
    private Game game;
    private int delay;
    private World world;
    private boolean active = false;
    private final Cuboid up;

    public DelayedPlayerWeightedPlatformAction(Game game, Cuboid weight, int delay, Cuboid up, World world) {

        super(weight);
        this.up = up;
        this.world = world;
        this.delay = delay;
        this.game = game;
    }

    public void update(final boolean newState) {

        if(active == newState){
            return;
        }

        if(newState){

            Map<Location, Material> materialStorage = new HashMap<Location, Material>();
            Map<Location, Byte> byteStorage = new HashMap<Location, Byte>();

            for (Block block : Cuboids.getBlocks(up, world)) {
                materialStorage.put(block.getLocation(), block.getType());
                byteStorage.put(block.getLocation(), block.getData());
                block.setType(Material.AIR);
            }

            for (Block block : Cuboids.getBlocks(up, world)) {
                world.getBlockAt(block.getLocation().add(0, (newState ? 1 : -1), 0)).setType(materialStorage.get(block.getLocation()));
                world.getBlockAt(block.getLocation().add(0, (newState ? 1 : -1), 0)).setData(byteStorage.get(block.getLocation()));
                ParticleUtil.display(WrapperPlayServerWorldParticles.ParticleEffect.PORTAL, new Vector(0.6, 1.2, 0.6), 0f, 8, block.getLocation().add(0.5, 1.5, 0.5));
            }

        } else {

            task = new BukkitRunnable() {
                @Override
                public void run() {
                    Map<Location, Material> materialStorage = new HashMap<Location, Material>();
                    Map<Location, Byte> byteStorage = new HashMap<Location, Byte>();

                    for (Block block : Cuboids.getBlocks(up, world)) {
                        materialStorage.put(block.getLocation(), block.getType());
                        byteStorage.put(block.getLocation(), block.getData());
                        block.setType(Material.AIR);
                    }

                    for (Block block : Cuboids.getBlocks(up, world)) {
                        world.getBlockAt(block.getLocation().add(0, (newState ? 1 : -1), 0)).setType(materialStorage.get(block.getLocation()));
                        world.getBlockAt(block.getLocation().add(0, (newState ? 1 : -1), 0)).setData(byteStorage.get(block.getLocation()));
                        ParticleUtil.display(WrapperPlayServerWorldParticles.ParticleEffect.PORTAL, new Vector(0.6, 1.2, 0.6), 0f, 8, block.getLocation().add(0.5, 1.5, 0.5));
                    }

                    task = null;
                }
            }.runTaskLater(game.getPlugin(), delay);
        }

        active = newState;
    }

    @Override
    protected void onAction(Player player, Location from, Location to) {

        if(task == null) {
            if (active) {
                if (!getCuboid().intersects(to)) {
                    update(false);
                }
            } else {
                update(true);
            }
        }
    }

    public static class DelayedPlayerWeightedPlatformActionLoader implements ConfigurationLoader<DelayedPlayerWeightedPlatformAction> {

        @Override
        public DelayedPlayerWeightedPlatformAction load(Game game, ConfigurationSection section) {

            World actionWorld = Bukkit.getWorld(section.getString("world"));
            Cuboid trigger = Util.parseCuboid(section.getString("trigger"));
            Cuboid up = Util.parseCuboid(section.getString("up"));
            int delay = section.getInt("delay");

            return new DelayedPlayerWeightedPlatformAction(game, trigger, delay, up, actionWorld);
        }
    }
}
