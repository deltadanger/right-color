package com.rightcolor;

import helper.Callback;
import helper.ClickableZone;
import helper.PreferenceKeysFactory;
import helper.PreferenceKeysFactory.Preference;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.rightcolor.comunication.ISocialNetworkAPI;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.impl.RulesFactory;

public class GameWorld {
    
    public static final String PREFERENCES_NAME = "com.rightcolor";
    public static final float MIN_TIME_TUTORIAL_DISPLAY = 1f;
    
    public static enum GameState {
        MENU,
        TUTORIAL,
        PRERUNNING,
        RUNNING,
    	GAME_OVER
    }
    
    public static enum TutorialState {
//        LEVEL_1("Level1"),
        LEVEL_2("Level2"),
        LEVEL_3("Level3"),
        LEVEL_4("Level4"),
        MODE_EASYPEASY("ModeEasyPeasy"),
        MODE_MARATHON("ModeMarathon"),
        MODE_SPRINT("ModeSprint"),
        MODE_FASTER("ModeFaster");

        private String key;
        private TutorialState(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
        public static TutorialState forMode(GameMode mode) {
            switch (mode) {
            case EASYPEASY:
                return MODE_EASYPEASY;
            case MARATHON:
                return MODE_MARATHON;
            case FASTER:
                return MODE_FASTER;
            case SPRINT:
                return MODE_SPRINT;
            }
            return null;
        }
    }
    
    public static enum GameMode {
        EASYPEASY("Easy Peasy", "EasyPeasy"), // Validate as many as possible, 5 seconds per validation
        MARATHON("Marathon", "Marathon"), // Timer resets to 10s every 10 validated
        SPRINT("Sprint", "Sprint"), // Validate as many as possible in 20s 
        FASTER("Faster 'n faster", "Faster"); // Decreasing timer to validate 1
        
        private String text;
        private String key;
        private GameMode(String text, String key) {
            this.text = text;
            this.key = key;
        }
        public String getText() {
            return text;
        }
        public String getKey() {
            return key;
        }
    }
    
    private GameState currentState;

	private Preferences preferences;
	private int level;

    private ClickableZone level1 = new ClickableZone();
    private ClickableZone level2 = new ClickableZone();
    private ClickableZone level3 = new ClickableZone();
    private ClickableZone level4 = new ClickableZone();
    
    private ClickableZone buttonMenu= new ClickableZone();
    private ClickableZone buttonAgain = new ClickableZone();
    private ClickableZone buttonTwitter= new ClickableZone();
    private ClickableZone buttonFacebook = new ClickableZone();
	
    private ColorButton topLeft = new ColorButton(GameMode.EASYPEASY);
    private ColorButton topRight = new ColorButton(GameMode.MARATHON);
    private ColorButton bottomLeft = new ColorButton(GameMode.SPRINT);
    private ColorButton bottomRight = new ColorButton(GameMode.FASTER);
    
    private float elapsedTime = 0;
    
    private RulesSet currentRules;
    private RulesFactory rulesFactory = new RulesFactory();
    private TutorialState currentTutorial = null;
    
    private ISocialNetworkAPI facebook;
    private ISocialNetworkAPI twitter;

    public GameWorld(ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
    	this.facebook = facebook;
    	this.twitter = twitter;
    	
        initialisePreferences();
        dev();
        
        currentState = GameState.MENU;
        rulesFactory.assignInitialColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
    }
    
    private void dev() {
        // TODO: remove this
        for (TutorialState state : TutorialState.values()) {
            String key = PreferenceKeysFactory.getPreferencesKey(state);
            preferences.remove(key);
        }
    }
    
    private void initialisePreferences() {
        boolean flush = false;
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        String keyLevel = PreferenceKeysFactory.getPreferencesKey(Preference.LEVEL);
        if (!preferences.contains(keyLevel)) {
        	preferences.putInteger(keyLevel, 1);
            flush = true;
        }

        if (flush) {
            preferences.flush();
        }
        
        level = preferences.getInteger(keyLevel);
    }
    
    public void resetGame(RulesSet ruleSet) {
        currentRules = ruleSet;
        currentRules.replaceEventListeners(RulesSet.EVENT_ROUND_FINISHED, initialiseNewRound);
        currentRules.replaceEventListeners(RulesSet.EVENT_GAME_END_VICTORY, onVictory);
        currentRules.replaceEventListeners(RulesSet.EVENT_GAME_END_DEFEAT, onDefeat);
        currentRules.assignInitialColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
        initialiseNewRound.call();
    }
    
    private Callback initialiseNewRound = new Callback() {
        
        @Override
        public Object call() {
            Color[] availableColors = currentRules.assignColorToButtons(topLeft, topRight, bottomLeft, bottomRight);
            currentRules.generateNewTargetColor(availableColors);
            currentRules.setNewTextColor();
            
            return null;
        }
    };
    
    private Callback onVictory = new Callback() {
        
        @Override
        public Object call() {
            manageScore();
            GameWorld.this.currentState = GameState.GAME_OVER;
            return null;
        }
    };
    
    private Callback onDefeat = new Callback() {
        
        @Override
        public Object call() {
            manageScore();
            GameWorld.this.currentState = GameState.GAME_OVER;
            return null;
        }
    };
    
    private void manageScore() {
        int score = currentRules.getScore();
        String key = PreferenceKeysFactory.getPreferencesKey(Preference.SCORE, currentRules);
        int best = preferences.getInteger(key, 0);
        
        if (score > best) {
            preferences.putInteger(key, score);
            preferences.flush();
        }
    }

    public void update(float delta) {
        elapsedTime += delta;
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

        case TUTORIAL:
            if (elapsedTime > MIN_TIME_TUTORIAL_DISPLAY) {
                switch (currentTutorial) {
                case LEVEL_2:
                case LEVEL_3:
                case LEVEL_4:
                    currentState = GameState.MENU;
                    break;
                default:
                    currentState = GameState.PRERUNNING;
                    break;
                }
            }
            break;
        case PRERUNNING:
        case RUNNING:
            onDefeat.call();
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
            
        case TUTORIAL:
            if (elapsedTime > MIN_TIME_TUTORIAL_DISPLAY) {
                switch (currentTutorial) {
                case LEVEL_2:
                case LEVEL_3:
                case LEVEL_4:
                    currentState = GameState.MENU;
                    break;
                default:
                    currentState = GameState.PRERUNNING;
                    break;
                }
            }
            break;
        case PRERUNNING:
            currentState = GameState.RUNNING;
	    case RUNNING:
	        handleClickOnRunning(x, y);
	        break;
	    }
    }
    
    private void handleClickOnMenu(int x, int y) {
        TutorialState tuto = null;
        
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
            tuto = TutorialState.forMode(button.getMode());
            resetGame(rulesFactory.getNewRulesSet(button.getMode(), level));
            currentState = GameState.PRERUNNING;
        }
        
        // Level selection
        int level = 0;
        if (level1.isInside(x, y)) {
            level = 1;
        } else if (level2.isInside(x, y)) {
            tuto = TutorialState.LEVEL_2;
            level = 2;
        } else if (level3.isInside(x, y)) {
            tuto = TutorialState.LEVEL_3;
            level = 3;
        } else if (level4.isInside(x, y)) {
            tuto = TutorialState.LEVEL_4;
            level = 4;
        }
        
        if (level != 0) {
            preferences.putInteger(PreferenceKeysFactory.getPreferencesKey(Preference.LEVEL), level);
            preferences.flush();
            this.level = level;
        }
        
        if (tuto != null && !wasTutoViewed(tuto)) {
            setTutorialMode(tuto);
        }
    }
    
    private void setTutorialMode(TutorialState state) {
        String key = PreferenceKeysFactory.getPreferencesKey(state);
        preferences.putBoolean(key, true);
        preferences.flush();
        
        currentState = GameState.TUTORIAL;
        currentTutorial = state;
        elapsedTime = 0;
    }

    private boolean wasTutoViewed(TutorialState state) {
        String key = PreferenceKeysFactory.getPreferencesKey(state);
        return preferences.contains(key);
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
        if (buttonMenu.isInside(x, y)) {
            this.currentState = GameState.MENU;
            
        } else if (buttonAgain.isInside(x, y)) {
            resetGame(rulesFactory.getNewRulesSet(currentRules.getGameMode(), level));
            this.currentState = GameState.RUNNING;
            
        } else if (buttonTwitter.isInside(x, y)) {
            // update status
            
        } else if (buttonFacebook.isInside(x, y)) {
            // update status
        }
    }
    
    
    public ClickableZone getLevelButton(int level) {
        ClickableZone[] buttons = new ClickableZone[]{level1, level2, level3, level4};
        return buttons[level-1];
    }
    
    public ColorButton getTopLeft() {
        return topLeft;
    }

    public ColorButton getTopRight() {
        return topRight;
    }

    public ColorButton getBottomLeft() {
        return bottomLeft;
    }

    public ColorButton getBottomRight() {
        return bottomRight;
    }
    
    public ClickableZone getButtonMenu() {
        return buttonMenu;
    }
    
    public ClickableZone getButtonAgain() {
        return buttonAgain;
    }
    
    public ClickableZone getButtonTwitter() {
        return buttonTwitter;
    }
    
    public ClickableZone getButtonFacebook() {
        return buttonFacebook;
    }
    
    public GameState getCurrentState() {
        return currentState;
    }
    
    public RulesSet getCurrentRules() {
        return currentRules;
    }
    
    public TutorialState getCurrentTutorial() {
        return currentTutorial;
    }
    
    public int getLevel() {
        return level;
    }
    
    public Preferences getPreferences() {
        return preferences;
    }
}
