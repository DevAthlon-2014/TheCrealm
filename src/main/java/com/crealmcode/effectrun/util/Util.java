package com.crealmcode.effectrun.util;

import com.crealmcode.effectrun.util.cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class Util {

    public static Location parseLocation(String rawLocation, boolean yawAndPitch) {

        String[] parts = rawLocation.split(";");

        World world = Bukkit.getWorld(parts[0]);
        Double x = Double.parseDouble(parts[1]);
        Double y = Double.parseDouble(parts[2]);
        Double z = Double.parseDouble(parts[3]);

        float yaw = 0f;
        float pitch = 0f;

        if(yawAndPitch){
            yaw = Float.parseFloat(parts[4]);
            pitch = Float.parseFloat(parts[5]);
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Vector parseVector(String rawVector){

        String[] parts = rawVector.split(";");

        Double x = Double.parseDouble(parts[0]);
        Double y = Double.parseDouble(parts[1]);
        Double z = Double.parseDouble(parts[2]);

        return new Vector(x, y, z);
    }

    public static Cuboid parseCuboid(String rawCuboid){

        String[] parts = rawCuboid.split(";");

        Double x1 = Double.parseDouble(parts[0]);
        Double y1 = Double.parseDouble(parts[1]);
        Double z1 = Double.parseDouble(parts[2]);
        Double x2 = Double.parseDouble(parts[3]);
        Double y2 = Double.parseDouble(parts[4]);
        Double z2 = Double.parseDouble(parts[5]);

        return new Cuboid(new Vector(x1, y1, z1), new Vector(x2, y2, z2));
    }
}
