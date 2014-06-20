package com.rightcolor.rules.impl;

import helper.Callback;
import helper.EventDispatcher;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.RulesSetFromLevel;
import com.rightcolor.rules.RulesSetFromMode;

public class Rules implements RulesSet, EventDispatcher {

    private RulesSetFromLevel rulesFromLevel;
    private RulesSetFromMode rulesFromMode;
    
    public Rules(RulesSetFromLevel rulesFromLevel, RulesSetFromMode rulesFromMode) {
        this.rulesFromLevel = rulesFromLevel;
        this.rulesFromMode = rulesFromMode;
    }

    @Override
    public Color[] assignInitialColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        return rulesFromLevel.assignInitialColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public Color[] assignColorToButtons(ColorButton topLeft, ColorButton topRight,
            ColorButton bottomLeft, ColorButton bottomRight) {
        return rulesFromLevel.assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public void setNewTextColor() {
        rulesFromLevel.setNewTextColor();
    }

    @Override
    public Color getTextColor() {
        return rulesFromLevel.getTextColor();
    }

    @Override
    public String getLevelPreferencesKey() {
        return rulesFromLevel.getLevelPreferencesKey();
    }

    @Override
    public String getLevelName() {
        return rulesFromLevel.getLevelName();
    }

    @Override
    public Color generateNewTargetColor(Color[] availableColors) {
        return rulesFromMode.generateNewTargetColor(availableColors);
    }

    @Override
    public void buttonClicked(ColorButton button) {
        rulesFromMode.buttonClicked(button);
    }

    @Override
    public void updateTimer(float delta) {
        rulesFromMode.updateTimer(delta);
    }

    @Override
    public float getRemainingTime() {
        return rulesFromMode.getRemainingTime();
    }

    @Override
    public float getTotalTime() {
        return rulesFromMode.getTotalTime();
    }

    @Override
    public int getScore() {
        return rulesFromMode.getScore();
    }

    @Override
    public Color getTargetColor() {
        return rulesFromMode.getTargetColor();
    }

    @Override
    public GameMode getGameMode() {
        return rulesFromMode.getGameMode();
    }

    @Override
    public void addEventListener(String event, Callback callback) {
        rulesFromMode.addEventListener(event, callback);
    }

    @Override
    public void removeEventListener(String event, Callback callback) {
        rulesFromMode.removeEventListener(event, callback);
    }

    @Override
    public void clearEventListeners(String event) {
        rulesFromMode.clearEventListeners(event);
    }

    @Override
    public void replaceEventListeners(String event, Callback callback) {
        rulesFromMode.replaceEventListeners(event, callback);
    }

    @Override
    public void dispatchEvent(String event) {
        rulesFromMode.dispatchEvent(event);
    }
}
