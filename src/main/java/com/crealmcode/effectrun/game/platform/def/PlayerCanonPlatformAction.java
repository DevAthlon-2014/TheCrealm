package com.crealmcode.effectrun.game.platform.def;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles;
import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.game.platform.Platform;
import com.crealmcode.effectrun.util.ConfigurationLoader;
import com.crealmcode.effectrun.util.ParticleUtil;
import com.crealmcode.effectrun.util.Util;
import com.crealmcode.effectrun.util.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class PlayerCanonPlatformAction extends Platform {

    private Set<Player> lockedPlayers = new HashSet<Player>();
    private Game game;
    private Vector velocity;

    public PlayerCanonPlatformAction(Game game, Cuboid cuboid, Vector velocity) {

        super(cuboid);
        this.game = game;
        this.velocity = velocity;
    }

    @Override
    protected void onAction(Player player, Location from, Location to) {

        if(lockedPlayers.contains(player)){
            return;
        }

        lockedPlayers.add(player);
        new CanonTask(player, this).runTaskTimer(game.getPlugin(), 1l, 3l);
    }


    public static class CanonTask extends BukkitRunnable {

        private Player player;
        private AtomicInteger count = new AtomicInteger(0);
        private PlayerCanonPlatformAction platformAction;

        public CanonTask(Player player, PlayerCanonPlatformAction platformAction) {

            this.player = player;
            this.platformAction = platformAction;
            player.setWalkSpeed(0.0f);
        }

        @Override
        public void run() {

            ParticleUtil.display(WrapperPlayServerWorldParticles.ParticleEffect.CLOUD, new Vector(0.1 * count.get(), 0.15 * count.get(), 0.10 * count.get()), 0, 4 * count.get(), player.getLocation());

            if(count.get() < 12) {
                player.setVelocity(new Vector().zero());
            }

            if(count.get() == 16) {
                player.setVelocity(platformAction.velocity);
                player.playSound(player.getLocation(), Sound.IRONGOLEM_THROW, 1, 1);

            }


            if(count.get() >= 20) {
                cancel();
                platformAction.lockedPlayers.remove(player);
                player.setWalkSpeed(0.2f);
            }

            count.incrementAndGet();
        }
    }

    public static class PlayerCanonPlatformActionLoader implements ConfigurationLoader<PlayerCanonPlatformAction> {

        @Override
        public PlayerCanonPlatformAction load(Game game, ConfigurationSection section) {

            Cuboid cuboid = Util.parseCuboid(section.getString("cuboid"));
            Vector vector = Util.parseVector(section.getString("velocity"));

            return new PlayerCanonPlatformAction(game, cuboid, vector);
        }
    }
}
