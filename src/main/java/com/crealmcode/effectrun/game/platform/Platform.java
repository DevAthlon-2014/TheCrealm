package com.crealmcode.effectrun.game.platform;

import com.crealmcode.effectrun.game.action.CuboidAction;
import com.crealmcode.effectrun.util.cuboid.Cuboid;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public abstract class Platform extends CuboidAction {

    protected Platform(Cuboid cuboid) {

        super(cuboid);
    }
}
