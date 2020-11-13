package sample;

import javafx.scene.image.ImageView;

public class BasicPerson extends Prop{
    public BasicPerson( ImageView sprite, int x, int y, int width, int height) {
        super(ID.Person, true, sprite, x, y, width, height, "Your coworker and co-sufferer");
    }
}
