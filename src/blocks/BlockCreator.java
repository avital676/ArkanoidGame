package blocks;

/**
 * Interface implemented in classes that create blocks at a certain (x,y) position.
 */
public interface BlockCreator {

    /**
     * Create a block at the specified location.
     *
     * @param xpos x position of the block.
     * @param ypos y position of the block.
     * @return new Block.
     */
    Block create(int xpos, int ypos);

    /**
     * returns the blocks width.
     *
     * @return width.
     */
    int getWidth();
}