package sprites;

import biuoop.DrawSurface;
import basic.Counter;
import animations.GameLevel;
import geometry.Rectangle;
import java.awt.Color;

/**
 * ScoreIndicator is a sprite that is in charge of displaying the current score.
 */
public class ScoreIndicator implements Sprite {
    private Rectangle rect;
    private Counter score;

    /**
     * Constructor: creates a ScoreIndicator from a given rectangle and a given counter.
     *
     * @param rect rectangle of the score indicator.
     * @param score counter of the score indicator.
     */
    public ScoreIndicator(Rectangle rect, Counter score) {
        this.rect = rect;
        this.score = score;
    }

    /**
     * Adds the score indicator to the given game.
     *
     * @param game the game to add the score indicator to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int upperLeftX = (int) this.rect.getUpperLeft().getX();
        int upperLeftY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(upperLeftX, upperLeftY, width, height);
        drawCounter(d);
    }

    /**
     * Draws a hit counter on the block, indicating how many hits are left for the block.
     *
     * @param d the surface to draw the counter on.
     */
    private void drawCounter(DrawSurface d) {
        int xLocation = (int) (this.rect.getUpperLeft().getX() + this.rect.getWidth() / 2);
        int yLocation = (int) (this.rect.getUpperLeft().getY() + this.rect.getHeight() / 1.5);
        d.setColor(Color.BLACK);
        d.drawText(xLocation, yLocation, "Score: " + this.score.getValue(), 12);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
    }
}