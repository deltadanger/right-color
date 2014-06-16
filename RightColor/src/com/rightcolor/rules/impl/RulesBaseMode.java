package com.rightcolor.rules.impl;

import helper.EventDispatcherImpl;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.RulesSetFromMode;

public abstract class RulesBaseMode extends EventDispatcherImpl implements RulesSetFromMode  {

    protected final float TOTAL_TIMER = 0;
    
    protected float timer = TOTAL_TIMER;
    protected int score = 0;
    
    private Random r = new Random();
    
    protected Color targetColor;

    @Override
    public Color generateNewTargetColor() {
        Color newColor = RulesSet.AVAILABLE_COLORS[r.nextInt(RulesSet.AVAILABLE_COLORS.length)];
        while (newColor.equals(targetColor)) {
            newColor = RulesSet.AVAILABLE_COLORS[r.nextInt(RulesSet.AVAILABLE_COLORS.length)];
        }
        targetColor = newColor;
        return targetColor;
    }

    @Override
    public void updateTimer(float delta) {
        timer -= delta;
        if (timer <= 0) {
            dispatchEvent(RulesSet.EVENT_GAME_END_VICTORY);
        }
    }

    @Override
    public float getRemainingTime() {
        return timer;
    }

    @Override
    public float getTotalTime() {
        return TOTAL_TIMER;
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
