package geometry;

import java.util.List;

/**
 * The class Point is a point object that has (x,y) coordinates.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructor: creates a new point with x, y coordinates.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The method gets another point and calculates the distance between that point and the point object.
     *
     * @param other another point object
     * @return distance of this point to the other point
     */
    public double distance(Point other) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = other.x;
        double y2 = other.y;
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    /**
     * Finds the list index of the closest point to this point, by comparing the distances
     * of each point in the list to this point.
     *
     * @param pointList list of points.
     * @return list index of the closest point.
     */
    public int findClosestPointIndex(List<Point> pointList) {
        double tempDis;
        double minDis;
        int pointIndex = 0;
        minDis = this.distance(pointList.get(0));

        // iterate pointList list to find the closest point:
        for (int i = 1; i < pointList.size(); i++) {
            tempDis = this.distance(pointList.get(i));
            if (tempDis < minDis) {
                minDis = tempDis;
                pointIndex = i;
            }
        }
        return pointIndex;
    }

    /**
     * Checks if the 2 points are equal.
     *
     * @param other another point object.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        if ((this.x == other.x) && (this.y == other.y)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the x value of the point.
     *
     * @return x value of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y value of the point.
     *
     * @return y value of the point.
     */
    public double getY() {
        return this.y;
    }
}