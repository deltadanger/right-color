package com.rightcolor.rules;

import helper.Callback;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;

public abstract class RuleSet {
    
    protected Color targetColor;
    protected Callback onWin;
    protected Callback onLoss;
    
    public static final Color[] AVAILABLE_COLORS = {
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.PINK
    };
    public abstract Color getNewTargetColor();

    public abstract void assignColorToButtons(ColorButton topLeft, ColorButton topRight,
            ColorButton bottomLeft, ColorButton bottomRight);

    public abstract void buttonClicked(ColorButton button);

    public void setOnWin(Callback callback) {
        this.onWin = callback;
    }
    
    public void setOnLoss(Callback callback) {
        this.onLoss = callback;
    }
    
}
