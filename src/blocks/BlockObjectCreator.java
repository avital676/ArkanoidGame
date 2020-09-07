package blocks;

import geometry.Point;

import java.util.Map;

/**
 * Creates a Block object.
 */
public class BlockObjectCreator implements BlockCreator {
    private int height;
    private int width;
    private int hitPoints;
    private Map<Integer, String> fillsMap;
    private java.awt.Color stroke;

    /**
     * Constructor: creates a new Block object creator.
     *
     * @param height height of the block.
     * @param width width of the block.
     * @param hitPoints number of allowed hits for the block.
     * @param fillsMap map of fillings for the block.
     * @param stroke stroke of the block.
     */
    public BlockObjectCreator(int height, int width, int hitPoints,
                              Map<Integer, String> fillsMap, java.awt.Color stroke) {
        this.height = height;
        this.width = width;
        this.hitPoints = hitPoints;
        this.fillsMap = fillsMap;
        this.stroke = stroke;
    }

    /**
     * Create a block at the specified location.
     *
     * @param xpos x position of the block.
     * @param ypos y position of the block.
     * @return new Block.
     */
    @Override
    public Block create(int xpos, int ypos) {
        Point location = new Point(xpos, ypos);
        return new Block(location, height, width, hitPoints, fillsMap, stroke);
    }

    /**
     * returns the blocks width.
     *
     * @return width.
     */
    @Override
    public int getWidth() {
        return this.width;
    }
}
