//package levels;
//
//import geometry.Point;
//import blocks.Block;
//import sprites.Level4Background;
//import sprites.Sprite;
//import sprites.Velocity;
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Level4 is the fourth level of the Arkanoid game.
// */
//public class Level4 implements LevelInformation {
//    private static final double PADDLE_SPEED = 14;
//    private static final int PADDLE_WIDTH = 125;
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
//        velocityList.add(Velocity.fromAngleAndSpeed(30, 7.5));
//        velocityList.add(Velocity.fromAngleAndSpeed(5, 6));
//        velocityList.add(Velocity.fromAngleAndSpeed(330, 5));
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
//        return ("Final Four");
//    }
//
//    /**
//     * Returns a sprite with the background of the level.
//     *
//     * @return background of level as a sprite.
//     */
//    @Override
//    public Sprite getBackground() {
//        return new Level4Background();
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
//        blockList.addAll(blockRow(rowY, Color.decode("#FF6666")));
//        blockList.addAll(blockRow(rowY + blockHeight, Color.decode("#FF9933")));
//        blockList.addAll(blockRow(rowY + blockHeight * 2, Color.decode("#FFFF33")));
//        blockList.addAll(blockRow(rowY + blockHeight * 3, Color.decode("#B2FF66")));
//        blockList.addAll(blockRow(rowY + blockHeight * 4, Color.decode("#99FFCC")));
//        blockList.addAll(blockRow(rowY + blockHeight * 5, Color.decode("#A1A1FF")));
//        blockList.addAll(blockRow(rowY + blockHeight * 6, Color.decode("#B266FF")));
//        return blockList;
//    }
//
//    /**
//     * Creates a list of blocks in a given color, at a given height on the screen.
//     *
//     * @param rowHeight height at which to locate the blocks.
//     * @param color color of the blocks.
//     * @return list of blocks in the row.
//     */
//    private List<Block> blockRow(int rowHeight, java.awt.Color color) {
//        List<Block> blockList = new ArrayList<Block>();
//
//        // set x location of the beginning of the first block:
//        double blockStart = 732.5;
//
//        // create the blocks in the row:
//        for (int i = 0; i < 16; i++) {
//            blockList.add(new Block(new geometry.Rectangle(new Point(blockStart, rowHeight),
//                    47.5, 25), color, 1));
//
//            // set x location of the next block:
//            blockStart = blockStart - 47.5;
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