package blocks;

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basic.Collidable;
import animations.GameLevel;
import basic.HitNotifier;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
import sprites.Ball;
import sprites.Sprite;
import sprites.Velocity;

/**
 * The class Block is a block object. The block has a rectangle, color and number of hits.
 * The Block class implements Collidable and Sprite interfaces.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private java.awt.Color color;
    private java.awt.Color stroke;
    private int hitNumLeft;
    private int initialHitNum;
    private List<HitListener> hitListeners;
    private Map<Integer, String> fillsMap = new HashMap<>();
    private int width;
    private int height;
    private Point location;
    private Filler filler;

    /**
     * Constructor: creates a new block.
     *
     * @param location location of the block.
     * @param height height of the block.
     * @param width width of the block.
     * @param hitPoints number of hits allowed for the block.
     * @param fillsMap map of how to fill each block depending on the hit number.
     * @param stroke stroke of the block.
     */
    public Block(Point location, int height, int width, int hitPoints,
                 Map<Integer, String> fillsMap, java.awt.Color stroke) {
        this.location = location;
        this.width = width;
        this.height = height;
        this.rect = new Rectangle(location, width, height);
        this.hitNumLeft = hitPoints;
        this.initialHitNum = hitPoints;
        this.stroke = stroke;
        this.fillsMap = fillsMap;
        this.hitListeners = new ArrayList<HitListener>();
        this.filler = new Filler(fillsMap);
    }

    /**
     * Constructor: copies the properties of other to this block.
     *
     * @param other Block.
     */
    public Block(Block other) {
        this.location = other.location;
        this.width = other.width;
        this.height = other.height;
        this.rect = new Rectangle(location, width, height);
        this.hitNumLeft = other.hitNumLeft;
        this.initialHitNum = other.initialHitNum;
        this.stroke = other.stroke;
        this.fillsMap = other.fillsMap;
        this.hitListeners = new ArrayList<HitListener>();
        this.filler = new Filler(other.fillsMap);
    }

    /**
     * Constructor: creates a new block with a rectangle and a color.
     *
     * @param rect the rectangle forming the block.
     * @param color the color of the block.
     * @param hitNum the number of hits the block can get.
     */
    public Block(Rectangle rect, java.awt.Color color, int hitNum) {
        this.rect = rect;
        this.location = rect.getUpperLeft();
        this.color = color;
        this.hitNumLeft = hitNum;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Gets the "collision shape" of the block.
     *
     * @return the rectangle of the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Notify the block that it was hit, check on which side of the block the hit occurred and
     * determine a new velocity based on the velocity the object had before the hit.
     *
     * @param hitter the ball doing the hitting.
     * @param collisionPoint the collision point with the block.
     * @param currentVelocity the velocity of object hitting the block.
     * @return new velocity expected after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double newDX = currentVelocity.getDX();
        double newDY = currentVelocity.getDY();

        // check which side of rectangle the ball hit:
        boolean pointInLeftSide = rect.getLeftSide().isPointInLine(collisionPoint, rect.getLeftSide());
        boolean pointInRightSide = rect.getRightSide().isPointInLine(collisionPoint, rect.getRightSide());
        boolean pointInTopSide = rect.getTopSide().isPointInLine(collisionPoint, rect.getTopSide());
        boolean pointInBottomSide = rect.getBottomSide().isPointInLine(collisionPoint, rect.getBottomSide());

        // determine new velocity according to the collision location:
        if (pointInLeftSide || pointInRightSide) {
            newDX = -newDX;
        }
        if (pointInTopSide || pointInBottomSide) {
            newDY = -newDY;
        }
        if (this.hitNumLeft > 0) {
            this.hitNumLeft = this.hitNumLeft - 1;
        }
        this.notifyHit(hitter);

        return new Velocity(newDX, newDY);
    }

    /**
     * Draws a hit counter on the block, indicating how many hits are left for the block.
     *
     * @param d the surface to draw the counter on.
     */
    public void drawHitCounter(DrawSurface d) {
        int xLocation = (int) (this.rect.getUpperLeft().getX() + this.rect.getWidth() / 2);
        int yLocation = (int) (this.rect.getUpperLeft().getY() + this.rect.getHeight() / 1.5);
        d.setColor(Color.WHITE);
        if (this.hitNumLeft > 0) {
            d.drawText(xLocation, yLocation, Integer.toString(this.hitNumLeft), 12);
        } else {
            d.drawText(xLocation, yLocation, "X", 12);
        }
    }

    /**
     * Draws the block on the given surface.
     *
     * @param d the surface to draw the block on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int upperLeftX = (int) this.location.getX();
        int upperLeftY = (int) this.location.getY();

        // draw block:
        if (fillsMap.containsKey(this.hitNumLeft)) {
            this.filler.fillFromString(this.hitNumLeft, location, d, (int) getHeight(), (int) getWidth());
        } else {
            this.filler.fillFromString(1, location, d, (int) getHeight(), (int) getWidth());
        }

        // draw stroke if exists:
        if (this.stroke != null) {
            d.setColor(stroke);
            d.drawRectangle(upperLeftX, upperLeftY, (int) getWidth(), (int) getHeight());
        }
    }

    /**
     * Notify the Block that time has passed. The block isn't moving so the method does nothing.
     * (Implemented because of the Sprite interface).
     */
    @Override
    public void timePassed() {

    }

    /**
     * Adds the block to the given game.
     *
     * @param game the game to add the block to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * Removes the block from the given game.
     *
     * @param game the game to remove the block from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Adds a given hit listener as a listener to hit events of the block.
     *
     * @param hl the hit listener to be added.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes a given hit listener from the list of listeners to hit events of the block.
     *
     * @param hl the listener to be removed.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies all listeners of the hit events of the block that a hit event has occurred.
     *
     * @param hitter the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {

        // Make a copy of the hitListeners before iterating over them:
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);

        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Gets the number of hits the block can get.
     *
     * @return number of hits left for the block.
     */
    public int getHitPoints() {
        return this.hitNumLeft;
    }

    /**
     * Gets the width of the block.
     *
     * @return width of block.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the block.
     *
     * @return height of block.
     */
    public double getHeight() {
        return this.height;
    }
}