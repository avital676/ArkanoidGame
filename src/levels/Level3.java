//package levels;
//
//import blocks.Block;
//import sprites.Sprite;
//import sprites.Velocity;
//import sprites.Level3Background;
//import geometry.Rectangle;
//import geometry.Point;
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Level3 is the third level of the Arkanoid game.
// */
//public class Level3 implements LevelInformation {
//    private static final double PADDLE_SPEED = 12;
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
//        velocityList.add(Velocity.fromAngleAndSpeed(30, 9));
//        velocityList.add(Velocity.fromAngleAndSpeed(330, 5.5));
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
//        return ("Green 3");
//    }
//
//    /**
//     * Returns a sprite with the background of the level.
//     *
//     * @return background of level as a sprite.
//     */
//    @Override
//    public Sprite getBackground() {
//        return new Level3Background();
//    }
//
//    /**
//     * Returns a list of the blocks that make up this level, each block contains its size, color and location.
//     *
//     * @return list of blocks in level.
//     */
//    @Override
//    public List<Block> blocks() {
//        List<Block> blockList = new ArrayList<Block>();
//        int rowY = 100;
//        int blockHeight = 25;
//
//        // add blocks in rows to the list:
//        blockList.addAll(blockRow(10, rowY, Color.decode("#00CC00")));
//        blockList.addAll(blockRow(9, rowY + blockHeight, Color.decode("#1ADE1A")));
//        blockList.addAll(blockRow(8, rowY + blockHeight * 2, Color.decode("#33FF33")));
//        blockList.addAll(blockRow(7, rowY + blockHeight * 3, Color.decode("#66FF66")));
//        blockList.addAll(blockRow(6, rowY + blockHeight * 4, Color.decode("#99FF99")));
//        return blockList;
//    }
//
//    /**
//     * Creates a list of blocks in row having a given amount of blocks in the row, located at a given height
//     * on the screen surface, with a given color and hit number (i.e how many times the block can be hit).
//     *
//     * @param amount amount of blocks in the row.
//     * @param rowHeight y location of the block row on the screen surface.
//     * @param color color of the blocks in the row.
//     * @return list of blocks in row.
//     */
//    private List<Block> blockRow(int amount, int rowHeight, java.awt.Color color) {
//        List<Block> blockList = new ArrayList<Block>();
//
//        // set x location of the beginning of the first block:
//        int blockStart = 720;
//
//        // create the blocks in the row:
//        for (int i = 0; i < amount; i++) {
//            blockList.add(new Block(new Rectangle(new Point(blockStart, rowHeight), 60, 25), color, 1));
//
//            // set x location of the next block:
//            blockStart = blockStart - 60;
//        }
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