package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import animations.Menu;
import animations.MenuAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelSet;

import java.io.File;

import java.util.List;

/**
 * GameFlow is in charge of the flow of the game.
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private biuoop.KeyboardSensor keyboardSensor;
    private int startLivesNum;
    private GUI gui;
    private HighScoresTable highScoresTable;
    private List<LevelSet> levelSetsList;
    private File highScoreFile;

    /**
     * Constructor: creates a new GameFlow with given animation runner, keyboard sensor, gui and number of lives, etc.
     *
     * @param ar animation runner.
     * @param ks keyboard sensor.
     * @param livesNum number of lives for the game.
     * @param gui graphic interface for the game.
     * @param table the table.
     * @param levelSetsList list of level sets.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, int livesNum, GUI gui,
                    HighScoresTable table, List<LevelSet> levelSetsList) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.startLivesNum = livesNum;
        this.gui = gui;
        this.highScoresTable = table;
        this.levelSetsList = levelSetsList;
    }

    /**
     * Start the flow of the game by calling a method to create the menu and running the loop of the game.
     *
     * @param scoreFile the high score file
     */
    public void startFlow(File scoreFile) {

        this.highScoreFile = scoreFile;

        // create menu:
        Menu<Task<Void>> menu = makeMenu();

        // run game:
        while (true) {
            animationRunner.run(menu);

            // wait for user selection
            Task<Void> task = menu.getStatus();

            // run the task of the user selection:
            task.run();
        }
    }

    /**
     * Creates a menu for the game, and tasks for each selection option of the menu.
     *
     * @return menu for the game.
     */
    private Menu<Task<Void>> makeMenu() {
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(this.keyboardSensor,
                this.animationRunner, "MENU");
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(keyboardSensor,
                this.animationRunner, "LEVEL SETS");
        LevelSet set;

        // make sub menu:
        for (int i = 0; i < this.levelSetsList.size(); i++) {
            set = levelSetsList.get(i);

            // make task for selection:
            Task<Void> gameTask = new GameTask<Void>(set, this.animationRunner,
                    this.keyboardSensor, this.startLivesNum, this.highScoresTable,
                    this.highScoreFile, this.gui);
            subMenu.addSelection(set.getKey(), "(" + set.getKey() + ") " + set.getName(), gameTask);
        }
        menu.addSubMenu("s", "(s) Start Game", subMenu);
        Task<Void> showHighScoreTask = new Task<Void>() {
            @Override
            public Void run() {
                Animation highScoreAnimation = new HighScoresAnimation(highScoresTable);
                animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor, "space", highScoreAnimation));
                return null;
            }
        };
        menu.addSelection("h", "(h) High Scores", showHighScoreTask);
        Task<Void> exitTask = new Task<Void>() {
            @Override
            public Void run() {
                gui.close();
                System.exit(0);
                return null;
            }
        };
        menu.addSelection("q", "(q) Quit", exitTask);

        return menu;
    }
}