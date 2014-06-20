package com.rightcolor.rules.impl;

import helper.Utils;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSetFromLevel;

public class RulesLevel3 implements RulesSetFromLevel  {
    
    private Color textColor = Color.WHITE;

    @Override
    public Color[] assignInitialColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        return new RulesLevel1().assignInitialColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public Color[] assignColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        return new RulesLevel1().assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public String getLevelPreferencesKey() {
        return "Level3";
    }

    @Override
    public void setNewTextColor() {
        textColor = Utils.getRandomColor();
    }
    
    @Override
    public Color getTextColor() {
        return textColor;
    }

    @Override
    public String getLevelName() {
        return "Level 3";
    }
}
