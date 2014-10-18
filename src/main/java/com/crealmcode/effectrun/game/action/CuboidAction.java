package com.crealmcode.effectrun.game.action;

import com.crealmcode.effectrun.util.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public abstract class CuboidAction {

    private final Cuboid cuboid;

    public CuboidAction(Cuboid cuboid) {

        this.cuboid = cuboid;
    }

    public Cuboid getCuboid() {

        return cuboid;
    }

    //----->>> Implement by Action <<<-----

    protected abstract void onAction(Player player, Location from, Location to);
}
