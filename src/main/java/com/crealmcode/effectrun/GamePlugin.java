package com.crealmcode.effectrun;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class GamePlugin extends JavaPlugin {

    private FileConfiguration config;
    private Game game;

    @Override
    public void onEnable() {

        //Loading stuff
        config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/config.yml"));

        //Init game
        game = new Game(this);
    }

    @Override
    public FileConfiguration getConfig() {

        return config;
    }
}
