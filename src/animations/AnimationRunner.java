package animations;

import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * Animation Runner is a class for running animations. It has a GUI,
 * and runs animations with a certain number of frames per second.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;

    /**
     * Constructor: creates a new AnimationRunner with given gui, number of frames per second and a sleeper.
     *
     * @param gui graphical user interface.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
        this.sleeper = new biuoop.Sleeper();
    }

    /**
     * Runs a given animation by setting timing and calling a method to show frames of the animation.
     *
     * @param animation the animation to be run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (!animation.shouldStop()) {

            // timing:
            long startTime = System.currentTimeMillis();

            // show animation:
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);

            // sleep:
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}