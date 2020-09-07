package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Table of high scores of the game.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> scoreInfoList;
    private static int maxSize;

    /**
     * Create an empty high-scores table with a given size.
     *
     * @param size the number of results the table can hold.
     */
    public HighScoresTable(int size) {
        this.scoreInfoList = new ArrayList<ScoreInfo>();
        maxSize = size;
    }

    /**
     * Add a high score to the table in the right place in accordance to the score value.
     *
     * @param score the score to be added.
     */
    public void add(ScoreInfo score) {
        if (maxSize == 0) {
            return;
        }

        // add first score to empty list:
        if (this.scoreInfoList.isEmpty()) {
            this.scoreInfoList.add(score);
            return;
        }

        // list isn't empty:
        boolean scoreAdded = false;
        int rank = getRank(score.getScore());

        // iterate scores in list to find where and whether to add score to the list:
        for (int i = 0; i < this.scoreInfoList.size(); i++) {

            // check if rank of score is smaller than ranks of scores in the table,
            // or if score is equal to a score in the table:
            if ((rank <= i + 1) || (score.getScore() == this.scoreInfoList.get(i).getScore())) {
                if (this.scoreInfoList.size() == maxSize) {

                    // list is full & last score in list is smaller than score to be added, remove it:
                    this.scoreInfoList.remove(maxSize - 1);
                }
                this.scoreInfoList.add(i, score);
                scoreAdded = true;
                break;
            }
        }

        // check if to add score to the end of the list:
        if ((!scoreAdded) && (this.scoreInfoList.size() < maxSize)) {
            this.scoreInfoList.add(this.scoreInfoList.size(), score);
        }
    }

    /**
     * Return table size.
     *
     * @return table size.
     */
    public int size() {
        return this.scoreInfoList.size();
    }

    /**
     * Return the maximum size of the table.
     *
     * @return maximum size of the table.
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Return the current high scores in a sorted list.
     *
     * @return sorted list of high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoreInfoList;
    }

    /**
     * return the rank of the current score.
     * Rank 1: the score will be highest on the list.
     * Rank `size`: the score will be lowest.
     * Rank > `size`: the score is too low and will not be added to the list.
     *
     * @param score the score to find the rank for.
     * @return rank of the score.
     */
    public int getRank(int score) {
        if (this.scoreInfoList.isEmpty()) {
            return 1;
        }

        // compare score to the scores in the table:
        int i;
        for (i = 0; i < size(); ++i) {
            if (score > this.scoreInfoList.get(i).getScore()) {
                return ++i;
            }
        }
        return ++i;
    }

    /**
     * Clear the table.
     */
    private void clear() {
        this.scoreInfoList.clear();
    }

    /**
     * Clear the current table data and load table data from a given file.
     *
     * @param filename file to load data from.
     * @throws IOException throws an IO exception in case there was a problem reading the file.
     */
    private void load(File filename) throws IOException {

        // clear current table:
        this.clear();

        // load new table from file:
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(new FileInputStream(filename));
            this.scoreInfoList = ((HighScoresTable) is.readObject()).getHighScores();
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * Saves the table data to a given file.
     *
     * @param filename file to save the data to.
     * @throws IOException throws an exception in case there was a problem saving to the file.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(new FileOutputStream(filename));
            os.writeObject(this);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * Reads a table from a given file and returns it. In case the file doesn't exist or if
     * there's a problem reading it, it returns an empty table.
     *
     * @param filename file to read data from.
     * @return loaded table or empty table.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable loadedTable = new HighScoresTable(maxSize);
        try {
            loadedTable.load(filename);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return loadedTable;
    }
}