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
    public static TextureRegion button;
    public static TextureRegion buttonActive;
    
    public static TextureRegion twitter;
    public static TextureRegion facebook;

    public static Sound music;

    public static BitmapFont mainFont;

    public static void load() {

    	// Images
    	
        texture = new Texture(Gdx.files.internal("data/textures.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        pixel = new TextureRegion(texture, 0, 0, 1, 1);
        
        textColor = new TextureRegion(texture, 74, 754, 753, 257);
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
        
        button = new TextureRegion(texture, 31, 501, 460, 235);
        button.flip(false, true);

        buttonActive = new TextureRegion(texture, 533, 501, 460, 235);
        buttonActive.flip(false, true);

        twitter = new TextureRegion(texture, 19, 11, 487, 480);
        twitter.flip(false, true);

        facebook = new TextureRegion(texture, 520, 11, 477, 480);
        facebook.flip(false, true);
        
        
        // Fonts

        mainFont = new BitmapFont(Gdx.files.internal("data/machinescript.fnt"));
        mainFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
    
    public static void dispose() {
        texture.dispose();
        mainFont.dispose();
    }

}