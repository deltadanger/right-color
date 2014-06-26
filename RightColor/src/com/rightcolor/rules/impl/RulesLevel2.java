package com.rightcolor.rules.impl;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSetFromLevel;

class RulesLevel2 implements RulesSetFromLevel  {

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
        return RulesHelper.assignRandomColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public String getLevelPreferencesKey() {
        return "Level2";
    }

    @Override
    public void setNewTextColor() {}

    @Override
    public Color getTextColor() {
        return new RulesLevel1().getTextColor();
    }

    @Override
    public String getLevelName() {
        return "Level 2";
    }
}
