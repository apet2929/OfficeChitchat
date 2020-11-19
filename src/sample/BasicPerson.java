package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicPerson extends Prop{
    public ImageView imageView;
    public Floor floor;
    public BasicPerson( int x, int y, int width, int height, Image image, Floor floor) {
        super(ID.Person, true, x, y, width, height, "Your coworker and co-sufferer");
        imageView = new ImageView(image);
        updatePosition();
        if(floor != null){
            this.floor = floor;
            this.floor.getChildren().add(imageView);
        }

    }
    public void updatePosition() {
        imageView.setLayoutX(getPosX()*Main.scale);
        imageView.setLayoutY(getPosY()*Main.scale);
    }
}
