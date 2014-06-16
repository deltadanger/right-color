package com.rightcolor.rules.impl;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;

public class RulesModeSprint extends RulesBaseMode {

    protected final float TOTAL_TIMER = 20f;

    @Override
    public void buttonClicked(ColorButton button) {
        if (targetColor != null && targetColor.equals(button.getColor())) {
            score++;
            dispatchEvent(RulesSet.EVENT_ROUND_FINISHED);
            
        } else if (!targetColor.equals(button.getColor())) {
            dispatchEvent(RulesSet.EVENT_GAME_END_DEFEAT);
        }
    }

    @Override
    public String getPreferencesKey() {
        return "Sprint";
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.SPRINT;
    }
}
