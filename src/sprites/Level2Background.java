package sprites;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Background of level 2.
 */
public class Level2Background implements Sprite {

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.decode("#BBE3EE"));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // draw rays:
        d.setColor(Color.decode("#FFE59E"));
        int rayNum = 75;
        int raySpace = 750 / rayNum;
        for (int i = 1; i <= rayNum; ++i) {
            d.drawLine(140, 140, raySpace * i, 250);
        }

        // draw sun:
        int circleRadius = 60;
        List<Color> color = this.makeColor();
        for (int i = 0; i < 3; i++) {
            d.setColor(color.get(i));
            d.fillCircle(140, 140, circleRadius);
            circleRadius = circleRadius - 10;
        }
    }

    /**
     * Creates a list of colors.
     *
     * @return list of colors.
     */
    private List<Color> makeColor() {
            List<Color> colorList = new ArrayList<Color>();
            colorList.add(Color.decode("#FFE59E"));
            colorList.add(Color.decode("#FFDF89"));
            colorList.add(Color.decode("#FFE147"));
            return colorList;
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
    }
}