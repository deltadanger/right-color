package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.rightcolor.GameWorld;

public class InputHandler implements InputProcessor {
    private GameWorld world;

    public InputHandler(GameWorld world) {
        this.world = world;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float ratio = 1f*Utils.GAME_WIDTH / Gdx.graphics.getWidth();
        world.onClick((int)(screenX*ratio), (int)(screenY*ratio));
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Keys.BACK){
            world.onBackClick();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}

