package basic;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Color parser is a class for getting a color out of a string.
 */
public class ColorsParser {

    /**
     * Parses a color definition given by a string to java.awt.Color.
     *
     * @param s string definition of a color.
     * @return color.
     */
    public static java.awt.Color colorFromString(String s) {
        String colorStr = null;
        if (s.contains("RGB")) {

            // color is represented by "color(RGB(int,int,int)"
            List<Integer> rgb = new ArrayList<>();
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(s);
            while (m.find()) {
                rgb.add(Integer.parseInt(m.group()));
            }
            return new Color(rgb.get(0), rgb.get(1), rgb.get(2));
        } else {

            // color is represented by a string name of color: "color(colorname)"
            colorStr = s.split("\\(")[1].split("\\)")[0];
            try {
                Field field = Color.class.getField(colorStr);
                return (Color) field.get(null);
            } catch (Exception e) {
                System.out.println("ColorParser problem last lines");
                return null;
            }
        }
    }
}