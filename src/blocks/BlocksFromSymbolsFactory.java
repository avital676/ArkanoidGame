package blocks;

import java.util.Map;

/**
 * Factory of blocks.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor: creates a new factory from given block creators and spacer widths.
     *
     * @param spacerWidths map of symbols representing spacers and widths of the spacers.
     * @param blockCreators map of symbols representing blocks and block creators.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Checks if a given string is a valid space symbol.
     *
     * @param s string to check.
     * @return true if 's' is a valid space symbol, false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * Checks if a given string is a valid block symbol.
     *
     * @param s string to check.
     * @return true if 's' is a valid block symbol, false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     *
     * @param s symbol of the block.
     * @param xpos x position of the block.
     * @param ypos y position of the block.
     * @return new block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s spacer symbol.
     * @return width.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Returns the block creator associated with the given block symbol.
     *
     * @param s block symbol.
     * @return block creator.
     */
    public BlockCreator getBlockCreator(String s) {
        return this.blockCreators.get(s);
    }
}