package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Key Press Stoppable Animation is a decorator class that wraps an existing
 * animation and adds a "waiting-for-key" behavior to it.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
   private boolean isAlreadyPressed;

    /**
     * Constructor: creates a new key press stoppable animation with a given sensor, key and animation.
     *
     * @param sensor keyboard sensor.
     * @param key the keyboard key that should stop the animation.
     * @param animation the animation to be stopped by pressing the key.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor =  sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * Draws one frame of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        if (this.sensor.isPressed(this.key)) {
            if (!isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            isAlreadyPressed = false;
        }
    }

    /**
     * Returns a boolean value indicating of the animation should stop or not.
     *
     * @return true if the animation should stop, false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}