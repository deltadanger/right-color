package com.rightcolor.rules;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.gameobjects.ColorButton;

public interface RulesSetFromLevel {

    public Color[] assignInitialColorToButtons(ColorButton topLeft, ColorButton topRight,
            ColorButton bottomLeft, ColorButton bottomRight);

    public Color[] assignColorToButtons(ColorButton topLeft, ColorButton topRight,
            ColorButton bottomLeft, ColorButton bottomRight);

    public void setNewTextColor();
    public Color getTextColor();

    public String getPreferencesKey();
}
