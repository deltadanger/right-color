package com.rightcolor;

import helper.ClickableZone;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.rightcolor.comunication.ISocialNetworkAPI;

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

    private ClickableZone clickableBtn = new ClickableZone();
    
    private ISocialNetworkAPI facebook;
    private ISocialNetworkAPI twitter;

    public GameWorld(int gameHeight, ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
    	this.gameHeight = gameHeight;
    	this.facebook = facebook;
    	this.twitter = twitter;
    	
        initialisePreferences();
        
        reset();
        currentState = GameState.START;
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
            if (clickableBtn.isInside(x, y)) {
            }
            break;
            
	    case MENU:
            break;
            
	    case GAME_OVER:
            break;
            
	    case RUNNING:
            break;
	    }
    }
    
    public void updateButton(int x, int y, int width, int height) {
        clickableBtn.update(x, y, width, height);
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
