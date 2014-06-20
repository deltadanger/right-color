package com.rightcolor.rules.impl;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;

public class RulesModeMarathon extends RulesBaseMode {

    private final float TOTAL_TIME = 10f;
    private final int NUMBER_TO_VALIDATE = 10;
    
    @Override
    public void buttonClicked(ColorButton button) {
        if (targetColor != null && targetColor.equals(button.getColor())) {
            score++;
            if (score % NUMBER_TO_VALIDATE == 0) {
                time = getTotalTime();
            }
            dispatchEvent(RulesSet.EVENT_ROUND_FINISHED);
            
        } else if (!targetColor.equals(button.getColor())) {
            dispatchEvent(RulesSet.EVENT_GAME_END_DEFEAT);
        }
    }
    
    @Override
    public float getTotalTime() {
        return TOTAL_TIME;
    }
    
    @Override
    public GameMode getGameMode() {
        return GameMode.MARATHON;
    }
    
    @Override
    public String getPreferencesKey() {
        return "Marathon";
    }
}