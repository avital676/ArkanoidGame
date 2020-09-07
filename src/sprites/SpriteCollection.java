package sprites;

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * Sprite collection class holds a list of all the sprites in the game.
 */
public class SpriteCollection {
    private List<Sprite> spriteList;

    /**
     * Constructor: creates a sprite collection that has an array list of sprites.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<Sprite>();
    }

    /**
     * Adds a given sprite to the sprite list.
     *
     * @param s the sprite to be added to the list.
     */
    public void addSprite(Sprite s) {
        spriteList.add(s);
    }

    /**
     * Removes a given sprite from the sprite list.
     *
     * @param s sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        spriteList.remove(s);
    }

    /**
     * Calls the timePassed() method on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copySpriteList = new ArrayList<Sprite>(this.spriteList);
        for (Sprite s : copySpriteList) {
            if (s != null) {
                s.timePassed();
            }
        }
    }

    /**
     * Calls drawOn() method on all sprites.
     *
     * @param d the surface to draw all the sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> copySpriteList = new ArrayList<Sprite>(this.spriteList);
        for (Sprite s : copySpriteList) {
            if (s != null) {
                s.drawOn(d);
            }
        }
    }
}