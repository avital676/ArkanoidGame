package levels;

import java.util.List;

/**
 * Set of levels, has a key, message and list of levels information.
 */
public class LevelSet {
    private String key;
    private String name;
    private List<LevelInformation> set;

    /**
     * Constructor: creates a new level set.
     *
     * @param key key of the level set.
     * @param name name of the level set.
     * @param set list of levels in the set.
     */
    public LevelSet(String key, String name, List<LevelInformation> set) {
        this.key = key;
        this.name = name;
        this.set = set;
    }

    /**
     * Gets the key of the level set.
     *
     * @return key of level set.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Gets the name of the level set.
     *
     * @return name of level set.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets list of level information of the levels in the set.
     *
     * @return list of levels information of the set.
     */
    public List<LevelInformation> getSet() {
        return this.set;
    }
}