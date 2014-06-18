package helper;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.rightcolor.rules.RulesSet;

public class Utils {
    public static final int GAME_WIDTH = 200;
    
    public static final Random RANDOM = new Random();
    
    public static Color getRandomColor() {
        return RulesSet.AVAILABLE_COLORS[RANDOM.nextInt(RulesSet.AVAILABLE_COLORS.length)];
    }
    
    public static Color getRandomColor(Color[] colors) {
        return colors[RANDOM.nextInt(colors.length)];
    }
}
