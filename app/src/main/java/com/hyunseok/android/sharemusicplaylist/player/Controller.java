package com.hyunseok.android.sharemusicplaylist.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHS on 2017-04-12.
 */

public class Controller {
    private static Controller instance = null;
    List<ControlInterface> targets;

    private Controller(){
        targets = new ArrayList<>();
    }

    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

    public void addObserver(ControlInterface target) {
        targets.add(target);
    }

    public void play(){
        for(ControlInterface target : targets){
            target.startPlayer();
        }
    }

    public void pause(){
        for(ControlInterface target : targets){
            target.pausePlayer();
        }
    }

    public void remove(ControlInterface target){
        targets.remove(target);
    }
}
