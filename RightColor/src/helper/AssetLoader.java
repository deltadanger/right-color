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

    public static Sound music;

    public static BitmapFont mainFont;

    public static void load() {

    	// Images
    	
        texture = new Texture(Gdx.files.internal("data/textures.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        pixel = new TextureRegion(texture, 0, 0, 1, 1);
        
        
        // Fonts

        mainFont = new BitmapFont(Gdx.files.internal("data/bd_cartoon.fnt"));
        mainFont.setScale(.08f, -.08f);
        
        
        // Sounds

        music = Gdx.audio.newSound(Gdx.files.internal("data/gravity_down.wav"));
        
        // Animations
        // TextureRegion[] anim = { anim1, anim2, anim3};
        // animation = new Animation(0.06f, anim);
        // animation.setPlayMode(Animation.LOOP_PINGPONG);
    }
    
    public static void dispose() {
        texture.dispose();
        mainFont.dispose();
        music.dispose();
        // animation.dispose();
    }

}