package game;

import animations.AnimationRunner;
import animations.EndScreen;
import animations.GameLevel;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import basic.Counter;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import levels.LevelSet;
import sprites.LevelIndicator;
import sprites.LivesIndicator;
import sprites.ScoreIndicator;

import java.io.File;
import java.io.IOException;

/**
 * Game task is a task that runs the game.
 *
 * @param <T> return type of run method.
 */
public class GameTask<T> implements Task<T> {
    private LevelSet set;
    private LivesIndicator livesInd;
    private ScoreIndicator scoreInd;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter scoreCounter;
    private Counter livesCounter;
    private int startLivesNum;
    private HighScoresTable highScoresTable;
    private File highScoreFile;
    private GUI gui;

    /**
     * Constructor: creates a new game task from given level set, animation runner, etc.
     *
     * @param set set of levels.
     * @param ar animation runner.
     * @param ks keyboard sensor.
     * @param startLivesNum initial lives number for the game.
     * @param highScoresTable table of high scores.
     * @param highScoreFile file of high scores.
     * @param gui graphical user interface.
     */
    public GameTask(LevelSet set, AnimationRunner ar, KeyboardSensor ks, int startLivesNum,
                    HighScoresTable highScoresTable, File highScoreFile, GUI gui) {
        this.set = set;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.startLivesNum = startLivesNum;
        this.highScoresTable = highScoresTable;
        this.highScoreFile = highScoreFile;
        this.gui = gui;
    }

    /**
     * Creates and initializes the lives and score indicators.
     */
    private void initialize() {

        // create lives indicator:
        Rectangle livesDisplay = new Rectangle(new Point(0, 0), 250, 30);
        this.livesCounter = new Counter(startLivesNum);
        this.livesInd = new LivesIndicator(livesDisplay, livesCounter);

        // create score indicator:
        Rectangle scoreDisplay = new Rectangle(new Point(250, 0), 250, 30);
        this.scoreCounter = new Counter(0);
        this.scoreInd = new ScoreIndicator(scoreDisplay, scoreCounter);
    }

    /**
     * Run the task- call a method to initialize indicators, run the levels and finish the game.
     *
     * @return null.
     */
    @Override
    public T run() {
        this.initialize();

        for (LevelInformation levelInfo : this.set.getSet()) {
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor,
                    this.animationRunner, this.scoreCounter, this.livesCounter);

            level.initialize();
            scoreInd.addToGame(level);
            livesInd.addToGame(level);

            // create level indicator:
            Rectangle levelDisplay = new Rectangle(new Point(485, 0), 315, 30);
            LevelIndicator levelInd = new LevelIndicator(levelDisplay, levelInfo.levelName());
            levelInd.addToGame(level);

            // play while level has more blocks and player has more lives:
            while ((level.getBlockCounter().getValue() > 0) && (level.getLivesCounter().getValue() > 0)) {
                level.playOneTurn();
            }

            // no more lives:
            if (level.getLivesCounter().getValue() == 0) {
                break;
            }
        }

        // check if to add player to highScoresTable:
        int score = scoreCounter.getValue();
        if (highScoresTable.getRank(score) <= highScoresTable.getMaxSize()) {
            highScoresTable.add(new ScoreInfo(getPlayersName(), score));
        }

        // display end screen:
        endGame();
        animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor, "space",
                new HighScoresAnimation(highScoresTable)));

        // save high scores table in file:
        try {
            highScoresTable.save(highScoreFile);
        } catch (IOException e) {
            System.err.println("failed saving score");
        }
        return null;
    }

    /**
     * Open a dialog manager to get the name of the player.
     *
     * @return string of the players name.
     */
    private String getPlayersName() {
        DialogManager dialog = gui.getDialogManager();
        return dialog.showQuestionDialog("Enter Name", "What is your name?", "");
    }

    /**
     * display end screen according to the game result.
     */
    private void endGame() {
        if (livesCounter.getValue() == 0) {
            this.animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor,
                    "space", new EndScreen(scoreCounter, false)));
        } else {
            this.animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor,
                    "space", new EndScreen(scoreCounter, true)));
        }
    }
}
