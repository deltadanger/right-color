package com.rightcolor.rules.impl;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSetFromLevel;

public class RulesLevel1 implements RulesSetFromLevel  {

    private final Color TEXT_COLOR = Color.WHITE;

    @Override
    public Color[] assignInitialColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        
        return RulesHelper.assignRandomColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public Color[] assignColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {

        return new Color[]{topLeft.getColor(), topRight.getColor(), bottomLeft.getColor(), bottomRight.getColor()};
    }

    @Override
    public String getLevelPreferencesKey() {
        return "Level1";
    }

    @Override
    public Color getTextColor() {
        return TEXT_COLOR;
    }

    @Override
    public void setNewTextColor() {}

    @Override
    public String getLevelName() {
        return "Level 1";
    }
}
