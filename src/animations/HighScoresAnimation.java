package animations;

import biuoop.DrawSurface;
import game.HighScoresTable;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 * Animation showing a table of the high scores of players.
 */
public class HighScoresAnimation implements Animation {
    private boolean stop;
    private HighScoresTable scores;
    private Image img;

    /**
     * Constructor: creates a new high score animation from a given table.
     *
     * @param scores table of high scores.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.stop = false;
        this.scores = scores;
        try {
            this.img = ImageIO.read(new File("resources/game_images/purple.jpg"));
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
        String name;
        String score;
        int height = 270;
        if (this.img != null) {
            d.drawImage(0, 0, img);
        } else {
            d.setColor(Color.decode("#B266FF"));
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }
        d.setColor(Color.WHITE);
        d.drawText(125, 120, "HIGHEST SCORES", 60);
        d.drawText(140, 200, "Player Name:", 40);
        d.drawText(500, 200, "Score:", 40);
        d.drawLine(140, 220, 610, 220);

        // draw list of names and scores:
        for (int i = 0; i < this.scores.size(); i++) {
            name = this.scores.getHighScores().get(i).getName();
            score = Integer.toString(this.scores.getHighScores().get(i).getScore());
            d.drawText(140, height, name, 25);
            d.drawText(500, height, score, 25);
            height = height + 50;
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
