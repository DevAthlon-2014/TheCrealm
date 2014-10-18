package com.crealmcode.effectrun.game.platform.def;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles;
import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.game.platform.Platform;
import com.crealmcode.effectrun.util.ConfigurationLoader;
import com.crealmcode.effectrun.util.ParticleUtil;
import com.crealmcode.effectrun.util.Util;
import com.crealmcode.effectrun.util.cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.*;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class PlayerElevatorPlatform extends Platform {

    public PlayerElevatorPlatform(Game game, Cuboid cuboid) {

        super(cuboid);
        Bukkit.getScheduler().runTaskTimer(game.getPlugin(), new ElevatorTask(game, this), 0, 1l);
    }

    @Override
    protected void onAction(Player player, Location from, Location to) {

        if(player.getVelocity().getY() < 0.25) {

            Vector velocity = new Vector();
            velocity.setY(0.5);

            if(!player.isSneaking()) {
                velocity.setX(to.getX() - from.getX());
                velocity.setZ(to.getZ() - from.getZ());
            }

            player.setVelocity(velocity);
        }
    }

    private static class ElevatorTask implements Runnable {

        private List<FallingBlock> fallingBlocks = new ArrayList<FallingBlock>();
        private Game game;
        private Random random = new Random();
        private Cuboid cuboid;

        private ElevatorTask(Game game, PlayerElevatorPlatform platform) {

            this.game = game;
            this.cuboid = platform.getCuboid();
        }

        @Override
        public void run() {

            //Effect things..
            for(double x = cuboid.getVectorMin().getX(); x <= cuboid.getVectorMax().getX(); x++){
                for(double z = cuboid.getVectorMin().getZ(); z <= cuboid.getVectorMax().getZ(); z++){
                    if(random.nextInt(80) == 0){
                        final double finalX = x;
                        final double finalZ = z;
                        final AtomicInteger count = new AtomicInteger(cuboid.getVectorMin().getBlockY());
                        new BukkitRunnable(){

                            @Override
                            public void run() {

                                ParticleUtil.display("smoke", new Vector(0, 0.2, 0), 0, 4, new Location(Bukkit.getWorlds().get(0), finalX + 0.5, count.get(), finalZ + 0.5));

                                if(count.incrementAndGet() >= cuboid.getVectorMax().getBlockY()){
                                    cancel();
                                }
                            }

                        }.runTaskTimer(game.getPlugin(), 0, 3l);
                    }
                }
            }
        }
    }

    public static class PlayerElevatorPlatformLoader implements ConfigurationLoader<PlayerElevatorPlatform> {

        @Override
        public PlayerElevatorPlatform load(Game game, ConfigurationSection section) {

            Cuboid cuboid = Util.parseCuboid(section.getString("actionCuboid"));
            return new PlayerElevatorPlatform(game, cuboid);
        }
    }
}
