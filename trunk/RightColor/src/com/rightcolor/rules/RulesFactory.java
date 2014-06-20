package com.rightcolor.rules;

import java.util.HashMap;
import java.util.Map;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.impl.Rules;
import com.rightcolor.rules.impl.RulesLevel1;
import com.rightcolor.rules.impl.RulesLevel2;
import com.rightcolor.rules.impl.RulesLevel3;
import com.rightcolor.rules.impl.RulesLevel4;
import com.rightcolor.rules.impl.RulesModeFaster;
import com.rightcolor.rules.impl.RulesModeMarathon;
import com.rightcolor.rules.impl.RulesModeSprint;

public class RulesFactory {
    
    @SuppressWarnings("serial")
    Map<GameMode, Class<? extends RulesSetFromMode>> modeToClass = new HashMap<GameMode, Class<? extends RulesSetFromMode>>() {{
        put(GameMode.SPRINT, RulesModeSprint.class);
        put(GameMode.MARATHON, RulesModeMarathon.class);
        put(GameMode.FASTER, RulesModeFaster.class);
    }};
    
    @SuppressWarnings("serial")
    Map<Integer, Class<? extends RulesSetFromLevel>> levelToClass = new HashMap<Integer, Class<? extends RulesSetFromLevel>>() {{
        put(1, RulesLevel1.class);
        put(2, RulesLevel2.class);
        put(3, RulesLevel3.class);
        put(4, RulesLevel4.class);
    }};

    public RulesSet getRulesSet(GameMode mode, int level) {
        try {
            RulesSetFromMode rulesFromMode = modeToClass.get(mode).newInstance();
            RulesSetFromLevel rulesFromLevel = levelToClass.get(level).newInstance();
            return new Rules(rulesFromLevel, rulesFromMode);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
         return null;
    }
    
    public void assignInitialColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        
        getRulesSet(GameMode.SPRINT, 2).assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }
}
