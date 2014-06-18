package com.rightcolor.gameobjects;

import helper.ClickableZone;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.GameWorld.GameMode;


public class ColorButton extends ClickableZone {
    public static final int BUTTON_CLICK_ZONE_PADDING = 0;
    
    private Color color;
    private GameMode mode;

    public ColorButton(GameMode mode) {
        this.mode = mode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }
    
}
