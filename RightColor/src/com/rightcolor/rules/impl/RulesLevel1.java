package com.rightcolor.rules.impl;

import helper.Utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSetFromLevel;

public class RulesLevel1 implements RulesSetFromLevel  {

    private final Color TEXT_COLOR = Color.WHITE;
    
    public static Color[] assignRandomColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {

        ColorButton[] buttons = {topLeft, topRight, bottomLeft, bottomRight};
        ArrayList<Color> assignedColor = new ArrayList<Color>();
        
        for (int i = 0; i < buttons.length; i++) {
            ColorButton b = buttons[i];
            Color c = Utils.getRandomColor();
            while (assignedColor.contains(c)) {
                c = Utils.getRandomColor();
            }
            b.setColor(c);
            assignedColor.add(c);
        }
        
        return assignedColor.toArray(new Color[]{});
    }

    @Override
    public Color[] assignInitialColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {
        
        return assignRandomColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
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
