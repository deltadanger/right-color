package helper;

import com.badlogic.gdx.math.Vector2;

public class ClickableZone {
    public static final int BUTTON_CLICK_ZONE_PADDING = 4;
    
    private Vector2 position = null;
    private int radius = -1;
    private int width = -1;
    private int height = -1;
    
    public void update(int x, int y, int width, int height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }
    
    public void update(int x, int y, int radius) {
        this.position = new Vector2(x, y);
        this.radius = radius;
    }
    
    public boolean isInside(int x, int y) {
        if (position == null) {
            return false;
        }
        
        if (radius >= 0) {
            Vector2 centre = position.cpy();
            centre.add(radius*0.707f, radius*0.707f);
            return centre.dst(x, y) <= radius + BUTTON_CLICK_ZONE_PADDING;
            
        } else if (width >= 0 && height >= 0) {
            return (x >= position.x - BUTTON_CLICK_ZONE_PADDING && x <= position.x + width + BUTTON_CLICK_ZONE_PADDING &&
                    y >= position.y - BUTTON_CLICK_ZONE_PADDING && y <= position.y + height + BUTTON_CLICK_ZONE_PADDING);
        }
        
        return false;
    }
}
