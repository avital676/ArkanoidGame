package levels;

import io.LevelSpecificationReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates level sets for the game.
 */
public class LevelSetsCreator {

    /**
     * Creates a list of level sets from a file specified in args (command line), or from a default file.
     *
     * @param args string of file name to read level sets from or empty string.
     * @return list of level sets.
     */
    public static List<LevelSet> getSetsFromArgs(String[] args) {
        List<LevelSet> levelSetsList;
        String path;
        InputStream is = null;
        BufferedReader reader = null;
        try {
            if (args.length != 0) {
                path = args[0];
            } else {
                path = "level_sets.txt";
            }
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            reader = new BufferedReader(new InputStreamReader(is));
            levelSetsList = new LevelSetsCreator().makeSetsList(reader);
            return levelSetsList;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            try {
                reader.close();
                is.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Reads the file f level sets and creates the sets.
     *
     * @param reader reader to read from level specifications file.
     * @return list of level sets or null if an exception was thrown while trying to read from the file.
     */
    private List<LevelSet> makeSetsList(Reader reader) {
        List<LevelSet> setsList = new ArrayList<>();
        LineNumberReader lineReader = new LineNumberReader(reader);
        LevelSpecificationReader levelSpecification = new LevelSpecificationReader();
        BufferedReader levelReader = null;
        InputStream is = null;
        String key = null;
        String name = null;
        List<LevelInformation> levelsList;
        try {
            String line;
            while ((line = lineReader.readLine()) != null) {
                line = line.trim();
                if ((line.startsWith("#")) || (line.length() == 0)) {
                    continue;
                }

                // odd-numbered lines:
                if (lineReader.getLineNumber() % 2 != 0) {
                    key = line.split(":")[0];
                    name = line.split(":")[1];
                } else {

                    // even-numbered lines:
                    is = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
                    levelReader = new BufferedReader(new InputStreamReader(is));
                    levelsList = levelSpecification.fromReader(levelReader);
                    setsList.add(new LevelSet(key, name, levelsList));
                }
            }
            return setsList;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("error in makeSetsList");
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}