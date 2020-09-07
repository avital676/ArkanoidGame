package listeners;

import basic.Counter;
import animations.GameLevel;
import sprites.Ball;
import blocks.Block;

/**
 * BlockRemover is in charge of removing blocks from the game, as well as
 * keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Constructor: creates a new BlockRemover with a reference to a game and to a counter of blocks.
     *
     * @param game game from which the block should be removed.
     * @param remainingBlocks counter of blocks in the game.
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Checks if the given block that is hit has reached 0 hit points. If so removes it
     * from the game and removes this listener from the block that is being removed.
     *
     * @param beingHit the block to be removed.
     * @param hitter the ball that hit the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            remainingBlocks.decrease(1);
        }
    }
}