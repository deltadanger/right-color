package com.rightcolor.rules;

import helper.EventDispatcher;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;

public interface RulesSetFromMode extends EventDispatcher {

    public Color generateNewTargetColor();

    public void buttonClicked(ColorButton button);
    
    public void updateTimer(float delta);
    
    public float getRemainingTime();
    
    public float getTotalTime();
    
    public int getScore();
    
    public Color getTargetColor();

    public String getPreferencesKey();

    public GameMode getGameMode();
}
