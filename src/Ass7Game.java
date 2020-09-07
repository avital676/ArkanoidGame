import biuoop.GUI;
import animations.AnimationRunner;
import game.GameFlow;
import game.HighScoresTable;
import levels.LevelSet;
import levels.LevelSetsCreator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Ass7Game class has a main method to run the game.
 */
public class Ass7Game {

    /**
     * Creates a new game flow and a levels list and runs the levels.
     *
     * @param args command line parameters, may contain a file name of level definitions for the game.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800, 600);

        // load high scores file and save it:
        File highScoresFile = new File("highscores");
        HighScoresTable table = new HighScoresTable(5);
        table = HighScoresTable.loadFromFile(highScoresFile);
        try {
            table.save(highScoresFile);
        } catch (IOException e) {
            table = new HighScoresTable(5);
        }

        // create level sets list from args:
        List<LevelSet> levelSetsList = LevelSetsCreator.getSetsFromArgs(args);
        if (levelSetsList == null) {
            throw new RuntimeException();
        }

        // create a game flow and start:
        GameFlow gameFlow = new GameFlow(new AnimationRunner(gui), gui.getKeyboardSensor(),
                7, gui, table, levelSetsList);
        gameFlow.startFlow(highScoresFile);
    }
}