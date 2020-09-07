package sprites;

import biuoop.DrawSurface;
import basic.CollisionInfo;
import animations.GameLevel;
import basic.GameEnvironment;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

/**
 * The class Ball is a ball object. The ball has a center, radius, color and
 * velocity, and it can move. The class implements the Sprite interface.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment gameEnv;

    /**
     * Constructor: creates a new ball by setting its center, radius, color and velocity (set to 0 by default).
     *
     * @param center center point of the ball to be created.
     * @param r radius of the ball.
     * @param color color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;

        // set velocity 0 in case setVelocity() isn't invoked.
        this.v = new Velocity(0, 0);
    }

    /**
     * Constructor: calls the other constructor with a new center point.
     *
     * @param x x coordinate of the center of the ball.
     * @param y y coordinate of the center of the ball.
     * @param r radius of the ball.
     * @param color color if the ball.
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this(new Point(x, y), r, color);
    }

    /**
     * Constructor: calls the first constructor with a center, radius and color,
     * and sets the game environment.
     *
     * @param center center point of the ball.
     * @param r radius of the ball.
     * @param color color of the ball.
     * @param gameEnv game environment of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnv) {
        this(center, r, color);
        this.gameEnv = gameEnv;
    }

    /**
     * Gets the x value of the center of the ball.
     *
     * @return x value of the center of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y  value of the center of the ball.
     *
     * @return y value of the center of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return ball radius.
     */
    public int getSize() {
        return (int) this.r;
    }

    /**
     * Gets the ball color.
     *
     * @return ball color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface a gui DrawSurface.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        int x = this.getX();
        int y = this.getY();
        int radius = this.getSize();

        // draw the ball:
        surface.setColor(this.getColor());
        surface.fillCircle(x, y, radius);
    }

    /**
     * Sets a velocity for the ball.
     *
     * @param velocity the velocity to be set for the ball.
     */
    public void setVelocity(Velocity velocity) {
        this.v = velocity;
    }

    /**
     * Sets a velocity for the ball.
     *
     * @param dx dx value of velocity.
     * @param dy dy value of velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Moves the ball one step in accordance to the velocity, by setting a new location
     * for its center. In case the step would take the ball to hit a collidable,
     * it changes the current location of the ball so it almost hits the collidable.
     * Then it changes the velocity direction so that the ball will bounce to the opposite direction.
     */
    public void moveOneStep() {
        Line trajectory = new Line(this.center, this.getVelocity().applyToPoint(this.center));
        CollisionInfo collisionInfo = this.gameEnv.getClosestCollision(trajectory);
        double x = this.center.getX();
        double y = this.center.getY();
        if (collisionInfo == null) {

            // no collision in current step:
            this.center = this.getVelocity().applyToPoint(this.center);
            return;
        }

        // check collision location on the collision object, y:
        Rectangle rect = collisionInfo.collisionObject().getCollisionRectangle();
        Point p = collisionInfo.collisionPoint();
        if ((this.getVelocity().getDY() < 0)
                && (rect.getBottomSide().isPointInLine(p, rect.getBottomSide()))) {
            y = p.getY() + this.r;
        }
        if ((this.getVelocity().getDY() > 0)
                && (rect.getTopSide().isPointInLine(p, rect.getTopSide()))) {
            y = p.getY() - this.r;
        }

        // check collision location on the collision object, x:
        if ((this.getVelocity().getDX() > 0)
                && (rect.getLeftSide().isPointInLine(p, rect.getLeftSide()))) {
            x = p.getX() - this.r;
        }
        if ((this.getVelocity().getDX() < 0)
                && (rect.getRightSide().isPointInLine(p, rect.getRightSide()))) {
            x = p.getX() + this.r;
        }
        this.center = new Point(x, y);

        // change velocity:
        this.setVelocity(collisionInfo.collisionObject().hit(this, p, this.getVelocity()));
    }

    /**
     * Sets the balls game environment to the given game environment.
     *
     * @param gameEnvironment the game environment to be set for the ball.
     */
    public void setGamEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnv = gameEnvironment;
    }

    /**
     * Notifies the ball that time has passed, and calls another method to move the ball.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Adds the ball to the given game.
     *
     * @param game the game to add the ball to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Removes the ball from the given game.
     *
     * @param game the game to remove the ball from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}