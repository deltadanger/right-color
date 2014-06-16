package com.rightcolor;

import helper.Callback;
import helper.ClickableZone;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.rightcolor.comunication.ISocialNetworkAPI;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesFactory;
import com.rightcolor.rules.RulesSet;

public class GameWorld {
    
    public static final String PREFERENCES_NAME = "com.rightcolor";
    public static final String PREFERENCE_KEY_SCORE = "keyScore";
    public static final String PREFERENCE_KEY_LEVEL = "keyLevel";
    
    public static enum GameState {
        MENU,
    	RUNNING,
    	GAME_OVER
    }
    
    public static enum GameMode {
        SPRINT("Sprint"), // Validate X(30) as fast as possible
        MARATHON("Marathon"), // Timer resets to X(10) every Y(15) validated 
        FASTER("Faster 'n faster"), // Decreasing timer to validate X(5)
        BLAH("BLAH"); // ???? Validate as many as possible in X(20) seconds
        
        private String text;
        private GameMode(String text) {
            this.text = text;
        }
        public String toString() {
            return text;
        }
    }
    
    private GameState currentState;

	private Preferences preferences;
	private int level;

    private ClickableZone level1 = new ClickableZone();
    private ClickableZone level2 = new ClickableZone();
    private ClickableZone level3 = new ClickableZone();
    private ClickableZone level4 = new ClickableZone();
	
    private ColorButton topLeft = new ColorButton();
    private ColorButton topRight = new ColorButton();
    private ColorButton bottomLeft = new ColorButton();
    private ColorButton bottomRight = new ColorButton();
    @SuppressWarnings("serial")
    private Map<ColorButton, GameMode> buttonToMode = new HashMap<ColorButton, GameWorld.GameMode>() {{
        this.put(topLeft, GameMode.SPRINT);
        this.put(topRight, GameMode.MARATHON);
        this.put(bottomLeft, GameMode.FASTER);
        this.put(bottomRight, GameMode.FASTER);
    }};
    
    private RulesSet currentRules;
    private RulesFactory rulesFactory = new RulesFactory();
    
    private ISocialNetworkAPI facebook;
    private ISocialNetworkAPI twitter;

    public GameWorld(ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
    	this.facebook = facebook;
    	this.twitter = twitter;
    	
        initialisePreferences();
        
//        resetGame(new RulesClassic());
        currentState = GameState.MENU;
        rulesFactory.assignInitialColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
//        currentState = GameState.RUNNING;
    }
    
    private void initialisePreferences() {
        boolean flush = false;
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        if (!preferences.contains(PREFERENCE_KEY_LEVEL)) {
        	preferences.putInteger(PREFERENCE_KEY_LEVEL, 1);
            flush = true;
        }

        if (flush) {
            preferences.flush();
        }

        level = preferences.getInteger(PREFERENCE_KEY_LEVEL);
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
    	
        currentRules.updateTimer(delta);
    }
    
    public void onBackClick() {
        switch (currentState) {
        
        case MENU:
            Gdx.app.exit();
            break;
            
        case GAME_OVER:
            currentState = GameState.MENU;
            break;
            
        case RUNNING:
            currentState = GameState.GAME_OVER;
            break;
        }
    }

	public void onClick(int x, int y) {
	    switch (currentState) {
	    
	    case MENU:
	        handleClickOnMenu(x, y);
            break;
            
	    case GAME_OVER:
	        handleClickOnGameOver(x, y);
            break;
            
	    case RUNNING:
	        handleClickOnRunning(x, y);
	        break;
	    }
    }
    
    private void handleClickOnMenu(int x, int y) {
        // Game mode selection
        ColorButton button = null;
        if (topLeft.isInside(x, y)) {
            button = topLeft;
        } else if (topRight.isInside(x, y)) {
            button = topRight;
        } else if (bottomLeft.isInside(x, y)) {
            button = bottomLeft;
        } else if (bottomRight.isInside(x, y)) {
            button = bottomRight;
        }
        
        if (button != null) {
            resetGame(rulesFactory.getRulesSet(getGameModeForButton(button), level));
            currentState = GameState.RUNNING;
        }
        
        // Level selection
        int level = 0;
        if (level1.isInside(x, y)) {
            level = 1;
        } else if (level2.isInside(x, y)) {
            level = 2;
        } else if (level3.isInside(x, y)) {
            level = 3;
        } else if (level4.isInside(x, y)) {
            level = 4;
        }
        
        if (level != 0) {
            preferences.putInteger(PREFERENCE_KEY_LEVEL, level);
            preferences.flush();
            this.level = level;
        }
    }
    
    private void handleClickOnRunning(int x, int y) {
        if (topLeft.isInside(x, y)) {
            currentRules.buttonClicked(topLeft);
            
        } else if (topRight.isInside(x, y)) {
            currentRules.buttonClicked(topRight);
            
        } else if (bottomLeft.isInside(x, y)) {
            currentRules.buttonClicked(bottomLeft);
            
        } else if (bottomRight.isInside(x, y)) {
            currentRules.buttonClicked(bottomRight);
        }
    }
    
    private void handleClickOnGameOver(int x, int y) {
        resetGame(rulesFactory.getRulesSet(currentRules.getGameMode(), level));
        this.currentState = GameState.RUNNING;
    }
    
    
    public void updateLevel1(int x, int y, int width, int height) {
        level1.update(x, y, width, height);
    }
    
    public void updateLevel2(int x, int y, int width, int height) {
        level2.update(x, y, width, height);
    }
    
    public void updateLevel3(int x, int y, int width, int height) {
        level3.update(x, y, width, height);
    }
    
    public void updateLevel4(int x, int y, int width, int height) {
        level4.update(x, y, width, height);
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

    public String getTopLeftText() {
        return buttonToMode.get(topLeft).text;
    }

    public String getTopRightText() {
        return buttonToMode.get(topRight).text;
    }

    public String getBottomLeftText() {
        return buttonToMode.get(bottomLeft).text;
    }

    public String getBottomRightText() {
        return buttonToMode.get(bottomRight).text;
    }

    public GameState getCurrentState() {
        return currentState;
    }
    
    public RulesSet getCurrentRules() {
        return currentRules;
    }
    
    public int getLevel() {
        return level;
    }
    
    public GameMode getGameModeForButton(ColorButton button) {
        return buttonToMode.get(button);
    }
}
