package com.crealmcode.effectrun.util.cuboid;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class Cuboid {

    private final Vector vectorMin;
    private final Vector vectorMax;

    public Cuboid(Vector vectorOne, Vector vectorTwo) {

        this.vectorMin = Vector.getMinimum(vectorOne, vectorTwo);
        this.vectorMax = Vector.getMaximum(vectorOne, vectorTwo);
    }

    public Vector getVectorMin() {

        return vectorMin;
    }

    public Vector getVectorMax() {

        return vectorMax;
    }

    public boolean intersects(Player player){

        return intersects(player.getLocation());
    }

    public boolean intersects(Location location) {

        return intersects(location.toVector());
    }

    public boolean intersects(Vector vector){

        return vector.isInAABB(vectorMin, vectorMax);
    }
}
