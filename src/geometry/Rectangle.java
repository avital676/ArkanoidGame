package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Rectangle class has 4 points (upper left, down left, upper right and down right).
 * It has 4 sides, width and height and has a method to find intersection of lines with it.
 */
public class Rectangle {
    private Point upLeft;
    private Point upRight;
    private Point downLeft;
    private Point downRight;
    private double width;
    private double height;
    private Line leftSide;
    private Line rightSide;
    private Line topSide;
    private Line bottomSide;

    /**
     * Constructor: creates a new rectangle with location, width and height.
     *
     * @param upperLeft upper left point of the rectangle.
     * @param width width of the rectangle.
     * @param height height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the points and sides of the rectangle.
     */
    public void sidesAndPoints() {
        this.upRight = new Point(this.upLeft.getX() + width, this.upLeft.getY());
        this.downLeft = new Point(this.upLeft.getX(), this.upLeft.getY() + height);
        this.downRight = new Point(this.upLeft.getX() + width, this.upLeft.getY() + height);
        this.leftSide = new Line(this.upLeft, this.downLeft);
        this.rightSide = new Line(this.upRight, this.downRight);
        this.topSide = new Line(this.upLeft, this.upRight);
        this.bottomSide = new Line(this.downLeft, this.downRight);
    }

    /**
     * Gets the left side of the rectangle.
     *
     * @return left side of rectangle (line).
     */
    public Line getLeftSide() {
        return this.leftSide;
    }

    /**
     * Gets the right side of the rectangle.
     *
     * @return right side of rectangle (line).
     */
    public Line getRightSide() {
        return this.rightSide;
    }

    /**
     * Gets the top side of the rectangle.
     *
     * @return top side of rectangle (line).
     */
    public Line getTopSide() {
        return this.topSide;
    }

    /**
     * Gets the bottom side of the rectangle.
     *
     * @return bottom side of rectangle (line).
     */
    public Line getBottomSide() {
        return this.bottomSide;
    }

    /**
     * Creates a list of intersection points of the rectangle with the given line,
     * by sending each side of the rectangle to a method checking for intersection
     * and adding intersection points to the list.
     *
     * @param line the line intersecting (or not intersecting) with the rectangle.
     * @return list of intersection points with the given line (could be an empty list).
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        sidesAndPoints();
        List<Point> pointList = new ArrayList<Point>();
        pointList = addPoint(line, this.leftSide, pointList);
        pointList = addPoint(line, this.rightSide, pointList);
        pointList = addPoint(line, this.topSide, pointList);
        pointList = addPoint(line, this.bottomSide, pointList);
        return pointList;
    }

    /**
     * Adds the intersection point of the line with the given rectangle side to the given list.
     * In case there's no intersection point, it returns the list without any changes.
     *
     * @param line the line intersecting with the rectangle side.
     * @param side the side that the line is intersecting with.
     * @param listP list of intersection points.
     * @return updated list of intersection points.
     */
    public java.util.List<Point> addPoint(Line line, Line side, List<Point> listP) {
        Point interP = line.intersectionWith(side);
        if (interP != null) {
            listP.add(interP);
        }
        return listP;
    }

    /**
     * Gets the width of the rectangle.
     *
     * @return width of rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the rectangle.
     *
     * @return height of rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Gets the upper left point of the rectangle.
     *
     * @return upper left point of rectangle.
     */
    public Point getUpperLeft() {
        return this.upLeft;
    }
}