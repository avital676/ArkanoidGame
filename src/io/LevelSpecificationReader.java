package io;

import biuoop.DrawSurface;
import basic.ColorsParser;
import blocks.BlocksFromSymbolsFactory;
import levels.Level;
import levels.LevelInformation;
import blocks.Block;
import sprites.Sprite;
import sprites.Velocity;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads level specification from a file.
 */
public class LevelSpecificationReader {

    /**
     * Returns a list of levels information by reading it from a given file.
     *
     * @param reader reads the file.
     * @return list of level information.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levelList = new ArrayList<LevelInformation>();
        List<String> levelInfoByStr = new ArrayList<String>();
        BufferedReader buffReader = new BufferedReader(reader);
        String line;
        LevelInformation level;
        try {
            while ((line = buffReader.readLine()) != null) {
                line = line.trim();
                if (line.equals("START_LEVEL")) {
                    levelInfoByStr.clear();
                }
                if ((!line.startsWith("#")) && (line.length() != 0)) {
                    levelInfoByStr.add(line.trim());
                }
                if ((line.equals("END_LEVEL")) && (!levelInfoByStr.isEmpty())) {
                    level = createLevel(levelInfoByStr);
                    if (level != null) {
                        levelList.add(level);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                reader.close();
                buffReader.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return levelList;
    }

    /**
     * Creates a level from data saved in a given string list.
     * Throws a runtime exception in case there are missing fields.
     *
     * @param levelByStr levels data.
     * @return new level information from levelByStr.
     * @throws IOException if there's a problem reading the file of block specification.
     */
    private LevelInformation createLevel(List<String> levelByStr) throws IOException {
        String levelName = null;
        List<Velocity> ballVelocities = null;
        Sprite background = null;
        Integer paddleSpeed = null;
        Integer paddleWidth = null;
        Integer blockStartX = null;
        Integer blockStartY = null;
        Integer rowHeight = null;
        Integer blockNum = null;
        List<Block> blockList;
        BlocksFromSymbolsFactory factory = null;
        List<String> layout = new ArrayList<>();
        int indexOfStartBlocks = -1;
        int indexOfEndBlocks = -1;

        String s;
        for (int i = 0; i < levelByStr.size(); i++) {
            s = levelByStr.get(i);
            if (s.startsWith("level_name")) {
                levelName = s.split(":")[1];
            } else if (s.startsWith("ball_velocities")) {
                ballVelocities = makeVelocityList(s.split(":")[1]);
            } else if (s.startsWith("background")) {
                background = makeBackground(s.split(":")[1]);
            } else if (s.startsWith("paddle_speed")) {
                paddleSpeed = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("paddle_width")) {
                paddleWidth = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("block_definitions")) {
                Reader reader = new FileReader("resources/" + s.split(":")[1].trim());
                factory = BlockDefinitionReader.fromReader(reader);
            } else if (s.startsWith("blocks_start_x")) {
                blockStartX = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("blocks_start_y")) {
                blockStartY = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("row_height")) {
                rowHeight = Integer.parseInt(s.split(":")[1]);
            } else if (s.startsWith("num_blocks")) {
                blockNum = Integer.parseInt(s.split(":")[1]);
            } else if (s.equals("START_BLOCKS")) {
                indexOfStartBlocks = i;
            } else if (s.equals("END_BLOCKS")) {
                indexOfEndBlocks = i;
            }
        }

        // build level layout:
        if ((indexOfStartBlocks != -1) && (indexOfEndBlocks != -1)) {
            for (int i = indexOfStartBlocks + 1; i < indexOfEndBlocks; i++) {
                layout.add(levelByStr.get(i));
            }
        }

        // create block list:
        if ((blockStartX != null) && (blockStartY != null) && (rowHeight != null) && (!layout.isEmpty())) {
            blockList = createBlockList(factory, blockStartX, blockStartY, rowHeight, layout);
        } else {
            return null;
        }

        if ((levelName != null) && (ballVelocities != null) && (background != null) && (paddleSpeed != null)
                && (paddleWidth != null) && (!blockList.isEmpty()) && (blockNum != null)) {
            return new Level(levelName, ballVelocities, paddleSpeed, paddleWidth, blockList, blockNum, background);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Creates a list of blocks for a level.
     *
     * @param factory blocks factory.
     * @param startX x position of the start of the blocks.
     * @param startY y position of the start of the blocks.
     * @param rowHeight height of each block row.
     * @param layout layout of blocks.
     * @return list of blocks.
     */
    private List<Block> createBlockList(BlocksFromSymbolsFactory factory,
                                        int startX, int startY, int rowHeight, List<String> layout) {
        List<Block> blocks = new ArrayList<>();
        if ((layout != null) && (factory != null)) {
            String symbol;
            int x = startX;
            int y = startY;
            for (String row : layout) {

                // create row of blocks:
                for (int i = 0; i < row.length(); i++) {
                    symbol = Character.toString(row.charAt(i));
                    if (factory.isSpaceSymbol(symbol)) {
                        x += factory.getSpaceWidth(symbol);
                    } else if (factory.isBlockSymbol(symbol)) {
                        blocks.add(factory.getBlock(symbol, x, y));
                        x += factory.getBlockCreator(symbol).getWidth();
                    }
                }

                // end of row.
                x = startX;
                y += rowHeight;
            }
        }
        return blocks;
    }

    /**
     * Creates a background for the level from data in a given string.
     *
     * @param s background data.
     * @return background of the level.
     */
    private Sprite makeBackground(String s) {
        if (s.startsWith("color")) {
            Color color = ColorsParser.colorFromString(s);
            return new Sprite() {

                /**
                 * Draws the sprite on a given surface.
                 *
                 * @param d surface to draw the sprite on.
                 */
                @Override
                public void drawOn(DrawSurface d) {
                    d.setColor(color);
                    d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                }

                /**
                 * notify the sprite that time has passed.
                 */
                @Override
                public void timePassed() {

                }
            };
        } else if (s.startsWith("image")) {
            String imgString = s.split("\\(")[1].split("\\)")[0];
            Image img;
            InputStream is = null;
            try {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(imgString);
                img = ImageIO.read(is);

                return new Sprite() {

                    /**
                     * Draws the sprite on a given surface.
                     *
                     * @param d surface to draw the sprite on.
                     */
                    @Override
                    public void drawOn(DrawSurface d) {
                        d.drawImage(0, (int) 0, img);
                    }

                    /**
                     * notify the sprite that time has passed.
                     */
                    @Override
                    public void timePassed() {

                    }
                };

            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        // none of the options above:
        return null;
    }

    /**
     * Creates a velocities list from data in a ggiven string.
     *
     * @param velocities velocities data.
     * @return list of velocities.
     */
    private List<Velocity> makeVelocityList(String velocities) {
        List<Velocity> vList = new ArrayList<Velocity>();
        double angle;
        double speed;
        String[] angleSpeedList = velocities.split(" ");
        for (String s : angleSpeedList) {
            angle = Double.parseDouble(s.split(",")[0].trim());
            speed = Double.parseDouble(s.split(",")[1].trim());
            vList.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
        return vList;
    }
}
