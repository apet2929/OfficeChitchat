package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Player extends Prop{
    public enum Status {
        Default,
        Looking
    };
    private Floor floor;
    public static final String UP_IMAGE = "player_up";
    public static final String DOWN_IMAGE = "player_down";
    public static final String LEFT_IMAGE = "player_left";
    public static final String RIGHT_IMAGE = "player_right";

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player(ImageView sprite, int x, int y, Floor floor, Status status) {
        super(ID.Player, true, sprite,x,y, 50, 50, "It's you");
        this.floor = floor;
        this.status = status;
    }

    public void moveDown(){
        this.setSprite(new Image(Main.genImages(DOWN_IMAGE)));
        if(getPosY() >= floor.getHeight()) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if (floor.props[this.getPosX()][this.getPosY() + 1].isPassable()) {
                if(floor.props[this.getPosX()][this.getPosY() + 1].getID() == ID.Empty){
                    this.floor.props[this.getPosX()][this.getPosY()] = this.floor.props[this.getPosX()][this.getPosY() + 1];
                    this.floor.props[this.getPosX()][this.getPosY() + 1] = this;
                    this.setPosY(this.getPosY() + 1);
                } else {
                    floor.switchPlaces(this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() + 1);
                }
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX()][this.getPosY() + 1].getID());
            }
        }
        updateSprite();

    }

    public void moveUp(){
        this.setSprite(new Image(Main.genImages(UP_IMAGE)));
        if(getPosY() <= 0) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else{
            Prop moveTo = floor.props[this.getPosX()][this.getPosY()-1];
            if (moveTo.isPassable()) {
                if(floor.props[this.getPosX()][this.getPosY() - 1].getID() == ID.Empty){
                    this.floor.props[this.getPosX()][this.getPosY()] = this.floor.props[this.getPosX()][this.getPosY() - 1];
                    this.floor.props[this.getPosX()][this.getPosY() - 1] = this;
                    this.setPosY(this.getPosY() - 1);
                } else {
                    floor.switchPlaces(this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() - 1);
                }
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX()][this.getPosY() - 1].getID());
            }
        }
        updateSprite();
    }
    public void moveLeft(){
        this.setSprite(new Image(Main.genImages(LEFT_IMAGE)));
        if(getPosX() <= 0) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
            System.out.println(floor.getWidth() + " " + floor.getHeight());
        } else{
            if (floor.props[this.getPosX() - 1][this.getPosY()].isPassable()) {
                if(floor.props[this.getPosX()-1][this.getPosY()].getID() == ID.Empty){
                    this.floor.props[this.getPosX()][this.getPosY()] = this.floor.props[this.getPosX()-1][this.getPosY()];
                    this.floor.props[this.getPosX()-1][this.getPosY()] = this;
                    this.setPosX(this.getPosX() - 1);
                }  else {
                    floor.switchPlaces(this.getPosX(), this.getPosY(), this.getPosX() - 1, this.getPosY());
                }
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX() - 1][this.getPosY()].getID());
            }
        }
        updateSprite();
    }
    public void moveRight() {
        this.setSprite(new Image(Main.genImages(RIGHT_IMAGE)));
        if (getPosX() >= floor.getWidth()) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
            System.out.println(floor.getWidth() + " " + floor.getHeight());
        } else{
            if (floor.props[this.getPosX() + 1][this.getPosY()].isPassable()) {
                if(floor.props[this.getPosX()+1][this.getPosY()].getID() == ID.Empty){
                    this.floor.props[this.getPosX()][this.getPosY()] = this.floor.props[this.getPosX()+1][this.getPosY()];
                    this.floor.props[this.getPosX()+1][this.getPosY()] = this;
                    this.setPosX(this.getPosX() + 1);
                } else {
                    floor.switchPlaces(this.getPosX(), this.getPosY(), this.getPosX() + 1, this.getPosY());
                }
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX() + 1][this.getPosY()].getID());
            }
        }
        updateSprite();
    }


}
