package com.rightcolor;

import helper.AssetLoader;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rightcolor.rules.RulesSet;

public class GameRenderer {

    private static final int TIMER_POSITION_Y = 20;
    private static final int COLOR_POSITION_Y = 50;

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
        case RUNNING:
            renderRunning();
            break;
        case GAME_OVER:
            renderGameOver();
            break;
        }
        
        batcher.end();
    }
    
    private void renderRunning() {
        batcher.setColor(Color.LIGHT_GRAY);
        String time = String.format("%.1f", world.getCurrentRules().getRemainingTime());
        TextBounds b = AssetLoader.mainFont.getBounds(time);
        AssetLoader.mainFont.draw(batcher, time, Utils.GAME_WIDTH/2 - b.width/2, TIMER_POSITION_Y);
        
        batcher.setColor(world.getCurrentRules().getTextColor());
        String color = RulesSet.COLOR_NAME.get(world.getCurrentRules().getTargetColor());
        b = AssetLoader.mainFont.getBounds(color);
        AssetLoader.mainFont.draw(batcher, color, Utils.GAME_WIDTH/2 - b.width/2, COLOR_POSITION_Y);
        
        int buttonTop = (int) (gameHeight / 3f);
        int buttonHeight = (int) (gameHeight * 2f / 3f / 2f);

        batcher.setColor(world.getTopLeftColor());
        batcher.draw(AssetLoader.pixel, 0, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateTopLeft(0, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);

        batcher.setColor(world.getTopRightColor());
        batcher.draw(AssetLoader.pixel, Utils.GAME_WIDTH/2, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateTopRight(Utils.GAME_WIDTH/2, buttonTop, Utils.GAME_WIDTH/2, buttonHeight);

        batcher.setColor(world.getBottomLeftColor());
        batcher.draw(AssetLoader.pixel, 0, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateBottomLeft(0, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);

        batcher.setColor(world.getBottomRightColor());
        batcher.draw(AssetLoader.pixel, Utils.GAME_WIDTH/2, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);
        world.updateBottomRight(Utils.GAME_WIDTH/2, buttonTop + buttonHeight, Utils.GAME_WIDTH/2, buttonHeight);
    }
    
    private void renderGameOver() {
        if (world.getCurrentRules() != null) {
             String score = ""+world.getCurrentRules().getScore();
             TextBounds b = AssetLoader.mainFont.getBounds(score);
             AssetLoader.mainFont.draw(batcher, score, Utils.GAME_WIDTH/2 - b.width/2, gameHeight/2 - b.height/2);
        }
    }
}
