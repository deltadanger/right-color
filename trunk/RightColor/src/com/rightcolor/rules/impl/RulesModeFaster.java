package com.rightcolor.rules.impl;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;

public class RulesModeFaster extends RulesBaseMode {

    protected final float TOTAL_TIMER = 5f;

    private final int NUMBER_TO_VALIDATE = 5;
    private final float TIMER_DECREASE = 0.1f;
    private float currentTotalTimer = TOTAL_TIMER;
    
    @Override
    public void buttonClicked(ColorButton button) {
        if (targetColor != null && targetColor.equals(button.getColor())) {
            score++;
            if (score % NUMBER_TO_VALIDATE == 0) {
                currentTotalTimer -= TIMER_DECREASE;
                timer = currentTotalTimer;
            }
            dispatchEvent(RulesSet.EVENT_ROUND_FINISHED);
            
        } else if (!targetColor.equals(button.getColor())) {
            dispatchEvent(RulesSet.EVENT_GAME_END_DEFEAT);
        }
    }
    
    @Override
    public float getTotalTime() {
        return currentTotalTimer;
    }

    @Override
    public String getPreferencesKey() {
        return "Faster";
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.FASTER;
    }
}
