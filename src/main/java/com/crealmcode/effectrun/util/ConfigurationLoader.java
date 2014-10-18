package com.crealmcode.effectrun.util;

import com.crealmcode.effectrun.Game;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public interface ConfigurationLoader<T> {

    public T load(Game game, ConfigurationSection section);
}
