package com.crealmcode.effectrun.game;

import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class GameListener implements Listener {

    private Game game;

    public GameListener(Game game) {

        this.game = game;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        if(event.getTo().getY() < game.getPlugin().getConfig().getDouble("game.fall")){
            event.setTo(Util.parseLocation(game.getPlugin().getConfig().getString("game.respawn"), true));
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){

        event.setCancelled(true);
    }
}
