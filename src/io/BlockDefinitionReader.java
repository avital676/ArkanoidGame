package io;

import blocks.BlockCreator;
import blocks.BlockObjectCreator;
import basic.ColorsParser;
import blocks.BlocksFromSymbolsFactory;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Block definition reader is a class that reads definitions for blocks from a given file, and returns a blocks factory.
 */
public class BlockDefinitionReader {

    /**
     * Creates a BlocksFromSymbolsFactory from a given reader.
     * Throws a runtime exception in case there are missing or invalid fields.
     *
     * @param reader reader to read the block definitions file.
     * @return blocks from symbols factory.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
        Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();
        String[] blockPropertiesArr;
        String[] defaultPropertiesArr = null;
        String blockSymbol;
        BlockCreator creator;
        String[] spacerPropertiesArr;
        String spacerSymbol;
        int spacerW;
        BufferedReader buffReader = new BufferedReader(reader);
        String line;
        try {
            while ((line = buffReader.readLine()) != null) {
                line = line.trim();
                if ((line.startsWith("#")) || (line.length() == 0)) {
                    continue;
                }
                if (line.startsWith("default")) {
                    defaultPropertiesArr = line.split(" ");
                } else if (line.startsWith("bdef")) {
                    blockPropertiesArr = line.split(" ");
                    blockSymbol = blockPropertiesArr[1].split(":")[1];
                    if (blockSymbol.length() > 1) {
                        throw new RuntimeException();
                    }
                    creator = createBlockCreator(blockPropertiesArr, defaultPropertiesArr);
                    if (creator != null) {
                        blockCreators.put(blockSymbol, creator);
                    }
                } else if (line.startsWith("sdef")) {
                    spacerPropertiesArr = line.split(" ");
                    spacerSymbol = spacerPropertiesArr[1].split(":")[1];
                    if (spacerSymbol.length() > 1) {
                        throw new RuntimeException();
                    }
                    spacerW = Integer.parseInt(spacerPropertiesArr[2].split(":")[1]);
                    spacerWidths.put(spacerSymbol, spacerW);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("error in BlockDefinitionReader class, fromReader method");
        } finally {
            try {
                reader.close();
                buffReader.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * Creates a BlockCreator with given properties from the file.
     * Throws a runtime exception in case there are missing or invalid fields.
     *
     * @param blockProperties properties of the block.
     * @param defaultProperties default properties for all blocks.
     * @return new block creator or null if fields are missing.
     */
    private static BlockCreator createBlockCreator(String[] blockProperties, String[] defaultProperties) {
        int height = 0;
        int width = 0;
        int hitPoints = 0;
        Map<Integer, String> fillsMap = new HashMap<>();
        Color stroke = null;
        String[] temp;

        // check for default properties:
        if (defaultProperties != null) {
            for (String s: defaultProperties) {
                if (s.startsWith("height")) {
                    height = Integer.parseInt(s.split(":")[1]);
                } else if (s.startsWith("width")) {
                    width = Integer.parseInt(s.split(":")[1]);
                } else if (s.startsWith("hit_points")) {
                    hitPoints = Integer.parseInt(s.split(":")[1]);
                } else if (s.startsWith("fill")) {
                    temp = s.split("-");

                    // only one fill for the block:
                    if (temp.length < 2) {
                        fillsMap.put(1, s.split(":")[1]);
                    } else {

                        // more than one fill for the block, save fill and fill number in map:
                        String indexOfFill = s.split("-")[1].split(":")[0];
                        fillsMap.put(Integer.parseInt(indexOfFill), s.split(":")[1]);
                    }
                } else if (s.startsWith("stroke")) {
                    stroke = ColorsParser.colorFromString(s.split(":")[1]);
                }
            }
        }

        // check for block properties:
        for (String s: blockProperties) {
            if (s.startsWith("height")) {
                height = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("width")) {
                width = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("hit_points")) {
                hitPoints = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("fill")) {
                temp = s.split("-");

                // only one fill for the block:
                if (temp.length < 2) {
                    fillsMap.put(1, s.split(":")[1]);
                } else {

                    // more than one fill for the block, save fill and fill number in map:
                    String indexOfFill = s.split("-")[1].split(":")[0];
                    fillsMap.put(Integer.parseInt(indexOfFill), s.split(":")[1]);
                }
            } else if (s.startsWith("stroke")) {
                stroke = ColorsParser.colorFromString(s.split(":")[1]);
            }
        }

        // check all fields exist and return a block creator:
        if ((height > 0) && (width > 0) && (hitPoints > 0) && (!fillsMap.isEmpty())) {
            return new BlockObjectCreator(height, width, hitPoints, fillsMap, stroke);
        } else {
            throw new RuntimeException();
        }
    }
}