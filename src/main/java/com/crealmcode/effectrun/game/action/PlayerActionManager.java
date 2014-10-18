package com.crealmcode.effectrun.game.action;

import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.GamePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class PlayerActionManager implements Listener {

    private List<CuboidAction> actions = new ArrayList<CuboidAction>();

    public PlayerActionManager(Game game) {

        game.getPlugin().getServer().getPluginManager().registerEvents(this, game.getPlugin());
    }

    public void registerAction(CuboidAction action){

        actions.add(action);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        for (CuboidAction action : actions) {
            if(action.getCuboid().intersects(event.getTo()) || action.getCuboid().intersects(event.getFrom())){
                action.onAction(event.getPlayer(), event.getFrom(), event.getTo());
            }
        }
    }
}
