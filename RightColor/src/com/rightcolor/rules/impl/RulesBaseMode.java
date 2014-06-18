package com.rightcolor.rules.impl;

import helper.EventDispatcherImpl;
import helper.Utils;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.RulesSetFromMode;

public abstract class RulesBaseMode extends EventDispatcherImpl implements RulesSetFromMode  {

    protected float time = getTotalTime();
    protected int score = 0;
    
    protected Color targetColor;

    @Override
    public Color generateNewTargetColor(Color[] availableColors) {
        Color newColor = Utils.getRandomColor(availableColors);
        while (newColor.equals(targetColor)) {
            newColor = Utils.getRandomColor(availableColors);
        }
        targetColor = newColor;
        return targetColor;
    }

    @Override
    public void updateTimer(float delta) {
        time -= delta;
        if (time <= 0) {
            dispatchEvent(RulesSet.EVENT_GAME_END_VICTORY);
        }
    }

    @Override
    public float getRemainingTime() {
        return time;
    }

    @Override
    public float getTotalTime() {
        return 0;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public Color getTargetColor() {
        return targetColor;
    }
}
