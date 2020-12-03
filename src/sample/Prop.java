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
//    public static Prop FLOOR = new Prop(ID.Empty, true, 0, 0, 50, 50, "There's nothing there.", 0);
    public static Prop PERSON = new BasicPerson( 0,0,50,50, null);
    public static Prop WALL = new Wall(0,0);
    private int imageID;
    //    private Image sprite;

    public int width, height;
    public Prop(ID id, boolean passable, int x, int y, int width, int height, String description, int imageID){
        this.id = id;
        this.passable = passable;
        this.x = x;
        this.y = y;
        this.description = description;
        this.width = width;
        this.height = height;
    }

    public static Prop getEmptyProp() {
        return new Prop(ID.Empty, true, 0, 0, 50, 50, "There's nothing there.", 0);

    }

    public static Prop clone(Prop prop) {
        return new Prop(prop.getID(), prop.passable,  prop.x, prop.y, prop.width, prop.height, prop.getDescription(), prop.getImageID());
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


    @Override
    public String toString() {
        return "Prop{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", passable=" + passable +
                '}';
    }
}
