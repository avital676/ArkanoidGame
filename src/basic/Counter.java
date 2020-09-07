package basic;

/**
 * Counter is a class for counting things.
 */
public class Counter {
    private int count;

    /**
     * Constructor: creates a new counter and initializes it to the given num.
     *
     * @param num the number to initialize the counter to.
     */
    public Counter(int num) {
       this.count = num;
    }

    /**
     * Adds the given number to the current count.
     *
     * @param number the number to be added.
     */
    public void increase(int number) {
        count = count + number;
    }

    /**
     * Subtracts the given number from the current count.
     *
     * @param number the number to be subtracted.
     */
    public void decrease(int number) {
        count = count - number;
    }

    /**
     * Gets the current count.
     *
     * @return current count.
     */
    public int getValue() {
        return this.count;
    }
}