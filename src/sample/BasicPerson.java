package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicPerson extends Prop{

    public BasicPerson( int x, int y, int width, int height, Floor floor) {
        super(ID.Person, true, x, y, width, height, "Your coworker and co-sufferer", Main.PLAYER_UP_SRC, floor);
    }

}
