package com.rightcolor;

import helper.AssetLoader;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightcolor.rules.RulesSet;

public class GameRenderer {

    private static final int TIMER_POSITION_Y = 20;
    private static final int COLOR_POSITION_Y = 50;
    private static final float TITLE_POSITION = 0.05f;
    private static final float TITLE_POSITION_2 = 0.2f;
    private static final int TITLE_2_WIDTH = 100;
    private static final float LEVEL_POSITION_Y = 0.7f;
    private static final int LEVEL_TEXT_MARGIN = 10;
    private static final float LEVEL_BUTTON_HEIGHT = 0.2f;
    private static final float LEVEL_BUTTON_TEXT_PADDING = 3;

    private GameWorld world;
    private OrthographicCamera cam;
    private SpriteBatch batcher;
    
    private int gameHeight;
    
    public GameRenderer(GameWorld world, int gameHeight) {
        this.world = world;
        this.gameHeight = gameHeight;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Utils.GAME_WIDTH, gameHeight);
        
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        batcher.begin();
        batcher.disableBlending();
        batcher.setColor(Color.BLACK);
        batcher.draw(AssetLoader.pixel, 0, 0, Utils.GAME_WIDTH, gameHeight);
        batcher.enableBlending();

        switch (world.getCurrentState()) {
        case MENU:
            renderMenu();
            break;
        case RUNNING:
            renderRunning();
            break;
        case GAME_OVER:
            renderGameOver();
            break;
        }
        
        batcher.end();
    }
    
    private void renderMenu() {
        TextBounds b;
        batcher.setColor(Color.WHITE);
        int availableSpace = draw4buttons(true);

        batcher.setColor(Color.LIGHT_GRAY);
        
        String title = "Pick the right";
        b = AssetLoader.mainFont.getBounds(title);
        AssetLoader.mainFont.draw(batcher, title, Utils.GAME_WIDTH/2 - b.width/2, availableSpace * TITLE_POSITION);

        int width = TITLE_2_WIDTH;
        int height = width * AssetLoader.textColor.getRegionHeight() / AssetLoader.textColor.getRegionWidth();
        batcher.draw(AssetLoader.textColor, Utils.GAME_WIDTH/2 - width/2, availableSpace * TITLE_POSITION_2, width, height);
        

        int buttonHeight = (int) (availableSpace * LEVEL_BUTTON_HEIGHT);
        
        String level = "Level:";
        b = AssetLoader.mainFont.getBounds(level);
        AssetLoader.mainFont.draw(batcher, level, 0, availableSpace * LEVEL_POSITION_Y + buttonHeight/2 + b.height/2); // b.height < 0

        int buttonWidth = (int) (Utils.GAME_WIDTH - b.width - LEVEL_TEXT_MARGIN) / 4;
        
        TextureRegion[] buttons = {AssetLoader.levelButton, AssetLoader.levelButton, AssetLoader.levelButton, AssetLoader.levelButton};
        buttons[world.getLevel()-1] = AssetLoader.levelButtonActive;
        int buttonLeft = (int) (b.width + LEVEL_TEXT_MARGIN);
        
        batcher.setColor(Color.WHITE);
        for (int i = 0; i < 4; i++) {
            batcher.draw(buttons[i], buttonLeft, availableSpace * LEVEL_POSITION_Y, buttonWidth, buttonHeight);
            level = ""+(i+1);
            b = AssetLoader.mainFont.getBounds(level);
            AssetLoader.mainFont.draw(batcher, level, buttonLeft + buttonWidth/2 - b.width/2,
                    availableSpace * LEVEL_POSITION_Y + LEVEL_BUTTON_TEXT_PADDING);
            buttonLeft += buttonWidth;
        }
    }
    
    private void renderRunning() {
        draw4buttons();
        
        batcher.setColor(Color.LIGHT_GRAY);
        String time = String.format("%.1f", world.getCurrentRules().getRemainingTime());
        TextBounds b = AssetLoader.mainFont.getBounds(time);
        AssetLoader.mainFont.draw(batcher, time, Utils.GAME_WIDTH/2 - b.width/2, TIMER_POSITION_Y);
        
        batcher.setColor(world.getCurrentRules().getTextColor());
        String color = RulesSet.COLOR_NAME.get(world.getCurrentRules().getTargetColor());
        b = AssetLoader.mainFont.getBounds(color);
        AssetLoader.mainFont.draw(batcher, color, Utils.GAME_WIDTH/2 - b.width/2, COLOR_POSITION_Y);
    }
    
    private int draw4buttons() {
        return draw4buttons(false);
    }
    
    private int draw4buttons(boolean withText) {
        String text;
        TextBounds b;
        
        int buttonTop = (int) (gameHeight / 3f);
        int buttonHeight = (int) (gameHeight * 2f / 3f / 2f);

        batcher.setColor(world.getTopLeftColor());
        batcher.draw(AssetLoader.pixel, 0, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateTopLeft(0, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);
        if (withText) {
            text = world.getTopLeftText();
            b = AssetLoader.mainFont.getBounds(text);
            AssetLoader.mainFont.draw(batcher, text, Utils.GAME_WIDTH/4 - b.width/2,
                    buttonTop + buttonHeight/2 + b.height/2); // b.height < 0
        }

        batcher.setColor(world.getTopRightColor());
        batcher.draw(AssetLoader.pixel, Utils.GAME_WIDTH/2, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateTopRight(Utils.GAME_WIDTH/2, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);
        if (withText) {
            text = world.getTopRightText();
            b = AssetLoader.mainFont.getBounds(text);
            AssetLoader.mainFont.draw(batcher, text, Utils.GAME_WIDTH/2 + Utils.GAME_WIDTH/4 - b.width/2,
                    buttonTop + buttonHeight/2 + b.height/2); // b.height < 0
        }

        batcher.setColor(world.getBottomLeftColor());
        batcher.draw(AssetLoader.pixel, 0, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateBottomLeft(0, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);
        if (withText) {
            text = world.getBottomLeftText();
            b = AssetLoader.mainFont.getBounds(text);
            AssetLoader.mainFont.draw(batcher, text, Utils.GAME_WIDTH/4 - b.width/2,
                    buttonTop + buttonHeight + buttonHeight/2 + b.height/2); // b.height < 0
        }

        batcher.setColor(world.getBottomRightColor());
        batcher.draw(AssetLoader.pixel, Utils.GAME_WIDTH/2, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateBottomRight(Utils.GAME_WIDTH/2, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);
        if (withText) {
            text = world.getBottomRightText();
            b = AssetLoader.mainFont.getBounds(text);
            AssetLoader.mainFont.draw(batcher, text, Utils.GAME_WIDTH/2 + Utils.GAME_WIDTH/4 - b.width/2,
                    buttonTop + buttonHeight + buttonHeight/2 + b.height/2); // b.height < 0
        }
        
        return gameHeight - buttonHeight*2;
    }
    
    private void renderGameOver() {
        if (world.getCurrentRules() != null) {
             String score = ""+world.getCurrentRules().getScore();
             TextBounds b = AssetLoader.mainFont.getBounds(score);
             AssetLoader.mainFont.draw(batcher, score, Utils.GAME_WIDTH/2 - b.width/2, gameHeight/2 - b.height/2);
        }
    }
}
