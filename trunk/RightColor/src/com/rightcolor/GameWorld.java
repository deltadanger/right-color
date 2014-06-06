package com.rightcolor;

import helper.ClickableZone;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.rightcolor.comunication.ISocialNetworkAPI;
import com.rightcolor.gameobjects.ColorButton;

public class GameWorld {
    
    public static final String PREFERENCES_NAME = "com.rightcolor";
    public static final String PREFERENCE_KEY = "key";
    
    public static final float MAX_POSSIBLE_DELTA = .15f;
    
    public static enum GameState {
    	START,
        MENU,
    	RUNNING,
    	GAME_OVER
    }
    
    private Random r = new Random();
    
    private int gameHeight;
    
    private int score;
    private GameState currentState;

	private Preferences preferences;

    private ClickableZone topLeft = new ColorButton();
    private ClickableZone topRight = new ColorButton();
    private ClickableZone bottomLeft = new ColorButton();
    private ClickableZone bottomRight = new ColorButton();
    
    private ISocialNetworkAPI facebook;
    private ISocialNetworkAPI twitter;

    public GameWorld(int gameHeight, ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
    	this.gameHeight = gameHeight;
    	this.facebook = facebook;
    	this.twitter = twitter;
    	
//        initialisePreferences();
        
        reset();
//        currentState = GameState.START;
        currentState = GameState.RUNNING;
    }
    
    private void initialisePreferences() {
        boolean flush = false;
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        if (!preferences.contains(PREFERENCE_KEY)) {
        	preferences.putInteger(PREFERENCE_KEY, 0);
            flush = true;
        }

        if (flush) {
            preferences.flush();
        }

        // value = preferences.getInteger(PREFERENCE_KEY);
    }
    
    public void reset() {
        
    }

    public void update(float delta) {
    	if (!GameState.RUNNING.equals(currentState)) {
    		return;
    	}

        if (delta > MAX_POSSIBLE_DELTA) {
            delta = MAX_POSSIBLE_DELTA;
        }
    	
        // elements.update(delta);
    }

    /**
     * To be called from InputHandler
     */
	public void onClick(int x, int y) {
	    switch (currentState) {
	    
	    case START:
            break;
            
	    case MENU:
            break;
            
	    case GAME_OVER:
            break;
            
	    case RUNNING:
            if (topLeft.isInside(x, y)) {
                
            } else if (topRight.isInside(x, y)) {
                
            } else if (bottomLeft.isInside(x, y)) {
                
            } else if (bottomRight.isInside(x, y)) {
                
            }
            break;
	    }
    }
    
    public void updateTopLeft(int x, int y, int width, int height) {
        topLeft.update(x, y, width, height);
    }
    
    public void updateTopRight(int x, int y, int width, int height) {
        topRight.update(x, y, width, height);
    }
    
    public void updateBottomLeft(int x, int y, int width, int height) {
        bottomLeft.update(x, y, width, height);
    }
    
    public void updateBottomRight(int x, int y, int width, int height) {
        bottomRight.update(x, y, width, height);
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
