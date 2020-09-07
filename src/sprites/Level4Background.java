package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Background of level 4.
 */
public class Level4Background implements Sprite {

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.decode("#3399FF"));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // draw rain:
        d.setColor(Color.WHITE);
        for (int i = 0; i < 12; i++) {
            d.drawLine(130 + (10 * i), 370, 100 + (10 * i), 600);
            d.drawLine(585 + (10 * i), 450, 570 + (10 * i), 600);
        }

        // draw clouds:
        d.setColor(Color.decode("#E0E0E0"));
        d.fillCircle(165, 370, 32);
        d.fillCircle(140, 350, 24);
        d.fillCircle(590, 440, 20);
        d.setColor(Color.decode("#C0C0C0"));
        d.fillCircle(178, 330, 30);
        d.fillCircle(628, 420, 30);
        d.fillCircle(610, 450, 20);
        d.setColor(Color.decode("#A0A0A0"));
        d.fillCircle(225, 350, 38);
        d.fillCircle(205, 375, 23);
        d.fillCircle(675, 450, 33);
        d.fillCircle(645, 460, 26);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
    }
}