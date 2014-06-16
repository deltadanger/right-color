package com.rightcolor.rules.impl;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.RulesSetFromLevel;

public class RulesLevel2 implements RulesSetFromLevel  {

    @Override
    public void assignColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {

        topLeft.setColor(RulesSet.AVAILABLE_COLORS[0]);
        topRight.setColor(RulesSet.AVAILABLE_COLORS[1]);
        bottomLeft.setColor(RulesSet.AVAILABLE_COLORS[2]);
        bottomRight.setColor(RulesSet.AVAILABLE_COLORS[3]);
    }

    @Override
    public String getPreferencesKey() {
        return "Level2";
    }

    @Override
    public Color getTextColor() {
        return new RulesLevel1().getTextColor();
    }
}
