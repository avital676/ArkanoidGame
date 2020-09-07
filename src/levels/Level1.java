//package levels;
//
//import geometry.Point;
//import geometry.Rectangle;
//import blocks.Block;
//import sprites.Sprite;
//import sprites.Velocity;
//import sprites.Level1Background;
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Level1 is the first level of the Arkanoid game.
// */
//public class Level1 implements LevelInformation {
//    private static final double PADDLE_SPEED = 7;
//    private static final int PADDLE_WIDTH = 120;
//
//    /**
//     * Returns the number of balls in the level.
//     *
//     * @return number of balls in the level.
//     */
//    @Override
//    public int numberOfBalls() {
//        return this.initialBallVelocities().size();
//    }
//
//    /**
//     * Returns the initial velocity of each ball.
//     *
//     * @return list of initial velocities of the balls.
//     */
//    @Override
//    public List<Velocity> initialBallVelocities() {
//        List<Velocity> velocityList = new ArrayList<Velocity>();
//        velocityList.add(Velocity.fromAngleAndSpeed(0, 5));
//        return velocityList;
//    }
//
//    /**
//     * Returns the paddles speed.
//     *
//     * @return paddle speed.
//     */
//    @Override
//    public int paddleSpeed() {
//        return (int) PADDLE_SPEED;
//    }
//
//    /**
//     * Returns the paddles width.
//     *
//     * @return paddles width.
//     */
//    @Override
//    public int paddleWidth() {
//        return PADDLE_WIDTH;
//    }
//
//    /**
//     * Returns the name of the level.
//     *
//     * @return name of level.
//     */
//    @Override
//    public String levelName() {
//        return ("Direct Hit");
//    }
//
//    /**
//     * Returns a sprite with the background of the level.
//     *
//     * @return background of level as a sprite.
//     */
//    @Override
//    public Sprite getBackground() {
//        return new Level1Background();
//    }
//
//    /**
//     * Returns a list of the blocks that make up this level, each block contains its size, color and location.
//     *
//     * @return list of blocks in level.
//     */
//    @Override
//    public List<Block> blocks() {
//        Point upLeftLocation = new Point(379, 144);
//        Rectangle rect = new Rectangle(upLeftLocation, 42, 42);
//        List<Block> blockList = new ArrayList<Block>();
//        blockList.add(new Block(rect, Color.RED, 1));
//        return blockList;
//    }
//
//    /**
//     * Returns the number of blocks that should be removed before the level is considered to be "cleared".
//     *
//     * @return number of blocks to be removed.
//     */
//    @Override
//    public int numberOfBlocksToRemove() {
//        return this.blocks().size();
//    }
//}