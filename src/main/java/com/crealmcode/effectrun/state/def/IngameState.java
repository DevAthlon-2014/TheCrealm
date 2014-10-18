package com.crealmcode.effectrun.state.def;

import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.game.GameListener;
import com.crealmcode.effectrun.game.action.PlayerActionManager;
import com.crealmcode.effectrun.game.platform.def.DelayedPlayerWeightedPlatformAction;
import com.crealmcode.effectrun.game.platform.def.PlayerCanonPlatformAction;
import com.crealmcode.effectrun.game.platform.def.PlayerElevatorPlatform;
import com.crealmcode.effectrun.state.State;
import com.crealmcode.effectrun.state.StateDescription;
import com.crealmcode.effectrun.util.Util;
import com.crealmcode.effectrun.util.cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by TheCrealm on 18.10.2014.
 */
@StateDescription(name = "Ingame State")
public class IngameState extends State {

    private PlayerActionManager playerActionManager;

    public IngameState(Game game) {

        super(game);
    }

    @Override
    public void onEnter() {

        Bukkit.getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(Util.parseLocation(getGame().getPlugin().getConfig().getString("game.respawn"), true));
        }

        for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
            if(!(entity instanceof Player)){
                entity.remove();
            }
        }

        Bukkit.getPluginManager().registerEvents(new GameListener(getGame()), getGame().getPlugin());

        this.playerActionManager = new PlayerActionManager(getGame());

        //Loading DelayedPlayerWeightedPlatforms
        for (String target : getGame().getPlugin().getConfig().getConfigurationSection("game.platforms.player-weighted").getKeys(false)) {
            DelayedPlayerWeightedPlatformAction.DelayedPlayerWeightedPlatformActionLoader loader = new DelayedPlayerWeightedPlatformAction.DelayedPlayerWeightedPlatformActionLoader();
            playerActionManager.registerAction(loader.load(getGame(), getGame().getPlugin().getConfig().getConfigurationSection("game.platforms.player-weighted").getConfigurationSection(target)));
        }

        //Loading PlayerElevatorPlatforms
        for (String target : getGame().getPlugin().getConfig().getConfigurationSection("game.platforms.elevator").getKeys(false)) {
            PlayerElevatorPlatform.PlayerElevatorPlatformLoader loader = new PlayerElevatorPlatform.PlayerElevatorPlatformLoader();
            playerActionManager.registerAction(loader.load(getGame(), getGame().getPlugin().getConfig().getConfigurationSection("game.platforms.elevator").getConfigurationSection(target)));
        }

        //Loading PlayerCanonPlatforms
        for (String target : getGame().getPlugin().getConfig().getConfigurationSection("game.platforms.canon").getKeys(false)){
            PlayerCanonPlatformAction.PlayerCanonPlatformActionLoader loader = new PlayerCanonPlatformAction.PlayerCanonPlatformActionLoader();
            playerActionManager.registerAction(loader.load(getGame(), getGame().getPlugin().getConfig().getConfigurationSection("game.platforms.canon").getConfigurationSection(target)));
        }
    }

    @Override
    public void onLeave() {

    }
}
