package blocks;

import biuoop.DrawSurface;
import basic.ColorsParser;
import geometry.Point;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Filler is a class for filling blocks on a drawsurface.
 */
public class Filler {
    private Map<Integer, String> fillsMap;
    private Map<Integer, Image> indexImageMap;

    /**
     * Constructor: creates a new Filler with a given fills map, and calls a method to load the images from the map.
     *
     * @param fillsMap map of integer:string, specifying the filling of a block depending
     *                 on the integer value (which is the number of hits left for the block)
     */
    public Filler(Map<Integer, String> fillsMap) {
        this.fillsMap = fillsMap;
        this.indexImageMap = new HashMap<>();
        loadImages();
    }

    /**
     * Loads the images that are in the fillsMap and saves them in a new map with their index,
     * i.e saves the block appearance in accordance to the number of hits left for the block.
     */
    private void loadImages() {
        InputStream is = null;
        for (int key : fillsMap.keySet()) {
            String s = fillsMap.get(key);
            if (!s.startsWith("color")) {
                String imgString = s.split("\\(")[1].split("\\)")[0];
                try {
                    is = ClassLoader.getSystemClassLoader().getResourceAsStream(imgString);
                    Image img = ImageIO.read(is);
                    indexImageMap.put(key, img);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Fills a block with given location, height and width and a given index on a given surface.
     *
     * @param indexOfFill the key of the wanted fill in the map (integer).
     * @param location upper left point of the block to fill.
     * @param d surface to draw on.
     * @param height height of the block to fill.
     * @param width width of the block to fill.
     */
    public void fillFromString(int indexOfFill, Point location, DrawSurface d, int height, int width) {
        if (indexImageMap.containsKey(indexOfFill)) {
            Image imgToFill = indexImageMap.get(indexOfFill);
            d.drawImage((int) location.getX(), (int) location.getY(), imgToFill);
        } else {
            Color filling = ColorsParser.colorFromString(fillsMap.get(indexOfFill));
            d.setColor(filling);
            int x = (int) location.getX();
            int y = (int) location.getY();
            d.fillRectangle(x, y, width, height);
        }
    }
}
