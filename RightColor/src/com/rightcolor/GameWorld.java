package com.rightcolor;

import helper.Callback;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.rightcolor.comunication.ISocialNetworkAPI;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesClassic;
import com.rightcolor.rules.RulesSet;

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
    
    private GameState currentState;

	private Preferences preferences;
	
    private ColorButton topLeft = new ColorButton();
    private ColorButton topRight = new ColorButton();
    private ColorButton bottomLeft = new ColorButton();
    private ColorButton bottomRight = new ColorButton();
    private RulesSet currentRules;
    
    private ISocialNetworkAPI facebook;
    private ISocialNetworkAPI twitter;

    public GameWorld(ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
    	this.facebook = facebook;
    	this.twitter = twitter;
    	
//        initialisePreferences();
        
        resetGame(new RulesClassic());
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
    
    public void resetGame(RulesSet ruleSet) {
        currentRules = ruleSet;
        currentRules.replaceEventListeners(RulesSet.EVENT_ROUND_FINISHED, initialiseNewRound);
        currentRules.replaceEventListeners(RulesSet.EVENT_GAME_END_VICTORY, onVictory);
        currentRules.replaceEventListeners(RulesSet.EVENT_GAME_END_DEFEAT, onDefeat);
        initialiseNewRound.call();
    }
    
    private Callback initialiseNewRound = new Callback() {
        
        @Override
        public Object call() {
            currentRules.generateNewTargetColor();
            currentRules.assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
            return null;
        }
    };
    
    private Callback onVictory = new Callback() {
        
        @Override
        public Object call() {
            GameWorld.this.currentState = GameState.GAME_OVER;
            return null;
        }
    };
    
    private Callback onDefeat = new Callback() {
        
        @Override
        public Object call() {
            GameWorld.this.currentState = GameState.GAME_OVER;
            return null;
        }
    };

    public void update(float delta) {
    	if (!GameState.RUNNING.equals(currentState)) {
    		return;
    	}

        if (delta > MAX_POSSIBLE_DELTA) {
            delta = MAX_POSSIBLE_DELTA;
        }
    	
        currentRules.decreaseTimer(delta);
    }

	public void onClick(int x, int y) {
	    switch (currentState) {
	    
	    case START:
            break;
            
	    case MENU:
            break;
            
	    case GAME_OVER:
	        resetGame(new RulesClassic());
	        this.currentState = GameState.RUNNING;
            break;
            
	    case RUNNING:
            if (topLeft.isInside(x, y)) {
                currentRules.buttonClicked(topLeft);
                
            } else if (topRight.isInside(x, y)) {
                currentRules.buttonClicked(topRight);
                
            } else if (bottomLeft.isInside(x, y)) {
                currentRules.buttonClicked(bottomLeft);
                
            } else if (bottomRight.isInside(x, y)) {
                currentRules.buttonClicked(bottomRight);
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
    
    public RulesSet getCurrentRules() {
        return currentRules;
    }

    public Color getTopLeftColor() {
        return topLeft.getColor();
    }

    public Color getTopRightColor() {
        return topRight.getColor();
    }

    public Color getBottomLeftColor() {
        return bottomLeft.getColor();
    }

    public Color getBottomRightColor() {
        return bottomRight.getColor();
    }
}
