package com.rightcolor.rules;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;

public interface RulesSetFromLevel {

    public void assignColorToButtons(ColorButton topLeft, ColorButton topRight,
            ColorButton bottomLeft, ColorButton bottomRight);

    public Color getTextColor();

    public String getPreferencesKey();
}
