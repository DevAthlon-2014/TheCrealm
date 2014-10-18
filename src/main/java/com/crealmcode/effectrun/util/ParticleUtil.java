package com.crealmcode.effectrun.util;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class ParticleUtil {

    public static void display(WrapperPlayServerWorldParticles.ParticleEffect effect, Vector offset, float speed, int count, Location location, double range){

        List<Player> players = new ArrayList<Player>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.getLocation().distance(location) <= range){
                players.add(player);
            }
        };

        display(effect, offset, speed, count, location, players.toArray(new Player[players.size()]));
    }

    public static void display(WrapperPlayServerWorldParticles.ParticleEffect effect, Vector offset, float speed, int count, Location location){

        display(effect, offset, speed, count, location, Bukkit.getOnlinePlayers());
    }

    public static void display(WrapperPlayServerWorldParticles.ParticleEffect effect, Vector offset, float speed, int count, Location location, Player... players){

        WrapperPlayServerWorldParticles packet = new WrapperPlayServerWorldParticles();

        packet.setParticleEffect(effect);
        packet.setOffset(offset);
        packet.setParticleSpeed(speed);
        packet.setNumberOfParticles(count);
        packet.setLocation(location);

        for (Player player : players) {
            packet.sendPacket(player);
        }
    }

    public static void display(String name, Vector offset, float speed, int count, Location location) {

        WrapperPlayServerWorldParticles packet = new WrapperPlayServerWorldParticles();

        packet.setParticleName(name);
        packet.setOffset(offset);
        packet.setParticleSpeed(speed);
        packet.setNumberOfParticles(count);
        packet.setLocation(location);

        for (Player player : Bukkit.getOnlinePlayers()) {
            packet.sendPacket(player);
        };

    }
}
