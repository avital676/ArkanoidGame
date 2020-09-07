package game;

import java.io.Serializable;

/**
 * Score information, has a name and a score.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * Constructor: creates a new score info.
     *
     * @param name name for the score info.
     * @param score score for the score info.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the name of the score info.
     *
     * @return name of score info.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the score of the score info.
     *
     * @return score of score info.
     */
    public int getScore() {
        return this.score;
    }
}