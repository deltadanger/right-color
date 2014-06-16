package com.rightcolor.rules.impl;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSetFromLevel;

public class RulesLevel4 implements RulesSetFromLevel  {

    @Override
    public void assignColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        new RulesLevel2().assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public String getPreferencesKey() {
        return "Level4";
    }

    @Override
    public Color getTextColor() {
        return new RulesLevel3().getTextColor();
    }
}
