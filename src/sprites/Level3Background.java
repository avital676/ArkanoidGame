package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Background of level 3.
 */
public class Level3Background implements Sprite {

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.decode("#298A1C"));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // draw building:
        d.setColor(Color.decode("#414142"));
        d.fillRectangle(100, 200, 10, 200);
        d.setColor(Color.decode("#171017"));
        d.fillRectangle(90, 400, 30, 200);
        d.setColor(Color.decode("#170F12"));
        d.fillRectangle(55, 450, 100, 200);
        d.setColor(Color.decode("#FFD149"));
        d.fillCircle(105, 200, 11);
        d.setColor(Color.decode("#B86731"));
        d.fillCircle(105, 200, 7);
        d.setColor(Color.WHITE);
        d.fillCircle(105, 200, 3);

        // draw windows:
        int width = 10;
        int height = 25;
        int space = 8;
        int rowHeight = 460;
        drawWindows(rowHeight, d);
        drawWindows(rowHeight + height + space, d);
        drawWindows(rowHeight + height * 2 + space * 2, d);
        drawWindows(rowHeight + height * 3 + space * 3, d);
        drawWindows(rowHeight + height * 4 + space * 4, d);
        drawWindows(rowHeight + height * 5 + space * 5, d);
    }

    /**
     * Draws windows (little rectangles) on the given surface.
     *
     * @param y y coordinate of the rectangles (windows) on the screen.
     * @param d surface to draw on.
     */
    private void drawWindows(int y, DrawSurface d) {
        int width = 10;
        int height = 25;
        int space = 8;
        d.setColor(Color.WHITE);

        // draw rows of windows:
        for (int i = 0; i < 5; i++) {
            d.fillRectangle(65 + width * i + space * i, y, width, height);
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
    }
}