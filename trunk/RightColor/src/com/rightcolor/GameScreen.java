package com.rightcolor;

import helper.InputHandler;
import helper.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.rightcolor.comunication.ISocialNetworkAPI;

public class GameScreen implements Screen {
    
    private GameWorld world;
    private GameRenderer renderer;
    
    public GameScreen(ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
        int gameHeight = (int) (Utils.GAME_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        
        world = new GameWorld(gameHeight, facebook, twitter);
        renderer = new GameRenderer(world, gameHeight);
        
        Gdx.input.setInputProcessor(new InputHandler(world));
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}