package listeners;

import sprites.Ball;
import blocks.Block;

/**
 * The HitListener interface is implemented in classes that should be notified whenever a hit occurs.
 * It has a hitEvent method for what to do in case of a hit.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block the is being hit.
     * @param hitter the ball that is doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}