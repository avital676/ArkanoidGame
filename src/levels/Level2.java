//package levels;
//
//import geometry.Point;
//import geometry.Rectangle;
//import blocks.Block;
//import sprites.Sprite;
//import sprites.Velocity;
//import sprites.Level2Background;
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Level2 is the second level of the Arkanoid game.
// */
//public class Level2 implements LevelInformation {
//    private static final double PADDLE_SPEED = 2;
//    private static final int PADDLE_WIDTH = 600;
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
//        for (int i = 1; i <= 5; i++) {
//            velocityList.add(Velocity.fromAngleAndSpeed(i * 10, 5));
//            velocityList.add(Velocity.fromAngleAndSpeed(360 - i * 10, 5));
//        }
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
//        return ("Wide Easy");
//    }
//
//    /**
//     * Returns a sprite with the background of the level.
//     *
//     * @return background of level as a sprite.
//     */
//    @Override
//    public Sprite getBackground() {
//        return new Level2Background();
//    }
//
//    /**
//     * Returns a list of the blocks that make up this level, each block contains its size, color and location.
//     *
//     * @return list of blocks in level.
//     */
//    @Override
//    public List<Block> blocks() {
//        double xStart = 20;
//        double width = 47.5;
//        List<Block> blockList = new ArrayList<Block>();
//        List<Color> colorList = this.makeColor();
//        for (Color color : colorList) {
//            blockList.add(new Block(new Rectangle(new Point(xStart, 250), width, 25), color, 1));
//            blockList.add(new Block(new Rectangle(new Point(xStart + width, 250), width, 25), color, 1));
//            xStart = xStart + width * 2;
//        }
//        return blockList;
//    }
//
//    /**
//     * Creates a list of different colors.
//     *
//     * @return list of colors.
//     */
//    private List<Color> makeColor() {
//        List<Color> colorList = new ArrayList<Color>();
//        colorList.add(Color.PINK);
//        colorList.add(Color.MAGENTA);
//        colorList.add(Color.RED);
//        colorList.add(Color.ORANGE);
//        colorList.add(Color.YELLOW);
//        colorList.add(Color.GREEN);
//        colorList.add(Color.BLUE);
//        colorList.add(Color.CYAN);
//        return colorList;
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