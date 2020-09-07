package sprites;

import biuoop.DrawSurface;
import animations.GameLevel;
import geometry.Rectangle;

import java.awt.Color;

/**
 * LevelIndicator is a sprite that is in charge of displaying the current level name.
 */
public class LevelIndicator implements Sprite {
    private final Rectangle rect;
    private String name;

    /**
     * Constructor: creates a new level indicator with a given rectangle and a given name.
     *
     * @param rect rectangle of the level indicator.
     * @param name name of the level.
     */
    public LevelIndicator(Rectangle rect, String name) {
        this.rect = rect;
        this.name = name;
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
        drawName(d);
    }

    /**
     * Draws the sprite on a given surface.
     *
     * @param d surface to draw the sprite on.
     */
    private void drawName(DrawSurface d) {
        int xLocation = (int) (this.rect.getUpperLeft().getX() + this.rect.getWidth() / 4);
        int yLocation = (int) (this.rect.getUpperLeft().getY() + this.rect.getHeight() / 1.5);
        d.setColor(Color.BLACK);
        d.drawText(xLocation, yLocation, "Level Name: " + this.name, 12);
    }

    /**
     * Adds the lives indicator to the given game.
     *
     * @param game the game to add the lives indicator to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
    }
}