package game;

/**
 * Task returning generic type.
 *
 * @param <T> generic type.
 */
public interface Task<T> {

    /**
     * Runs the task.
     *
     * @return generic type.
     */
    T run();
}