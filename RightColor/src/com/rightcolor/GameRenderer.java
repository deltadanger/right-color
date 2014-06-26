package com.rightcolor;

import helper.AssetLoader;
import helper.ClickableZone;
import helper.PreferenceKeysFactory;
import helper.Utils;
import helper.PreferenceKeysFactory.Preference;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightcolor.gameobjects.ColorButton;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.tutorial.TutorialContent;
import com.rightcolor.tutorial.TutorialContentFactory;

public class GameRenderer {
    
    // Menu screen positions
    private static final float TITLE_POSITION = 5f;
    private static final float TITLE_POSITION_2 = 20f;
    private static final float TITLE_2_WIDTH = 100;
    private static final float LEVEL_POSITION_Y_FACTOR = 0.7f;
    private static final float LEVEL_TEXT_MARGIN = 5;
    private static final float LEVEL_BUTTON_HEIGHT_FACTOR = 0.2f;
    private static final float LEVEL_BUTTON_TEXT_PADDING = 3;
    private static final float LEVEL_BUTTON_SPACING = 2;

    // Running screen positions
    private static final float TIMER_POSITION_Y = 0;
    private static final float TIME_BAR_HEIGHT = 10;
    private static final float SCORE_POSITION_Y = 20;
    private static final float COLOR_POSITION_Y = 50;
    
    // Game Over screen positions
    private static final float SCORE_POSITION_Y_FACTOR = 0.25f;
    private static final float BEST_SCORE_POSITION_Y_FACTOR = 0.35f;
    private static final float SOCIAL_BUTTON_POSITION_Y_FACTOR = 0.6f;
    private static final int SOCIAL_BUTTON_SPACING = 15;
    private static final int SOCIAL_BUTTON_SIZE = 40;
    private static final float NAVIGATION_BUTTON_HEIGHT = 50;
    
    // Text sizes
    private static final float TEXT_SCALE_NORMAL = 0.1f;
    private static final float TEXT_SCALE_BEST_SCORES = 0.08f;
    private static final float TEXT_SCALE_COLOR_NAME = 0.15f;
    private static final float TEXT_SCALE_TIME = 0.08f;
    private static final float TEXT_SCALE_SCORE = 0.08f;
    private static final float TEXT_SCALE_FINAL_SCORE = 0.15f;
    
    // Colors
    private static final Color COLOR_TIME_BAR = Color.WHITE;
    private static final Color COLOR_TIME_BAR_BACKGROUND = Color.RED;
    private static final Color COLOR_TEXT_TIME = Color.LIGHT_GRAY;
    private static final Color COLOR_TEXT_DEFAULT = Color.WHITE;
    private static final Color NAVIGATION_BUTTON_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = Color.valueOf("D2D2D2FF");
    private static final Color COLOR_SCREEN_DIM = Color.valueOf("0000007F");

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

        AssetLoader.mainFont.setScale(TEXT_SCALE_NORMAL, -TEXT_SCALE_NORMAL);
        switch (world.getCurrentState()) {
        case MENU:
            renderMenu();
            break;
        case TUTORIAL:
            renderTutorial();
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
        int availableSpace = draw4buttons(true);
        
        drawTitle();

        int buttonHeight = (int) (availableSpace * LEVEL_BUTTON_HEIGHT_FACTOR);
        
        b = drawText("Level:", 0, false, availableSpace * LEVEL_POSITION_Y_FACTOR + buttonHeight/2, true);
        
        int buttonWidth = (int) (Utils.GAME_WIDTH - b.width - LEVEL_TEXT_MARGIN*3) / 4;
        
        TextureRegion[] buttonTextures = {AssetLoader.levelButton, AssetLoader.levelButton, AssetLoader.levelButton, AssetLoader.levelButton};
        buttonTextures[world.getLevel()-1] = AssetLoader.levelButtonActive;
        int buttonLeft = (int) (b.width + LEVEL_TEXT_MARGIN);
        
        batcher.setColor(Color.WHITE);
        for (int i = 0; i < 4; i++) {
            drawButton(world.getLevelButton(i+1), buttonTextures[i],
                    buttonLeft, (int) (availableSpace * LEVEL_POSITION_Y_FACTOR), buttonWidth, buttonHeight);
//            batcher.draw(buttonTextures[i], buttonLeft, availableSpace * LEVEL_POSITION_Y_FACTOR, buttonWidth, buttonHeight);
//            world.getLevelButton(i+1).update(buttonLeft, (int) (availableSpace * LEVEL_POSITION_Y_FACTOR), buttonWidth, buttonHeight);
            drawText(""+(i+1), buttonLeft + buttonWidth/2, true,
                                  availableSpace * LEVEL_POSITION_Y_FACTOR + LEVEL_BUTTON_TEXT_PADDING, false);
            buttonLeft += buttonWidth + LEVEL_BUTTON_SPACING;
        }
    }

    private void renderRunning() {
        draw4buttons();
        
        batcher.setColor(COLOR_TIME_BAR_BACKGROUND);
        batcher.draw(AssetLoader.pixel, 0, 0, Utils.GAME_WIDTH, TIME_BAR_HEIGHT);
        
        float width = Utils.GAME_WIDTH * world.getCurrentRules().getRemainingTime() / world.getCurrentRules().getTotalTime();
        batcher.setColor(COLOR_TIME_BAR);
        batcher.draw(AssetLoader.pixel, 0, 0, width, TIME_BAR_HEIGHT);

        AssetLoader.mainFont.setScale(TEXT_SCALE_TIME, -TEXT_SCALE_TIME);
        drawText(String.format("%.1f", world.getCurrentRules().getRemainingTime()), COLOR_TEXT_TIME,
                Utils.GAME_WIDTH/2, true, TIMER_POSITION_Y, false);

        AssetLoader.mainFont.setScale(TEXT_SCALE_SCORE, -TEXT_SCALE_SCORE);
        drawText("Score: "+world.getCurrentRules().getScore(), Utils.GAME_WIDTH/2, true, SCORE_POSITION_Y, true);
        
        AssetLoader.mainFont.setScale(TEXT_SCALE_COLOR_NAME, -TEXT_SCALE_COLOR_NAME);
        drawText(RulesSet.AVAILABLE_COLORS.get(world.getCurrentRules().getTargetColor()), world.getCurrentRules().getTextColor(),
                Utils.GAME_WIDTH/2, true, COLOR_POSITION_Y, false);
        AssetLoader.mainFont.setScale(TEXT_SCALE_NORMAL, -TEXT_SCALE_NORMAL);
    }
    
    private void renderTutorial() {
        switch (world.getCurrentTutorial()) {
        case LEVEL_2:
        case LEVEL_3:
        case LEVEL_4:
            renderMenu();
            break;
        default:
            renderRunning();
            break;
        }

        
        batcher.setColor(COLOR_SCREEN_DIM);
        batcher.draw(AssetLoader.pixel, 0, 0, Utils.GAME_WIDTH, gameHeight);
        
        
        TutorialContent content = new TutorialContentFactory().getTutorialContent(world.getCurrentTutorial());
        content.drawTutorialContent(batcher);
    }
    
    private void renderGameOver() {
        drawTitle();
        
        AssetLoader.mainFont.setScale(TEXT_SCALE_FINAL_SCORE, -TEXT_SCALE_FINAL_SCORE);

        drawText("Score: "+world.getCurrentRules().getScore(), Utils.GAME_WIDTH/2, true, gameHeight*SCORE_POSITION_Y_FACTOR, true);
//        String text = "Best Score\n(" + world.getCurrentRules().getGameMode().getText() +
//                ", " + world.getCurrentRules().getLevelName() +
//                "):\n" + world.getPreferences().getInteger(PreferenceKeysFactory.getPreferencesKey(Preference.SCORE, world.getCurrentRules()));
//        
//        drawText(text, Utils.GAME_WIDTH/2, true, gameHeight*BEST_SCORE_POSITION_Y_FACTOR, false, Utils.GAME_WIDTH);
//        
        String text = "Best Score: " + world.getPreferences().getInteger(PreferenceKeysFactory.getPreferencesKey(Preference.SCORE, world.getCurrentRules()));
        drawText(text, Utils.GAME_WIDTH/2, true, gameHeight*BEST_SCORE_POSITION_Y_FACTOR+20, true);
        
        AssetLoader.mainFont.setScale(TEXT_SCALE_NORMAL, -TEXT_SCALE_NORMAL);

        batcher.setColor(Color.WHITE);
        drawButton(world.getButtonTwitter(), AssetLoader.twitter,
                Utils.GAME_WIDTH/2 - SOCIAL_BUTTON_SPACING/2 - SOCIAL_BUTTON_SIZE, gameHeight*SOCIAL_BUTTON_POSITION_Y_FACTOR,
                SOCIAL_BUTTON_SIZE, SOCIAL_BUTTON_SIZE);
        
        drawButton(world.getButtonFacebook(), AssetLoader.facebook,
                Utils.GAME_WIDTH/2 + SOCIAL_BUTTON_SPACING/2, gameHeight*SOCIAL_BUTTON_POSITION_Y_FACTOR,
                SOCIAL_BUTTON_SIZE, SOCIAL_BUTTON_SIZE);
        
        drawButton(world.getButtonMenu(), AssetLoader.levelButton,
                0, gameHeight - NAVIGATION_BUTTON_HEIGHT, Utils.GAME_WIDTH/2, NAVIGATION_BUTTON_HEIGHT);
        drawText("Back to menu", NAVIGATION_BUTTON_COLOR,
                Utils.GAME_WIDTH/4, true, gameHeight-NAVIGATION_BUTTON_HEIGHT/2, true);
        
        drawButton(world.getButtonAgain(), AssetLoader.levelButton,
                Utils.GAME_WIDTH/2, gameHeight - NAVIGATION_BUTTON_HEIGHT, Utils.GAME_WIDTH/2, NAVIGATION_BUTTON_HEIGHT);
        drawText("Try again !", NAVIGATION_BUTTON_COLOR,
                Utils.GAME_WIDTH/2 + Utils.GAME_WIDTH/4, true, gameHeight-NAVIGATION_BUTTON_HEIGHT/2, true);
    }
    
    private void drawTitle() {
        AssetLoader.mainFont.setColor(Color.WHITE);
        
        drawText("Pick the right", Utils.GAME_WIDTH/2, true, TITLE_POSITION, false);

        batcher.setColor(Color.WHITE);
        float width = TITLE_2_WIDTH;
        float height = width * AssetLoader.textColor.getRegionHeight() / AssetLoader.textColor.getRegionWidth();
        batcher.draw(AssetLoader.textColor, Utils.GAME_WIDTH/2 - width/2, TITLE_POSITION_2, width, height);
    }
    
    private int draw4buttons() {
        return draw4buttons(false);
    }
    
    private int draw4buttons(boolean withText) {
        int buttonTop = (int) (gameHeight / 3f);
        int buttonHeight = (int) (gameHeight * 2f / 3f / 2f);

        ColorButton[] buttons = {world.getTopLeft(), world.getTopRight(), world.getBottomLeft(), world.getBottomRight()};
        
        for (int i = 0; i < 4; i++) {
            ColorButton button = buttons[i];

            batcher.setColor(button.getColor());
            
            int x = 0;
            if (i%2 != 0) {
                x = Utils.GAME_WIDTH/2;
            }
            
            int y = buttonTop;
            if (i > 1) {
                y += buttonHeight;
            }
            
            drawButton(button, AssetLoader.pixel, x, y, Utils.GAME_WIDTH/2, buttonHeight);
//            batcher.draw(AssetLoader.pixel, x, y, Utils.GAME_WIDTH/2, buttonHeight);
//            button.update(x, y, Utils.GAME_WIDTH/2, buttonHeight);
            if (withText) {
                drawText(button.getMode().getText(), BUTTON_TEXT_COLOR, x + Utils.GAME_WIDTH/4, true, y + buttonHeight/3, true);
                
                AssetLoader.mainFont.setScale(TEXT_SCALE_BEST_SCORES, -TEXT_SCALE_BEST_SCORES);
                String keyLevel1 = PreferenceKeysFactory.getPreferencesKey(Preference.SCORE, button.getMode(), 1);
                String keyLevel2 = PreferenceKeysFactory.getPreferencesKey(Preference.SCORE, button.getMode(), 2);
                String keyLevel3 = PreferenceKeysFactory.getPreferencesKey(Preference.SCORE, button.getMode(), 3);
                String keyLevel4 = PreferenceKeysFactory.getPreferencesKey(Preference.SCORE, button.getMode(), 4);
                String text = "Bests: " +
                        world.getPreferences().getInteger(keyLevel1) +
                        " " +
                        world.getPreferences().getInteger(keyLevel2) +
                        " " +
                        world.getPreferences().getInteger(keyLevel3) +
                        " " +
                        world.getPreferences().getInteger(keyLevel4);
                drawText(text, BUTTON_TEXT_COLOR, x + Utils.GAME_WIDTH/4, true, y + buttonHeight*2/3, true);
                AssetLoader.mainFont.setScale(TEXT_SCALE_NORMAL, -TEXT_SCALE_NORMAL);
            }
        }
        
        return gameHeight - buttonHeight*2;
    }
    
    private TextBounds drawText(String text, float x, boolean centerX, float y, boolean centerY) {
        return drawText(text, x, centerX, y, centerY, 0);
    }
    
    private TextBounds drawText(String text, float x, boolean centerX, float y, boolean centerY, float wrap) {
        return drawText(text, COLOR_TEXT_DEFAULT, x, centerX, y, centerY, wrap);
    }
    
    private TextBounds drawText(String text, Color c, float x, boolean centerX, float y, boolean centerY) {
        return drawText(text, c, x, centerX, y, centerY, 0);
    }
    
    private TextBounds drawText(String text, Color c, float x, boolean centerX, float y, boolean centerY, float wrap) {
        AssetLoader.mainFont.setColor(c);
        TextBounds b;
        if (wrap > 0) {
            b = AssetLoader.mainFont.getWrappedBounds(text, wrap);
        } else {
            b = AssetLoader.mainFont.getBounds(text);
        }
        
        if (centerX) {
            x -= b.width/2;
        }
        if (centerY) {
            y += b.height/2; // b.height < 0
        }
        
        if (wrap > 0) {
            AssetLoader.mainFont.drawWrapped(batcher, text, x, y, wrap, HAlignment.CENTER);
//            AssetLoader.mainFont.drawWrapped(batcher, text, x, y, wrap);
        } else {
            AssetLoader.mainFont.draw(batcher, text, x, y);
        }
        AssetLoader.mainFont.setColor(COLOR_TEXT_DEFAULT);
        return b;
    }
    
    private void drawButton(ClickableZone button, TextureRegion img, float x, float y, float width, float height) {
        batcher.draw(img, x, y, width, height);
        button.update(x, y, width, height);
    }
}
