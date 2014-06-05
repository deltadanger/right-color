package com.{templatelc};

import helper.AssetLoader;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRenderer {

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
        batcher.draw(AssetLoader.background, 0, 0, Utils.GAME_WIDTH, gameHeight);
        batcher.enableBlending();

        switch (world.getCurrentState()) {
        }
        
        batcher.end();
    }
    
    // Render examples:
    
    // Render asset: batcher.draw(AssetLoader.ball, world.getBall().getX(), world.getBall().getY(), BALL_SIZE, BALL_SIZE);
    
    // Render text:
    // String score = ""+world.getScore();
    // TextBounds b = AssetLoader.mainFont.getBounds(score);
    // AssetLoader.mainFont.draw(batcher, score, Utils.GAME_WIDTH - b.width - SCORE_POSITION_X, SCORE_POSITION_Y);
}
