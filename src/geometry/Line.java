package geometry;

import java.util.List;

/**
 * The class Line is a line object that has start and end coordinates,
 * length, middle point and intersection point with the x axis (zeroY field).
 */
public class Line {
    private Point start;
    private Point end;
    private double length;
    private Point mid;
    private double zeroY; // intersection with x axis
    private static final double DEVIATION =  0.1;

    /**
     * Constructor: creates a new line from start and end points, and sets the length of the line.
     *
     * @param start starting point of the line to be created.
     * @param end ending point of the line to be created.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.mid = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
        this.length = start.distance(end);
    }

    /**
     * Constructor: calls the other constructor to set start and end points,
     * then sets the middle point of the new line.
     *
     * @param x1 x value of the starting point.
     * @param y1 y value of the starting point.
     * @param x2 x value of the ending point.
     * @param y2 y value of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
        this.mid = new Point(((x1 + x2) / 2), ((y1 + y2) / 2));
    }

    /**
     * Gets the length of the line.
     *
     * @return length of line.
     */
    public double length() {
        return this.length;
    }

    /**
     * Gets the middle point of the line.
     *
     * @return middle point of line.
     */
    public Point middle() {
        return this.mid;
    }

    /**
     * Gets the starting point of the line.
     *
     * @return start point of line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Gets the ending point of the line.
     *
     * @return end point of line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Calculates the slope of the line and sets the zeroY field (intersection with x axis point).
     *
     * @return slope of line.
     */
    public double slope() {
        double slope;
        double x1 = this.start().getX();
        double y1 = this.start().getY();
        double x2 = this.end().getX();
        double y2 = this.end().getY();
        slope = (y2 - y1) / (x2 - x1);

        // set intersection with x axis point:
        this.zeroY = y1 - slope * x1;
        return slope;
    }

    /**
     * Checks if the line is intersecting with the other line by calling another method to find whether
     * there's an intersection point or not.
     *
     * @param other a line to be checked for intersection.
     * @return true if the 2 lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {

        // call a method to check for intersection:
        if (intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the 2 lines intersect and calls other methods for extreme
     * cases (e.g. vertical or overlapping lines).
     *
     * @param other line to be checked for intersection.
     * @return intersection point if the 2 lines intersect, null if they
     * don't intersect or if they overlap.
     */
    public Point intersectionWith(Line other) {
        double x;
        double y;
        Point interP = null;
        boolean thisVertical = false;
        boolean otherVertical = false;

        // check if lines are vertical to x axis:
        if (this.start().getX() == this.end().getX()) {
            thisVertical = true;
        }
        if (other.start().getX() == other.end().getX()) {
            otherVertical = true;
        }

        // start checking intersection:
        // check if both lines are vertical to x axis:
        if (thisVertical && otherVertical) {

            // both vertical to x axis. Check if they have the same x value:
            if (this.start.getX() != other.start.getX()) {

                // both vertical but don't have the same x, i.e don't intersect
                return null;
            } else {

                // check if lines have a single mutual point (don't overlap):
                interP = singlePoint(this, other);
            }
        } else if (thisVertical) {

            // find intersection point with the other line:
            interP = verticalIntersection(this.start().getX(), other);
        } else if (otherVertical) {

            // find intersection point with 'this' line:
            interP = verticalIntersection(other.start().getX(), this);
        } else if (this.slope() == other.slope()) {

            // check if the lines are parallel or overlapping:
            interP = singlePoint(this, other);
        } else {

            // calculate x value of intersection:
            x = (other.zeroY - this.zeroY) / (this.slope() - other.slope());

            // calculate y value of intersection:
            y = this.slope() * x + this.zeroY;

            // create a point for the intersection:
            interP = new Point(x, y);
        }
        if (interP == null) {
            return null;
        }

        // check if interP is in both lines:
        boolean inLine1 = isPointInLine(interP, this);
        boolean inLine2 = isPointInLine(interP, other);
        if (inLine1 && inLine2) {
            return interP;
        }
        return null;
    }

    /**
     * Finds the intersection point of 'this' vertical line with 'other line'.
     *
     * @param x x value of intersection point.
     * @param other line to check the intersection with.
     * @return intersection point.
     */
    public Point verticalIntersection(double x, Line other) {
        double y = other.slope() * x + other.zeroY;
        return new Point(x, y);
    }

    /**
     * Checks if the lines have more than one mutual point by comparing their start
     * and end points and checking if the points are contained in the given lines.
     *
     * @param l1 first line.
     * @param l2 second line.
     * @return mutual point if it's a single mutual point, or null if it's not a single point.
     */
    public Point singlePoint(Line l1, Line l2) {
        Point start1 = l1.start();
        Point end1 = l1.end();
        Point start2 = l2.start();
        Point end2 = l2.end();
        if ((start1.equals(start2)) && (!end1.equals(end2))
                && (!isPointInLine(end1, l2)) && (!isPointInLine(end2, l1))) {
            return start1;
        }
        if ((start1.equals(end2)) && (!end1.equals(start2))
                && (!isPointInLine(end1, l2)) && (!isPointInLine(start2, l1))) {
            return start1;
        }
        if ((end1.equals(start2)) && (!start1.equals(end2))
                && (!isPointInLine(start1, l2)) && (!isPointInLine(end2, l1))) {
            return end1;
        }
        if ((end1.equals(end2)) && (!start1.equals(start2))
                && (!isPointInLine(start1, l2)) && (!isPointInLine(start2, l1))) {
            return end1;
        }
        return null;
    }

    /**
     * Checks if the given point is contained in the given line, by checking
     * if it's between the x and y values of the start and end of the line,
     * considering small deviations.
     *
     * @param p the point to be checked if contained in the given line.
     * @param line the line to be checked if contains the given point.
     * @return true if the point is contained in the line, false if it's not.
     */
    public boolean isPointInLine(Point p, Line line) {
        if ((p.getX() > Math.min(line.start.getX(), line.end.getX()) - DEVIATION)
                && (p.getX() < Math.max(line.start.getX(), line.end.getX()) + DEVIATION)
                && (p.getY() > Math.min(line.start.getY(), line.end.getY()) - DEVIATION)
                && (p.getY() < Math.max(line.start.getY(), line.end.getY()) + DEVIATION)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the given lines are equal by comparing their start and end points.
     *
     * @param other line to be compared to.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        if ((this.start == other.start) && (this.end == other.end)) {
            return true;
        }
        if ((this.start == other.end) && (this.end == other.start)) {
            return true;
        }
        return false;
    }

    /**
     * Finds the intersection point of a given rectangle with the line, which is the closest to the
     * start point of the line.
     *
     * @param rect the rectangle to be checked.
     * @return closest intersection point to the start of the line or null if there's no intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        int closestIntersectionIndex;
        List<Point> pointList = rect.intersectionPoints(this);
        if (pointList.size() == 0) {
            return null;
        }
        closestIntersectionIndex = this.start.findClosestPointIndex(pointList);
        return pointList.get(closestIntersectionIndex);
    }
}