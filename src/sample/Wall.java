package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends Prop{
    public static final String IMAGE = "wall";
    public Wall(int x, int y) {
        super(ID.Wall, false, x, y,50,50, "It's a wall");
    }
}
