package com.rightcolor.rules.impl;

import helper.Utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;

class RulesHelper {
    private static Color[] lastRandomColors = new Color[]{};
    public static Color[] getLastRandomColors() {
        return lastRandomColors;
    }
    
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
        
        lastRandomColors = assignedColor.toArray(new Color[]{});
        return lastRandomColors;
    }
}
