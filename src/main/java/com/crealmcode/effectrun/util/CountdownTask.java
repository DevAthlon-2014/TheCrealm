package com.crealmcode.effectrun.util;

import com.crealmcode.effectrun.GamePlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public abstract class CountdownTask extends BukkitRunnable {

    private GamePlugin plugin;
    private BukkitTask task;
    private final int initValue;
    private AtomicInteger current;

    public CountdownTask(GamePlugin plugin, int initValue) {

        this.plugin = plugin;
        this.initValue = initValue;
    }

    public void setRunning(boolean running) {

        if(running){
            if(task != null){
                task.cancel();
            }

            current = new AtomicInteger(initValue);
            task = runTaskTimer(plugin, 0, 20l);
        }
    }

    public void setCurrentTime(int newValue){

        current.set(newValue);
    }

    @Override
    public void run() {

        if(current.get() <= 0){
            cancel();
            onFinish();
        }

        onTick(current.get());
        current.decrementAndGet();
    }

    protected void onTick(int tick){

        //Do nothing here..
    }

    protected abstract void onFinish();
}
