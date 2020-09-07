package sprites;

import geometry.Point;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor: creates a new velocity with the given dx, dy.
     *
     * @param dx dx value of the velocity.
     * @param dy dy value of the velocity.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Converts a given angle and speed to velocity in terms of dx and dy, with angle 0 fixed as 'up'.
     *
     * @param angle angle of the object.
     * @param speed speed of the object.
     * @return velocity of the object in terms of dx, dy.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double rad = Math.toRadians(angle);
        double dx = speed * Math.sin(rad);
        double dy = speed * Math.cos(rad) * (-1);
        return new Velocity(dx, dy);
    }

    /**
     * Gets the dx value of velocity.
     *
     * @return dx value of velocity.
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * Gets the dy value of velocity.
     *
     * @return dy value of velocity.
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * Takes a point with position (x, y) and sets it a new location according to the velocity.
     *
     * @param p the point to change location to.
     * @return new point with a position of (x+dx, y+dy).
     */
    public Point applyToPoint(Point p) {
        double x = p.getX() + this.dx;
        double y = p.getY() + this.dy;
        return new Point(x, y);
    }
}