package sample;

import javafx.scene.image.ImageView;

public class Prop extends ImageView {
    //This class is a basic class that other classes like walls and chairs and shit will extend from
    private int x, y;
    private ID id;
    private boolean passable;
    private Sprite sprite;

    public Prop(ID id, boolean passable, Sprite sprite, int x, int y, int width, int height){
        this.id = id;
        this.passable = passable;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.setLayoutX(x*50);
        this.setLayoutY(y*50);
        setFitWidth(width);
        setFitHeight(height);
    }
    public Prop(ID id, boolean passable, Sprite sprite){
        this.id = id;
        this.passable = passable;
        this.sprite = sprite;
    }
    public ID getID() {
        return id;
    }
    public void setID(ID id) {
        this.id = id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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

    public static Prop getEmptyProp(int x, int y){
        return new Prop(ID.Empty, true, null, x,y, 50, 50);
    }

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
