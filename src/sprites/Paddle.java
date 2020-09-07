package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import basic.Collidable;
import animations.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;

/**
 * The paddle class implements the Sprite and Collidable interfaces. It has a keyboard sensor
 * and a rectangle. The paddle has methods to move right and left.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private int speed;
    private static final double ANGLE1 = -60;
    private static final double ANGLE2 = -30;
    private static final double ANGLE4 = 30;
    private static final double ANGLE5 = 60;

    /**
     * Constructor: creates a new paddle with given keyboard sensor, rectangle and speed.
     *
     * @param keyboard keyboard sensor set for the paddle.
     * @param rect rectangle set for the paddle.
     * @param speed speed of the paddle.
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle rect, int speed) {
        this.keyboard = keyboard;
        this.rect = rect;
        this.speed = speed;
    }

    /**
     * Moves the paddle left inside the frame. In case the paddle gets to the left border of the frame
     * or is going to get there in the current step, it puts it next to the frame. Otherwise it moves it
     * left by DX.
     */
    private void moveLeft() {
        Point newUpLeft;
        double oldUpLeftX = this.rect.getUpperLeft().getX();
        double oldUpLeftY = this.rect.getUpperLeft().getY();

        // set a new upper left point considering the location of the paddle regarding the left frame block:
        if (oldUpLeftX - speed <= GameLevel.FRAME_SIZE) {
            newUpLeft = new Point(GameLevel.FRAME_SIZE, oldUpLeftY);
        } else {
            newUpLeft = new Point(oldUpLeftX - speed, oldUpLeftY);
        }
        this.rect = new Rectangle(newUpLeft, this.rect.getWidth(), this.rect.getHeight());
    }

    /**
     * Moves the paddle right inside the frame. In case the paddle gets to the right border of the frame
     * or is going to get there in the current step, it puts it next to the frame. Otherwise it moves it
     * right by DX.
     */
    private void moveRight() {
        Point newUpLeft;
        double oldUpLeftX = this.rect.getUpperLeft().getX();
        double oldUpLeftY = this.rect.getUpperLeft().getY();

        // set a new upper left point considering the location of the paddle regarding the right frame block:
        if (oldUpLeftX + rect.getWidth() + speed >= GameLevel.SCREEN_WIDTH - GameLevel.FRAME_SIZE) {
            newUpLeft = new Point(GameLevel.SCREEN_WIDTH - GameLevel.FRAME_SIZE - rect.getWidth(), oldUpLeftY);
        } else {
            newUpLeft = new Point(oldUpLeftX + speed, oldUpLeftY);
        }
        this.rect = new Rectangle(newUpLeft, this.rect.getWidth(), this.rect.getHeight());
    }

    /**
     * Notifies the paddle that time has passed. If the left key of the keyboard is pressed it calls a method
     * to move the paddle left, and if the right key is pressed it calls a method to move the paddle right.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on a given surface.
     *
     * @param d surface to draw the paddle on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int upperLeftX = (int) this.rect.getUpperLeft().getX();
        int upperLeftY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();

        // draw the block:
        d.setColor(Color.BLACK);
        d.drawRectangle(upperLeftX, upperLeftY, width, height);
        d.setColor(Color.ORANGE);
        d.fillRectangle(upperLeftX, upperLeftY, width, height);
    }

    /**
     * Get the rectangle which is the paddle.
     *
     * @return the paddle rectangle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Notify the paddle that it was hit, check on which region of the paddle the hit occurred and
     * determine a new velocity based on the velocity the object had before the hit.
     *
     * @param hitter the ball doing the hitting.
     * @param collisionPoint the collision point with the paddle.
     * @param currentVelocity the velocity of object hitting the paddle.
     * @return new velocity expected after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double hitterSpeed = Math.sqrt(currentVelocity.getDX() * currentVelocity.getDX()
                + currentVelocity.getDY() * currentVelocity.getDY());

        // set the paddles different regions:
        double regWidth = this.rect.getWidth() / 5;
        double y = this.rect.getUpperLeft().getY();
        double leftX = this.rect.getUpperLeft().getX();
        Point p1 = this.rect.getUpperLeft();
        Point p2 = new Point(leftX + regWidth, y);
        Point p3 = new Point(leftX + 2 * regWidth, y);
        Point p4 = new Point(leftX + 3 * regWidth, y);
        Point p5 = new Point(leftX + 4 * regWidth, y);
        Point p6 = new Point(leftX + 5 * regWidth, y);
        Line segment1 = new Line(p1, p2);
        Line segment2 = new Line(p2, p3);
        Line segment3 = new Line(p3, p4);
        Line segment4 = new Line(p4, p5);
        Line segment5 = new Line(p5, p6);

        // check in which segment the hit occurred and change the velocity:
        if ((segment1.isPointInLine(collisionPoint, segment1))
                || (rect.getLeftSide().isPointInLine(collisionPoint, rect.getLeftSide()))) {
            return Velocity.fromAngleAndSpeed(ANGLE1, hitterSpeed);
        }
        if (segment2.isPointInLine(collisionPoint, segment2)) {
            return Velocity.fromAngleAndSpeed(ANGLE2, hitterSpeed);
        }
        if (segment3.isPointInLine(collisionPoint, segment3)) {
            return new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
        }
        if (segment4.isPointInLine(collisionPoint, segment4)) {
            return Velocity.fromAngleAndSpeed(ANGLE4, hitterSpeed);
        }
        if ((segment5.isPointInLine(collisionPoint, segment5))
                || (rect.getRightSide().isPointInLine(collisionPoint, rect.getRightSide()))) {
            return Velocity.fromAngleAndSpeed(ANGLE5, hitterSpeed);
        }
        return currentVelocity;
    }

    /**
     * Adds the paddle to a given game.
     *
     * @param g the game to add the paddle to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Removes the paddle from a given game.
     *
     * @param game the game to remove the paddle from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
}