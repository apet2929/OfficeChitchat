package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicPerson extends Prop{
    public Floor floor;
    public BasicPerson( int x, int y, int width, int height, Floor floor) {
        super(ID.Person, true, x, y, width, height, "Your coworker and co-sufferer", Main.PLAYER_UP_SRC);
        if(floor != null){
            this.floor = floor;
            floor.propImageViews[x][y] = new ImageView(new Image(Main.genImages(Main.PLAYER_UP_SRC)));
            System.out.println("ImageView added");
        }
        updatePosition();
    }

    public void updatePosition() {
        if(floor != null) {
            floor.setImage(getPosX(),getPosY(),getImageID());

        } else {
            System.out.println("floor is null");
        }
    }
}
