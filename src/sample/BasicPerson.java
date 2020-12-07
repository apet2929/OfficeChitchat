package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicPerson extends Prop{
    public Floor floor;
    public BasicPerson( int x, int y, int width, int height, Floor floor) {
        super(ID.Person, true, x, y, width, height, "Your coworker and co-sufferer", Main.PLAYER_UP_SRC);
        if(floor != null){
            this.floor = floor;
            this.floor.addProp(this);
        }
        this.setImageID(Main.PLAYER_UP_SRC);
        updatePosition();
    }

    public void updatePosition() {
        if(floor == null) {
            System.out.println("floor is null");
        } else {
            floor.setImage(getPosX(),getPosY(),getImageID());
        }
    }
    public void setFloor(Floor floor) {
        this.floor = floor;
        this.floor.addProp(this);
    }
}
