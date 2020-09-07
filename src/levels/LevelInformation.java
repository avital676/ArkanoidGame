package levels;

import blocks.Block;
import sprites.Sprite;
import sprites.Velocity;
import java.util.List;

/**
 * LevelInformation interface is implemented in classes of levels, and gives the
 * information about the level (number of balls, velocities, blocks ets).
 */
public interface LevelInformation {

    /**
     * Returns the number of balls in the level.
     *
     * @return number of balls in the level.
     */
    int numberOfBalls();

    /**
     * Returns the initial velocity of each ball.
     *
     * @return list of initial velocities of the balls.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns the paddles speed.
     *
     * @return paddle speed.
     */
    int paddleSpeed();

    /**
     * Returns the paddles width.
     *
     * @return paddles width.
     */
    int paddleWidth();

    /**
     * Returns the name of the level.
     *
     * @return name of level.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background of level as a sprite.
     */
    Sprite getBackground();

    /**
     * Returns a list of the blocks that make up this level, each block contains its size, color and location.
     *
     * @return list of blocks in level.
     */
    List<Block> blocks();

    /**
     * Returns the number of blocks that should be removed before the level is considered to be "cleared".
     *
     * @return number of blocks to be removed.
     */
    int numberOfBlocksToRemove();
}