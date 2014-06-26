package com.rightcolor.rules.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.RulesSetFromLevel;
import com.rightcolor.rules.RulesSetFromMode;

public class RulesFactory {
    
    @SuppressWarnings("serial")
    List<Class <? extends RulesSetFromMode>> modes = new ArrayList<Class <? extends RulesSetFromMode>>() {{
        add(RulesModeEasyPeasy.class);
        add(RulesModeMarathon.class);
        add(RulesModeSprint.class);
        add(RulesModeFaster.class);
    }};
    
    @SuppressWarnings("serial")
    Map<Integer, Class <? extends RulesSetFromLevel>> levelToClass = new HashMap<Integer, Class <? extends RulesSetFromLevel>>() {{
        put(1, RulesLevel1.class);
        put(2, RulesLevel2.class);
        put(3, RulesLevel3.class);
        put(4, RulesLevel4.class);
    }};

    public RulesSet getNewRulesSet(GameMode mode, int level) {
        RulesSetFromLevel rulesFromLevel;
        try {
            RulesSetFromMode rulesFromMode = getRulesSetForMode(mode);
            rulesFromLevel = levelToClass.get(level).newInstance();
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
        
        new RulesLevel2().assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }
    
    public RulesSetFromLevel[] getLevelRules() {
        return new RulesSetFromLevel[]{
                new RulesLevel1(),
                new RulesLevel2(),
                new RulesLevel3(),
                new RulesLevel4()
        };
    }
    
    private RulesSetFromMode getRulesSetForMode(GameMode mode) throws InstantiationException, IllegalAccessException {
        for (Class<? extends RulesSetFromMode> rulesClass : modes) {
            RulesSetFromMode rules = rulesClass.newInstance();
            if (rules.getGameMode().equals(mode)) {
                return rules;
            }
        }
        return null;
    }
}
