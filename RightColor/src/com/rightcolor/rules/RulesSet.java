package com.rightcolor.rules;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;

public interface RulesSet extends RulesSetFromLevel, RulesSetFromMode {

    public static final String EVENT_ROUND_FINISHED = "eventRoundFinished";
    public static final String EVENT_GAME_END_VICTORY = "eventGameEndVictory";
    public static final String EVENT_GAME_END_DEFEAT = "eventGameEndDefeat";
    
    @SuppressWarnings("serial")
    public static final Map<Color, String> AVAILABLE_COLORS = new HashMap<Color, String>(){{
        this.put(Color.valueOf("D43131FF"), "Red");
        this.put(Color.valueOf("D967E0FF"), "Pink");
        this.put(Color.valueOf("495BE3FF"), "Blue");
        this.put(Color.valueOf("38D047FF"), "Green");
        this.put(Color.valueOf("ECEA64FF"), "Yellow");
        this.put(Color.valueOf("EFEFEFFF"), "White");
        this.put(Color.valueOf("645209FF"), "Brown");
        this.put(Color.valueOf("F1973FFF"), "Orange");
        this.put(Color.valueOf("B5B5B5FF"), "Grey");
    }};
}