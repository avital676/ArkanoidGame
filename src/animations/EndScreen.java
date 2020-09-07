package animations;

import biuoop.DrawSurface;
import basic.Counter;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 * End Screen is an animation displayed when the game ends. It has different
 * behaviours depending on the game result (winning or loosing).
 */
public class EndScreen implements Animation {
    private boolean stop;
    private Counter score;
    private boolean userWon;
    private Image winImg;
    private Image loseImg;

    /**
     * Constructor: creates a new EndScreen with a given score counter
     * and a boolean value indicating whether the user won or failed.
     *
     * @param score counter of the game score.
     * @param userWon boolean value indication if the user won the game (true) or not (false)
     */
    public EndScreen(Counter score, boolean userWon) {
        this.score = score;
        this.userWon = userWon;
        this.stop = false;
        try {
            this.winImg = ImageIO.read(new File("resources/game_images/winner.jpg"));
        } catch (IOException e) {
            this.winImg = null;
        }
        try {
            this.loseImg = ImageIO.read(new File("resources/game_images/loser.jpg"));
        } catch (IOException e) {
            this.loseImg = null;
        }
    }

    /**
     * Draws one frame of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {

        // the user won:
        if (userWon) {
            if (this.winImg != null) {
                d.drawImage(0, 0, this.winImg);
            } else {
                d.setColor(Color.BLACK);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            }
            d.setColor(Color.decode("#FFE271"));
            d.drawText(184, 139, "YOU WIN!", 100);
            d.setColor(Color.decode("#FFD114"));
            d.drawText(185, 140, "YOU WIN!", 100);
            d.setColor(Color.decode("#FFE271"));
            d.drawText(139, 324, "Your score is " + score.getValue(), 70);
            d.setColor(Color.decode("#FFD114"));
            d.drawText(140, 325, "Your score is " + score.getValue(), 70);
        } else {

            // the user lost:
            if (this.loseImg != null) {
                d.drawImage(0, 0, this.loseImg);
            } else {
                d.setColor(Color.lightGray);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            }
            d.setColor(Color.decode("#178735"));
            d.drawText(d.getWidth() / 2 - 250, 120, "GAME OVER...", 80);
            d.drawText(d.getWidth() / 2 - 190, 500, "Your score is " + score.getValue() + ".", 50);
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