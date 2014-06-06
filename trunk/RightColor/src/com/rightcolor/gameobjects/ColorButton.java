package com.rightcolor.gameobjects;

import com.badlogic.gdx.graphics.Color;

import helper.ClickableZone;


public class ColorButton extends ClickableZone {
    
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
