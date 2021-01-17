package sample;

import java.io.Serializable;

import static sample.Main.genImages;

public class Prop implements Serializable {
    //This class is a basic class that other classes like walls and chairs and shit will extend from
    private int x, y;
    private ID id;
    private boolean walkable;
    private String description;
    private int imageID;
    public boolean animating;
    public Floor floor;
    public int gCost;
    public int hCost;

    public Prop parent;
    //    private Image sprite;

    public int width, height;




    public Prop(ID id, boolean walkable, int x, int y, int width, int height, String description, int imageID, Floor floor){
        this.id = id;
        this.walkable = walkable;
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

    public Prop(int posX, int posY){ //For empty props
        this.setXY(posX, posY);
        this.id = ID.Empty;
        this.walkable = true;
        this.description = "It's empty";
        this.width = Main.scale;
        this.height = Main.scale;
        this.imageID = Main.FLOOR_SRC;
        animating = false;
    }


    public static Prop clone(Prop prop, Floor floor) {
        return new Prop(prop.getID(), prop.walkable,  prop.x, prop.y, prop.width, prop.height, prop.getDescription(), prop.getImageID(), floor);
    }

    public ID getID() {
        return id;
    }
    public void setID(ID id) {
        this.id = id;
    }


    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
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

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }
    public int fCost(){
        return gCost + hCost;
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
                ", passable=" + walkable +
                ", description='" + description + '\'' +
                ", imageID=" + imageID +
                ", width=" + width +
                ", height=" + height +
                '}' + '\n';
    }
}
