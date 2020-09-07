package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Background of level 1.
 */
public class Level1Background implements Sprite {

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);

        // draw target:
        d.setColor(Color.BLUE);
        int circleRadius = 50;
        for (int i = 0; i < 3; i++) {
            d.drawCircle(400, 165, circleRadius);
            circleRadius = circleRadius + 25;
        }
        d.drawLine(400, 0, 400, 310);
        d.drawLine(245, 165, 555, 165);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
    }
}