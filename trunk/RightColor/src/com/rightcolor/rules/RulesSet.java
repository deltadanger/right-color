package com.rightcolor.rules;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;

public interface RulesSet extends RulesSetFromLevel, RulesSetFromMode {

    public static final String EVENT_ROUND_FINISHED = "eventRoundFinished";
    public static final String EVENT_GAME_END_VICTORY = "eventGameEndVictory";
    public static final String EVENT_GAME_END_DEFEAT = "eventGameEndDefeat";
    
    public static final Color[] AVAILABLE_COLORS = {
        Color.BLUE.sub(0.1f, 0.1f, 0.1f, 0),
        Color.CYAN.sub(0.1f, 0.1f, 0.1f, 0),
        Color.GREEN.sub(0.1f, 0.1f, 0.1f, 0),
        Color.PINK.sub(0.1f, 0.1f, 0.1f, 0)
    };
    
    @SuppressWarnings("serial")
    public static final Map<Color, String> COLOR_NAME = new HashMap<Color, String>(){{
        this.put(AVAILABLE_COLORS[0], "Blue");
        this.put(AVAILABLE_COLORS[1], "Cyan");
        this.put(AVAILABLE_COLORS[2], "Green");
        this.put(AVAILABLE_COLORS[3], "Pink");
    }};
}
