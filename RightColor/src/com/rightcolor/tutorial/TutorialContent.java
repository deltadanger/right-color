package com.rightcolor.tutorial;

import helper.AssetLoader;
import helper.Utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public abstract class TutorialContent {
    public static float TEXT_POSITION_X = 10;
    public static float TEXT_SCALE = 0.15f;
    
    public void drawTutorialContent(SpriteBatch batcher, float gameHeight) {
        AssetLoader.mainFont.setScale(TEXT_SCALE, -TEXT_SCALE);
        
        String text = getTutorialText();
        float wrap = Utils.GAME_WIDTH-2*TEXT_POSITION_X;
        
        TextBounds b = AssetLoader.mainFont.getWrappedBounds(text, wrap);
        AssetLoader.mainFont.drawWrapped(batcher, text, TEXT_POSITION_X, gameHeight/2 + b.height/2, wrap, HAlignment.CENTER);
    }

    protected abstract String getTutorialText();
}
