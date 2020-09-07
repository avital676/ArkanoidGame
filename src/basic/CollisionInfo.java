package basic;

import geometry.Point;

/**
 * CollisionInfo holds information about collisions. It has a collision point and a collision object.
 */
public class CollisionInfo {
    private Point collPoint;
    private Collidable collObject;

    /**
     * Constructor: create a new collision info with a collision point and a collision object.
     *
     * @param collPoint the point at which the collision occurred.
     * @param collObject the object that was collided.
     */
    public CollisionInfo(Point collPoint, Collidable collObject) {
        this.collPoint = collPoint;
        this.collObject = collObject;
    }

    /**
     * Get the point at which the collision occurs.
     *
     * @return point at which collision occurs.
     */
    public Point collisionPoint() {
        return this.collPoint;
    }

    /**
     * Get the collidable object involved in the collision.
     *
     * @return collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collObject;
    }
}