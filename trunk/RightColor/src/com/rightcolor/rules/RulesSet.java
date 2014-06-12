package com.rightcolor.rules;

import java.util.HashMap;
import java.util.Map;

import helper.EventDispatcher;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;

public abstract class RulesSet extends EventDispatcher {

    public static String EVENT_ROUND_FINISHED = "eventRoundFinished";
    public static String EVENT_GAME_END_VICTORY = "eventGameEndVictory";
    public static String EVENT_GAME_END_DEFEAT = "eventGameEndDefeat";
    
    protected Color targetColor;
    
    public static final Color[] AVAILABLE_COLORS = {
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.PINK
    };
    
    @SuppressWarnings("serial")
    public static final Map<Color, String> COLOR_NAME = new HashMap<Color, String>(){{
        this.put(AVAILABLE_COLORS[0], "Blue");
        this.put(AVAILABLE_COLORS[1], "Cyan");
        this.put(AVAILABLE_COLORS[2], "Green");
        this.put(AVAILABLE_COLORS[3], "Pink");
    }};

    public abstract Color generateNewTargetColor();

    public abstract void assignColorToButtons(ColorButton topLeft, ColorButton topRight,
            ColorButton bottomLeft, ColorButton bottomRight);

    public abstract void buttonClicked(ColorButton button);
    
    public abstract void decreaseTimer(float delta);
    
    public abstract float getRemainingTime();
    
    public abstract float getTotalTime();
    
    public abstract int getScore();
    
    public abstract String getPreferencesKey();
    
    public abstract Color getTextColor();
    
    public Color getTargetColor() {
        return targetColor;
    }
}
