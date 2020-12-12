package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Wall extends Prop{
    public Wall(int x, int y, Floor floor) {
        super(ID.Wall, false, x, y,50,50, "It's a wall", Main.WALL_SRC, floor);
        this.setImageID(Main.WALL_SRC);

    }
}
