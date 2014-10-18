package com.crealmcode.effectrun;

import com.crealmcode.effectrun.state.State;
import com.crealmcode.effectrun.state.def.CountdownState;
import com.crealmcode.effectrun.state.def.IngameState;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class Game {

    private GamePlugin plugin;

    private State currentState;
    private List<State> states = new ArrayList<State>();
    private ListIterator<State> stateIterator;

    public Game(GamePlugin plugin) {

        this.plugin = plugin;

        //Registering States
        states.add(new CountdownState(this));
        states.add(new IngameState(this));

        //Init state iteration process
        stateIterator = states.listIterator();
        currentState = stateIterator.next();
        currentState.onEnter();
    }

    public GamePlugin getPlugin() {

        return plugin;
    }

    public void nextState(){

        if(stateIterator.hasNext()){
            currentState.onLeave();
            currentState = stateIterator.next();
            currentState.onEnter();
        } else {
            //Game finished
        }
    }
}
