package animations;

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.List;

import blocks.Block;
import blocks.BoundaryBlock;
import basic.Collidable;
import basic.Counter;
import basic.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import sprites.Ball;
import sprites.Paddle;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Velocity;

/**
 * The game level class is in charge of the level of the game, and it holds the collidables and the sprites. It has
 * an environment, a keyboard sensor, counters, paddle, etc.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private biuoop.KeyboardSensor keyboard;
    private Counter blockCounter = new Counter(0);
    private Counter ballCounter = new Counter(0);
    private AnimationRunner runner;
    private Paddle paddle;
    private boolean running;
    private LevelInformation levelInfo;
    private Ball[] ballArr;
    private Counter score;
    private Counter lives;
    public static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    public static final int FRAME_SIZE = 5;
    private static final int PADDLE_H = 7;
    private static final int BALL_SIZE = 4;

    /**
     * Constructor: Creates a new gameLevel with given level information,
     * keyboard sensor, animation runner, score counter and lives counter.
     *
     * @param li level information.
     * @param ks keyboard sensor.
     * @param ar animation runner.
     * @param score score counter.
     * @param lives lives counter.
     */
    public GameLevel(LevelInformation li, biuoop.KeyboardSensor ks, AnimationRunner ar, Counter score, Counter lives) {
        this.levelInfo = li;
        this.runner = ar;
        this.keyboard = ks;
        this.score = score;
        this.lives = lives;
    }

    /**
     * Adds a given collidable to the environment of the game.
     *
     * @param c the collidable to be added.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a given sprite to the sprite collection of the game.
     *
     * @param s the sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes a new game by calling methods to set background, blocks, frame and death region.
     */
    public void initialize() {

        // set background:
        addSprite(levelInfo.getBackground());

        // set blocks:
        setBlocks();

        // set frame:
        setFrame();

        // set death region:
        setDeathRegion();
    }

    /**
     * Sets blocks as a game frame and adds them to the game.
     */
    private void setFrame() {

        // set sides and top frame:
        Rectangle topRect = new Rectangle(new Point(0, 30), SCREEN_WIDTH, FRAME_SIZE);
        Block topBlock = new BoundaryBlock(topRect, Color.GRAY);
        Rectangle rightRect = new Rectangle(new Point(SCREEN_WIDTH - FRAME_SIZE, 0), FRAME_SIZE, SCREEN_HEIGHT);
        Block rightBlock = new BoundaryBlock(rightRect, Color.GRAY);
        Rectangle leftRect = new Rectangle(new Point(0, 0), FRAME_SIZE, SCREEN_HEIGHT);
        Block leftBlock = new BoundaryBlock(leftRect, Color.GRAY);
        topBlock.addToGame(this);
        rightBlock.addToGame(this);
        leftBlock.addToGame(this);
    }

    /**
     * Sets the death region of the game.
     */
    private void setDeathRegion() {
        BallRemover ballRem = new BallRemover(this, ballCounter);
        Rectangle deathRect = new Rectangle(new Point(0, SCREEN_HEIGHT + BALL_SIZE), SCREEN_WIDTH, FRAME_SIZE);
        Block death = new BoundaryBlock(deathRect, Color.BLACK);
        death.addToGame(this);
        death.addHitListener(ballRem);
    }

    /**
     * Sets blocks for the game level and adds them to the game, as well as
     * setting listeners for score and hits, and updating the block counter.
     */
    private void setBlocks() {
        BlockRemover blockRem = new BlockRemover(this, this.blockCounter);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this.score);
        List<Block> blocksForLevel = levelInfo.blocks();

        // create blocks:
        for (Block block : blocksForLevel) {
            Block copyBlock = new Block(block);
            copyBlock.addHitListener(blockRem);
            copyBlock.addHitListener(scoreListener);
            copyBlock.addToGame(this);
        }
        blockCounter.increase(blocksForLevel.size());
    }

    /**
     * Indicates if the game level should stop.
     *
     * @return boolean value of 'running' member, true if the game should continue running or false if it should stop.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Does one frame of the animation: first checks if the game should pause, then draws the
     * current image of the animation and checks the game logic and the stopping conditions
     * of the game, i.e if there are still blocks and balls left.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {

        // check if the game should pause:
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }

        // draw the current image of the animation:
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        // check if there are blocks and balls left:
        if (this.blockCounter.getValue() == 0) {
            this.score.increase(100);

            // remove paddle and balls:
            paddle.removeFromGame(this);
            for (Ball ball : ballArr) {
                ball.removeFromGame(this);
            }
            this.running = false;
        }
        if (this.ballCounter.getValue() == 0) {
            this.lives.decrease(1);

            // remove paddle:
            paddle.removeFromGame(this);
            this.running = false;
        }
    }

    /**
     * Calls a method to create the balls and paddle for each turn and
     * a method for a count down animation and runs the current animation.
     */
    public void playOneTurn() {
        this.createBallsAndPaddle();

        // countdown before turn starts:
        this.runner.run(new CountdownAnimation(2, 3, sprites));
        this.running = true;

        // use our runner to run the current animation, which is one turn of the game:
        this.runner.run(this);
    }

    /**
     * Creates a paddle and balls with velocities from the levelInfo member.
     */
    private void createBallsAndPaddle() {
        List<Velocity> velocityForLevel = levelInfo.initialBallVelocities();
        ballArr = new Ball[levelInfo.numberOfBalls()];
        Point ballCenter = new Point(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT - FRAME_SIZE - PADDLE_H - BALL_SIZE);

        // set balls:
        int ballNum = levelInfo.numberOfBalls();
        for (int i = 0; i < ballNum; i++) {
            ballArr[i] = new Ball(ballCenter, BALL_SIZE, Color.WHITE, environment);
            ballArr[i].setVelocity(velocityForLevel.get(i));
            ballArr[i].addToGame(this);
            ballCounter.increase(1);
        }

        // set paddle:
        int paddleWidth = levelInfo.paddleWidth();
        Point paddleStart = new Point(SCREEN_WIDTH / 2.0 - paddleWidth / 2.0, SCREEN_HEIGHT - FRAME_SIZE - PADDLE_H);
        Rectangle paddleRect = new Rectangle(paddleStart, paddleWidth, PADDLE_H);
        paddle = new Paddle(keyboard, paddleRect, levelInfo.paddleSpeed());
        paddle.addToGame(this);
    }

    /**
     * Removes a given collidable from the game environment.
     *
     * @param c the collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a given sprite from the sprites list.
     *
     * @param s the sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Gets the block counter.
     *
     * @return block counter.
     */
    public Counter getBlockCounter() {
        return this.blockCounter;
    }

    /**
     * Gets the lives counter.
     *
     * @return lives counter.
     */
    public Counter getLivesCounter() {
        return this.lives;
    }
}