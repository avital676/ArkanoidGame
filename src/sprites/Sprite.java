package sprites;

import biuoop.DrawSurface;

/**
 * The Sprite interface is implemented in classes of objects that could be drawn to the screen.
 * It has methods to draw the sprite on a given surface and to notify the sprite that time has passed.
 */
public interface Sprite {

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}