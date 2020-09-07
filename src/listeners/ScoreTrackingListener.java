package listeners;

import basic.Counter;
import sprites.Ball;
import blocks.Block;

/**
 * ScoreTrackingListener is a hit listener that updates the score by updating its counter member.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor: creates a ScoreTrackingListener with a given counter.
     *
     * @param scoreCounter counter of the score tracking listener.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Adds 5 points to the counter for every hit (every call of this method),
     * and if the hit block is removed adds another 10 point.
     *
     * @param beingHit the block that is being hit.
     * @param hitter the ball that is doing the hitting.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
        if (beingHit.getHitPoints() == 0) {
            currentScore.increase(10);
        }
    }
}