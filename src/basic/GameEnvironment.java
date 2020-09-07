package basic;

import java.util.ArrayList;
import java.util.List;

import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

/**
 * The game environment class has a list of collidables. It has methods to add colidables and
 * to find the closest collision with the collidables.
 */
public class GameEnvironment {
    private List<Collidable> collidableList;

    /**
     * Constructor: creates a new game environment that has a list of collidables.
     */
    public GameEnvironment() {
        this.collidableList = new ArrayList<>();
    }

    /**
     * Adds a given collidable to the list of collidables of the game environment.
     *
     * @param c collidable to be added to the list.
     */
    public void addCollidable(Collidable c) {
        collidableList.add(c);
    }

    /**
     * Removes a given collidable from the collidables list.
     *
     * @param c collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        collidableList.remove(c);
    }

    /**
     * The method finds the closest collision point with the line trajectory for each collidable
     * by calling another method, and saves the points in a list, as well as the indexes of the
     * collidables colliding with the given line. Then it calls another method to search the list
     * for the collision point that is the closest to the start of the line.
     *
     * @param trajectory the trajectory of the moving object.
     * @return collision info if there is a collision, null otherwise.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestCollisionPoint;
        Point tempPoint;
        Collidable collideObject;
        int pointIndex;

        // create a list of collision points and a list for indexes:
        List<Point> pointList = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();

        // iterate the collidables:
        List<Collidable> copyCollidableList = this.collidableList;
        for (int i = 0; i < copyCollidableList.size(); i++) {
            Rectangle rect = copyCollidableList.get(i).getCollisionRectangle();
            tempPoint = trajectory.closestIntersectionToStartOfLine(rect);

            // if the point isn't null, add it to the intersection points list:
            if (tempPoint != null) {
                pointList.add(tempPoint);

                // each value in indexList holds the index of the added collidable in the collidableList.
                indexList.add(i);
            }
        }
        if (pointList.size() != 0) {

            // find the closest collision point:
            pointIndex = trajectory.start().findClosestPointIndex(pointList);
            closestCollisionPoint = pointList.get(pointIndex);
            collideObject = copyCollidableList.get(indexList.get(pointIndex));
            return new CollisionInfo(closestCollisionPoint, collideObject);
        }

        // list is empty:
        return null;
    }
}