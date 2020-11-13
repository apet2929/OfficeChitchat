package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends Prop{
    public static final String IMAGE = "wall";
    public Wall(ImageView sprite, int x, int y) {
        super(ID.Wall, false, sprite, x, y,50,50, "It's a wall");
        this.getSprite().setLayoutX(x*Main.scale);
        this.getSprite().setLayoutY(y*Main.scale);
    }
}
