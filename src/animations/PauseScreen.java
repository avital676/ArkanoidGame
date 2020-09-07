package animations;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 * Pause Screen is an animation displayed when there's a pause in the game.
 */
public class PauseScreen implements Animation {
    private boolean stop;
    private Image img;

    /**
     * Constructor: creates a new Pause Screen and initializes the stopping boolean to false.
     */
    public PauseScreen() {
        this.stop = false;
        try {
            this.img = ImageIO.read(new File("resources/game_images/pause.png"));
        } catch (IOException e) {
            this.img = null;
        }
    }

    /**
     * Draws one frame of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.img != null) {
            d.drawImage(0, 0, img);
        } else {
            d.setColor(Color.decode("#B266FF"));
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

            // draw pause:
            d.setColor(Color.WHITE);
            drawPause(d);

            // draw pause sign:
            d.setColor(Color.WHITE);
            d.fillCircle(400, 420, 80);
            d.setColor(Color.decode("#B266FF"));
            d.fillRectangle(365, 385, 30, 70);
            d.fillRectangle(405, 385, 30, 70);
        }

        d.setColor(Color.WHITE);
        d.drawText(195, 555, "press space to continue", 40);
    }

    /**
     * Draws the word "PAUSE" on a given surface.
     *
     * @param d surface to draw on.
     */
    private void drawPause(DrawSurface d) {

        //P
        d.fillRectangle(100, 100, 100, 25);
        d.fillRectangle(100, 125, 25, 100);
        d.fillRectangle(100, 150, 100, 25);
        d.fillRectangle(175, 100, 25, 50);

        //A
        d.fillRectangle(225, 100, 100, 25);
        d.fillRectangle(225, 125, 25, 100);
        d.fillRectangle(300, 125, 25, 100);
        d.fillRectangle(225, 150, 100, 25);

        //U
        d.fillRectangle(425, 100, 25, 100);
        d.fillRectangle(350, 100, 25, 100);
        d.fillRectangle(350, 200, 100, 25);

        //S
        d.fillRectangle(475, 100, 100, 25);
        d.fillRectangle(475, 200, 100, 25);
        d.fillRectangle(475, 100, 25, 50);
        d.fillRectangle(475, 150, 100, 25);
        d.fillRectangle(550, 150, 25, 50);

        //E
        d.fillRectangle(600, 100, 25, 100);
        d.fillRectangle(600, 100, 100, 25);
        d.fillRectangle(600, 150, 100, 25);
        d.fillRectangle(600, 200, 100, 25);
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