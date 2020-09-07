package animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import basic.Counter;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * The countdown animation displays a given gameScreen for a given numOfSeconds seconds, and shows a countdown from
 * countFrom to 1 on top of the gameScreen. Each number of the countdown would appear for numOfSeconds / countFrom)
 * seconds before being replaced with the next number.
 */
public class CountdownAnimation implements Animation {
    private SpriteCollection gameScreen;
    private boolean stop = false;
    private long millisecondsPerNumber;
    private Counter counter;
    private int flag;

    /**
     * Constructor: creates a new CountDownAnimation with given number
     * of seconds, number to count from and game screen.
     *
     * @param numOfSeconds number of seconds for the animation.
     * @param countFrom number to count down from.
     * @param gameScreen collection of sprites to show.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.gameScreen = gameScreen;
        this.millisecondsPerNumber = (long) ((numOfSeconds / (countFrom + 1)) * 1000);
        this.counter = new Counter(countFrom);
        this.stop = false;

        // flag indicates if the animation is running for the first time or not
        this.flag = 0;
    }

    /**
     * Draws one frame of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        if (counter.getValue() < 0) {
            this.stop = true;
        }
        gameScreen.drawAllOn(d);
        d.setColor(Color.WHITE);
        if (counter.getValue() == 0) {
            d.drawText(d.getWidth() / 2 - 60, d.getHeight() / 2 + 40, "GO!", 80);
        } else {
            d.drawText(d.getWidth() / 2 - 20, d.getHeight() / 2 + 40, Integer.toString(counter.getValue()), 80);
        }

        // timing:
        long startTime = System.currentTimeMillis();
        Sleeper sleeper = new Sleeper();
        long usedTime = System.currentTimeMillis() - startTime;
        long milliSecondLeftToSleep = millisecondsPerNumber - usedTime;

        // sleep if there's still time left and if its not the first run
        // (to prevent delay in uploading the game screen):
        if ((milliSecondLeftToSleep > 0) && (flag != 0)) {
            sleeper.sleepFor(milliSecondLeftToSleep);
        }

        // change flag to indicate that this isn't the first frame of the animation:
        flag = 1;
        counter.decrease(1);
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