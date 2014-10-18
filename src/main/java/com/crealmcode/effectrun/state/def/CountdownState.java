package com.crealmcode.effectrun.state.def;

import com.crealmcode.effectrun.Game;
import com.crealmcode.effectrun.state.State;
import com.crealmcode.effectrun.state.StateDescription;
import com.crealmcode.effectrun.util.CountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by TheCrealm on 18.10.2014.
 */

@StateDescription(name = "Countdown State")
public class CountdownState extends State {

    private CountdownTask task;

    public CountdownState(Game game) {

        super(game);
    }

    @Override
    public void onEnter() {

        task = new CountdownTask(getGame().getPlugin(), 30) {

            @Override
            protected void onFinish() {
                getGame().nextState();
            }

            @Override
            protected void onTick(int tick) {

                if(tick %5 == 0 || tick <= 3){
                    Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "EffectRun" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Das Spiel startet in " + ChatColor.GOLD + tick + ChatColor.YELLOW + (tick == 0 ? " Sekunde" : " Sekunden"));
                }
            }
        };

        task.setRunning(true);
    }

    @Override
    public void onLeave() {

    }
}
