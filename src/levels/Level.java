package levels;

import blocks.Block;
import sprites.Sprite;
import sprites.Velocity;

import java.util.List;

/**
 * Level is a class representing a level inn the game.
 */
public class Level implements LevelInformation {
    private String name;
    private List<Velocity> ballVelocities;
    private int pSpeed;
    private int pWidth;
    private List<Block> blockList;
    private int blocksNum;
    private Sprite background;

    /**
     * Constructor: creates a new Level obgect.
     *
     * @param name name of the level.
     * @param ballVelocities list of velocities of balls in the level.
     * @param pSpeed paddle speed.
     * @param pWidth paddle width.
     * @param blockList list of blocks.
     * @param blocksNum number of blocks.
     * @param background background of the level.
     */
    public Level(String name, List<Velocity> ballVelocities, int pSpeed, int pWidth, List<Block> blockList,
                 int blocksNum, Sprite background) {
        this.name = name;
        this.ballVelocities = ballVelocities;
        this.pSpeed = pSpeed;
        this.pWidth = pWidth;
        this.blockList = blockList;
        this.blocksNum = blocksNum;
        this.background = background;
    }

    /**
     * Returns the number of balls in the level.
     *
     * @return number of balls in the level.
     */
    @Override
    public int numberOfBalls() {
        return this.ballVelocities.size();
    }

    /**
     * Returns the initial velocity of each ball.
     *
     * @return list of initial velocities of the balls.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }

    /**
     * Returns the paddles speed.
     *
     * @return paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return this.pSpeed;
    }

    /**
     * Returns the paddles width.
     *
     * @return paddles width.
     */
    @Override
    public int paddleWidth() {
        return this.pWidth;
    }

    /**
     * Returns the name of the level.
     *
     * @return name of level.
     */
    @Override
    public String levelName() {
        return this.name;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background of level as a sprite.
     */
    @Override
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Returns a list of the blocks that make up this level, each block contains its size, color and location.
     *
     * @return list of blocks in level.
     */
    @Override
    public List<Block> blocks() {
        return this.blockList;
    }

    /**
     * Returns the number of blocks that should be removed before the level is considered to be "cleared".
     *
     * @return number of blocks to be removed.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksNum;
    }
}
