package com.rightcolor.rules.impl;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSetFromLevel;

public class RulesLevel3 implements RulesSetFromLevel  {

    private final Color TEXT_COLOR = Color.WHITE;

    @Override
    public void assignColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        new RulesLevel1().assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public String getPreferencesKey() {
        return "Level3";
    }

    @Override
    public Color getTextColor() {
        return TEXT_COLOR;
    }
}
