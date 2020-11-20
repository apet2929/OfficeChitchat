package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Player extends BasicPerson{
    public enum Status {
        Default,
        Looking
    };

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
    public Player( int x, int y, Floor floor, Status status) {
        super(x,y, 50, 50, Main.PLAYER_SRC, floor);
        this.setID(ID.Player);
        this.setDescription( "It's you");
        this.status = status;

    }

    public void moveDown(){
        if(getPosY() >= floor.getHeight()) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if (floor.props[this.getPosX()][this.getPosY() + 1].isPassable()) {
                if(floor.props[this.getPosX()][this.getPosY() + 1].getID() == ID.Empty){
                    this.floor.props[this.getPosX()][this.getPosY()] = this.floor.props[this.getPosX()][this.getPosY() + 1];
                    this.floor.props[this.getPosX()][this.getPosY() + 1] = this;
                    this.setPosY(this.getPosY() + 1);
                } else {
                    floor.switchPlaces(this, this.floor.props[this.getPosX()][this.getPosY() +1]);
                }
            } else {
                Main.print("You bumped into a " + floor.props[this.getPosX()][this.getPosY() + 1].getID());
            }
        }
        this.setSprite(new Image(Main.genImages(DOWN_IMAGE)));

    }

    public void moveUp(){

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
                    floor.switchPlaces(this, this.floor.props[this.getPosX()][this.getPosY() - 1]);
                }
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX()][this.getPosY() - 1].getID());
            }
        }
        this.setSprite(new Image(Main.genImages(UP_IMAGE)));
    }
    public void moveLeft(){

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
                    floor.switchPlaces(this, this.floor.props[this.getPosX()-1][this.getPosY()]);
                }
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX() - 1][this.getPosY()].getID());
            }
        }
        this.setSprite(new Image(Main.genImages(LEFT_IMAGE)));
    }
    public void moveRight() {
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
                    floor.switchPlaces(this, this.floor.props[this.getPosX()+1][this.getPosY()]);
                }
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX() + 1][this.getPosY()].getID());
            }
        }
        this.setSprite(new Image(Main.genImages(RIGHT_IMAGE)));
    }
    public void setSprite(Image image){
        imageView.setImage(image);
        updatePosition();
        this.floor.update();
    }


    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
