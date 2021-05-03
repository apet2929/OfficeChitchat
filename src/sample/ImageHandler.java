package sample;

import javafx.scene.image.Image;

public class ImageHandler {
    public static final Image[] images = new Image[]{
            new Image(ImageHandler.class.getResource("assets/floor.png").toExternalForm(), 32, 32, true, true), //0
            new Image(ImageHandler.class.getResource("assets/wall.png").toExternalForm(), 32, 32, true, true), //1
            new Image(ImageHandler.class.getResource("assets/wall_corner.png").toExternalForm(), 32, 32, true, true), //2
            new Image(ImageHandler.class.getResource("assets/player_up.png").toExternalForm(), 32, 32, true, true), //3
            new Image(ImageHandler.class.getResource("assets/player_down.png").toExternalForm(), 32, 32, true, true), //4
            new Image(ImageHandler.class.getResource("assets/spike.png").toExternalForm(), 32, 32, true, true), //5
    };

}