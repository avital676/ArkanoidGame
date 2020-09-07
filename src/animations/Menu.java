package animations;

/**
 * Interface of menu for the game.
 *
 * @param <T> the return value of each option in the menu.
 */
public interface Menu<T> extends Animation {

    /**
     * Adds an option that has given message, return value and key to the menu.
     *
     * @param key key to wait for.
     * @param message line to print.
     * @param returnVal what to return when receiving the key.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Gets the return value of the selected option from the menu.
     *
     * @return value of the selected option.
     */
    T getStatus();

    /**
     * Adds a sub menu to the menu.
     *
     * @param key key to wait for.
     * @param message line to print.
     * @param subMenu the sub menu to add.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}