package com.crealmcode.effectrun.state;

import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.state.exception.StateException;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public abstract class State {

    private Game game;

    public State(Game game) {

        this.game = game;
    }

    protected Game getGame() {

        return game;
    }

    public String getName(){

        if(getClass().isAnnotationPresent(StateDescription.class)){
            return getClass().getAnnotation(StateDescription.class).name();
        } else {
            throw new StateException("Could not retrieve name for class '" + getClass().getSimpleName() + "'");
        }
    }

    //----->>> Implement by running State <<<-----

    public abstract void onEnter();

    public abstract void onLeave();

}
