package com.rightcolor.rules;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.gameobjects.ColorButton;

public class RulesFactory {

    public RulesSet getRulesSet(GameMode mode, int level) {
        return new RulesClassic();
    }
    
    public void assignInitialColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        
        new RulesClassic().assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }
}
