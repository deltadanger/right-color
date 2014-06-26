package com.rightcolor.rules.impl;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;

class RulesModeFaster extends RulesBaseMode {

    private final int NUMBER_TO_VALIDATE = 1;
    private final float TIMER_DECREASE = 0.05f;
    private final float START_TOTAL_TIME = 1.5f;
    
    private float currentTotalTime = START_TOTAL_TIME;
    
    @Override
    public void buttonClicked(ColorButton button) {
        if (targetColor != null && targetColor.equals(button.getColor())) {
            score++;
            if (score % NUMBER_TO_VALIDATE == 0) {
                currentTotalTime -= TIMER_DECREASE;
                time = currentTotalTime;
            }
            dispatchEvent(RulesSet.EVENT_ROUND_FINISHED);
            
        } else if (!targetColor.equals(button.getColor())) {
            dispatchEvent(RulesSet.EVENT_GAME_END_DEFEAT);
        }
    }
    
    @Override
    public float getTotalTime() {
        if (currentTotalTime > 0) {
            return currentTotalTime;
        }
        return START_TOTAL_TIME;
    }
    
    @Override
    public GameMode getGameMode() {
        return GameMode.FASTER;
    }
}
