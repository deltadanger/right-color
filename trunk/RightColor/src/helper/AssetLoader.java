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
        1cbc2b
        d8ffdc
        Border: 048f12
        
        ccf8f9
        1dbabc
        Border: 0c8283
        */
        
        levelButton = new TextureRegion(texture, 31, 721, 460, 235);
        levelButton.flip(false, true);

        levelButtonActive = new TextureRegion(texture, 533, 721, 460, 235);
        levelButtonActive.flip(false, true);

        twitter = new TextureRegion(texture, 19, 151, 487, 480);
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