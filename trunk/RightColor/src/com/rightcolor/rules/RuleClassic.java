package com.rightcolor.rules;

import helper.Callback;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;

public class RuleClassic extends RuleSet {
    
    private Random r = new Random();
    
    @Override
    public Color getNewTargetColor() {
        targetColor = AVAILABLE_COLORS[r.nextInt(AVAILABLE_COLORS.length)]; 
        return targetColor;
    }

    @Override
    public void assignColorToButtons(ColorButton topLeft,
            ColorButton topRight, ColorButton bottomLeft,
            ColorButton bottomRight) {

        topLeft.setColor(AVAILABLE_COLORS[0]);
        topRight.setColor(AVAILABLE_COLORS[1]);
        bottomLeft.setColor(AVAILABLE_COLORS[2]);
        bottomRight.setColor(AVAILABLE_COLORS[3]);
    }

    @Override
    public void buttonClicked(ColorButton button) {
        if (onWin != null && targetColor != null && targetColor.equals(button.getColor())) {
            onWin.call();
        }
    }
}
