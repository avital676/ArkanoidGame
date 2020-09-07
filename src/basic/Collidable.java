package basic;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Velocity;

/**
 * The Collidable interface is implemented in classes of objects that could be collided with.
 * It has methods to return the rectangle that was collided, to return the new velocity after the collision
 * and to draw the collidable on a given surface.
 */
public interface Collidable {
    /**
     * Gets the collision shape of the collidable.
     *
     * @return "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that another object collided with it at collisionPoint with
     * a given velocity.
     *
     * @param hitter the ball doing the hitting.
     * @param collisionPoint the point where the collision occurred.
     * @param currentVelocity the velocity in which the collision occurred.
     * @return new velocity after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Draws the collidable on a given surface.
     *
     * @param d surface to draw the collidable on.
     */
    void drawOn(DrawSurface d);
}