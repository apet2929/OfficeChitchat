package sample;

import java.util.HashMap;
import java.util.Map;

public class LevelBuilder implements java.io.Serializable{
    public String name;
    private static final long serialVersionUID = 2L;
    Map<String, int[]> objects = new HashMap<>();
    public LevelBuilder(String name, int[] walls, int[] people, int[] playerSpawn) {
        this.name = name;
        objects.put("Walls", walls);
        objects.put("People", people);
        objects.put("Player", playerSpawn);

    }

}
