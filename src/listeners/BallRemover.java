package listeners;

import basic.Counter;
import animations.GameLevel;
import sprites.Ball;
import blocks.Block;

/**
 * BallRemover is in charge of removing balls from the game, as well as
 * keeping count of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Constructor: creates a new BallRemover with a reference to a game and to a counter of blocks.
     *
     * @param game game from which the ball should be removed.
     * @param remainingBlocks counter of blocks in the game.
     */
    public BallRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBalls = remainingBlocks;
    }

    /**
     * Acts when a hit is occurred- removes the given hitter from the game and decreases
     * the number of balls by decreasing the balls counter value.
     *
     * @param beingHit the block hit by the given ball (hitter).
     * @param hitter the ball that hit the block (beingHit).
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        remainingBalls.decrease(1);
    }
}