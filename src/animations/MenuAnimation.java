package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Animation of a menu for the game. Has methods to add selections, add sub menus, draw frame of the animation, etc.
 *
 * @param <T> return value of the options in the menu.
 */
public class MenuAnimation<T> implements Menu<T> {
    private ArrayList<String> keyList;
    private ArrayList<String> messageList;
    private ArrayList<T> returnValList;
    private KeyboardSensor keyboard;
    private boolean stop;
    private T status;
    private List<Menu<T>> subMenus;
    private List<Boolean> isTask;
    private AnimationRunner ar;
    private String name;
    private Image img;

    /**
     * Constructor: creates a new menu animation with given keyboard sensor, animation runner and the name of the menu.
     *
     * @param keyboard keyboard sensor.
     * @param ar animation runner.
     * @param menuName name of the menu.
     */
    public MenuAnimation(KeyboardSensor keyboard, AnimationRunner ar, String menuName) {
        this.keyList = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.returnValList = new ArrayList<>();
        this.keyboard = keyboard;
        this.stop = false;
        this.subMenus = new ArrayList<>();
        this.isTask = new ArrayList<>();
        this.ar = ar;
        this.name = menuName;
        try {
            this.img = ImageIO.read(new File("resources/game_images/blup.jpg"));
        } catch (IOException e) {
            this.img = null;
        }
    }

    /**
     * Adds an option that has given message, return value and key to the menu.
     *
     * @param key key to wait for.
     * @param message line to print.
     * @param returnVal what to return when receiving the key.
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.returnValList.add(returnVal);
        this.isTask.add(true);
        this.subMenus.add(null);
    }

    /**
     * Gets the return value of the selected option from the menu.
     *
     * @return value of the selected option.
     */
    @Override
    public T getStatus() {
        this.stop = false;
        return this.status;
    }

    /**
     * Adds a sub menu to the menu.
     *
     * @param key key to wait for.
     * @param message line to print.
     * @param subMenu the sub menu to add.
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.subMenus.add(subMenu);
        this.returnValList.add(null);
        this.isTask.add(false);
    }

    /**
     * Draws one frame of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {

        // draw background:
        if (this.img != null) {
            d.drawImage(0, 0, img);
        } else {
            d.setColor(Color.BLUE);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }

        // write:
        d.setColor(Color.BLUE);
        d.drawText(149, 159, this.name, 70);
        d.setColor(Color.WHITE);
        d.drawText(150, 160, this.name, 70);

        // write messages:
        int height = 240;
        for (String message : this.messageList) {
            d.setColor(Color.BLUE);
            d.drawText(199, height - 1, message, 30);
            d.setColor(Color.WHITE);
            d.drawText(200, height, message, 30);
            height = height + 50;
        }

        // check if to change status:
        String key;
        for (int i = 0; i < this.keyList.size(); i++) {
            key = this.keyList.get(i);
            if (this.keyboard.isPressed(key)) {
                if (this.isTask.get(i)) {
                    this.status = this.returnValList.get(this.keyList.indexOf(key));
                    this.stop = true;
                    break;
                } else {
                    this.ar.run(this.subMenus.get(i));
                    this.status = this.subMenus.get(i).getStatus();
                    this.stop = true;
                    break;
                }

            }
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
