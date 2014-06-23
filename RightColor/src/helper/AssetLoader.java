package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion pixel;
    public static TextureRegion textColor;
    public static TextureRegion levelButton;
    public static TextureRegion levelButtonActive;
    
    public static TextureRegion twitter;
    public static TextureRegion facebook;

    public static Sound music;

    public static BitmapFont mainFont;

    public static void load() {

    	// Images
    	
        texture = new Texture(Gdx.files.internal("data/textures.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        pixel = new TextureRegion(texture, 0, 0, 1, 1);
        
        textColor = new TextureRegion(texture, 1, 1, 300, 100);
        textColor.flip(false, true);
        
        /*
        Level button colors:
        1fd030
        a4ffad
        Border: 0bac1a
        */
        levelButton = new TextureRegion(texture, 310, 2, 270, 140);
        levelButton.flip(false, true);

        levelButtonActive = new TextureRegion(texture, 630, 2, 270, 140);
        levelButtonActive.flip(false, true);

        twitter = new TextureRegion(texture, 19, 151, 468, 471);
        twitter.flip(false, true);

        facebook = new TextureRegion(texture, 520, 151, 477, 480);
        facebook.flip(false, true);
        
        
        // Fonts

        mainFont = new BitmapFont(Gdx.files.internal("data/machinescript.fnt"));
        
        
        // Sounds

//        music = Gdx.audio.newSound(Gdx.files.internal("data/gravity_down.wav"));
        
        // Animations
        // TextureRegion[] anim = { anim1, anim2, anim3};
        // animation = new Animation(0.06f, anim);
        // animation.setPlayMode(Animation.LOOP_PINGPONG);
    }
    
    public static void dispose() {
        texture.dispose();
        mainFont.dispose();
//        music.dispose();
        // animation.dispose();
    }

}