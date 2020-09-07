package blocks;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * Boundary block is a block that is a boundary of the screen in the game.
 */
public class BoundaryBlock extends Block {
    private Color color;
    private Rectangle rect;

    /**
     * Constructor: creates a new boundary block by calling its super constructor.
     *
     * @param rect rectangle of the block.
     * @param color color of the block.
     */
    public BoundaryBlock(Rectangle rect, java.awt.Color color) {
        super(rect, color, 0);
        this.color = color;
        this.rect = rect;
    }

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Point location = this.rect.getUpperLeft();
        d.drawRectangle((int) location.getX(), (int) location.getY(), (int) super.getWidth(), (int) super.getHeight());
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
