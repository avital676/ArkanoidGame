package animations;

import biuoop.DrawSurface;

/**
 * Animation interface is implemented in classes of animations that have frames and could be stopped at some point.
 */
public interface Animation {

    /**
     * Draws one frame of the animation.
     *
     * @param d surface to draw on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * Returns a boolean value indicating of the animation should stop or not.
     *
     * @return true if the animation should stop, false otherwise.
     */
    boolean shouldStop();
}
