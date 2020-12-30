package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Objects;

import static sample.Main.genImages;

public class Prop implements Serializable {
    //This class is a basic class that other classes like walls and chairs and shit will extend from
    private int x, y;
    private ID id;
    private boolean passable;
    private String description;
    private int imageID;
    public boolean animating;
    public Floor floor;
    //    private Image sprite;

    public int width, height;

    public Prop(ID id, boolean passable, int x, int y, int width, int height, String description, int imageID, Floor floor){
        this.id = id;
        this.passable = passable;
        this.x = x;
        this.y = y;
        this.description = description;
        this.width = width;
        this.height = height;
        this.imageID = imageID;
        if(floor != null){
            floor.setImage(x,y,imageID);
            floor.addProp(this);
            this.floor = floor;
        }
        animating = false;
    }


    public static Prop clone(Prop prop, Floor floor) {
        return new Prop(prop.getID(), prop.passable,  prop.x, prop.y, prop.width, prop.height, prop.getDescription(), prop.getImageID(), floor);
    }

    public ID getID() {
        return id;
    }
    public void setID(ID id) {
        this.id = id;
    }


    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setFloor(Floor floor){
        this.floor = floor;
        floor.addProp(this);
        floor.setImage(getPosX(), getPosY(), imageID);
    }
    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getPosX() {
        return x;
    }

    public void setPosX(int x) {
        this.x = x;
    }


    public int getPosY() {
        return y;
    }

    public void setPosY(int y) {
        this.y = y;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public static Prop getEmptyProp(int x, int y){
//        return new Prop(ID.Empty, true, x,y, 50, 50, "There's nothing there.", 0);
//    }

    public void tick(Floor floor){}
    @Override
    public String toString() {
        return "Prop{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", passable=" + passable +
                ", description='" + description + '\'' +
                ", imageID=" + imageID +
                ", width=" + width +
                ", height=" + height +
                '}' + '\n';
    }
}
