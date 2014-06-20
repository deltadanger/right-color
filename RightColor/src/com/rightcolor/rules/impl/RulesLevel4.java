package com.rightcolor.rules.impl;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSetFromLevel;

public class RulesLevel4 implements RulesSetFromLevel  {
    
    RulesLevel3 level3instance = new RulesLevel3();

    @Override
    public Color[] assignInitialColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        return new RulesLevel2().assignInitialColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public Color[] assignColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        return new RulesLevel2().assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public String getLevelPreferencesKey() {
        return "Level4";
    }

    @Override
    public Color getTextColor() {
        return level3instance.getTextColor();
    }

    @Override
    public void setNewTextColor() {
        level3instance.setNewTextColor();
    }

    @Override
    public String getLevelName() {
        return "Level 4";
    }
}
