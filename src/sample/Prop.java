package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Prop {
    //This class is a basic class that other classes like walls and chairs and shit will extend from
    private int x, y;
    private ID id;
    private boolean passable;
//    private Image sprite;
    private ImageView sprite;
    public Prop(ID id, boolean passable, ImageView sprite, int x, int y, int width, int height){
        this.id = id;
        this.passable = passable;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        if(sprite != null) {
            this.sprite.setLayoutX(x * 50);
            this.sprite.setLayoutY(y * 50);
            this.sprite.setFitWidth(width);
            this.sprite.setFitHeight(height);
        }
    }
    public Prop(ID id, boolean passable, ImageView sprite){
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

    public ImageView getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite.setImage(sprite);
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
