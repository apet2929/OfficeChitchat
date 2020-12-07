package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;


public class Player extends BasicPerson {
    public enum Status {
        Default,
        Looking
    };

    public static final String UP_IMAGE = "player_up";
    public static final String DOWN_IMAGE = "player_down";
    public static final String LEFT_IMAGE = "player_left";
    public static final String RIGHT_IMAGE = "player_right";

    private Status status;
    public GameStateManager gameStateManager;
    public Floor floor;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player( int x, int y, Floor floor, Status status, GameStateManager gameStateManager) {
        super(x,y, 50, 50, floor);
        this.setID(ID.Player);
        this.floor = floor;
        this.setDescription( "It's you");
        this.status = status;
        this.gameStateManager = gameStateManager;
    }

    public void moveDown(){
        if(getPosY() >= floor.getHeight()-1){
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if(floor.props[getPosX()][getPosY()+1] == null){ //If the space im moving into is empty
                floor.props[getPosX()][getPosY()+1] = this;
                floor.props[getPosX()][getPosY()] = null;
                setPosY(getPosY()+1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX()][getPosY()+1];
                handleMovement(moveTo);
            }
        }
        this.setImageID(Main.PLAYER_DOWN_SRC);
        floor.update();
    }

    public void moveUp(){
        if(getPosY() == 0){
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if(floor.props[getPosX()][getPosY()-1] == null){ //If the space im moving into is empty
                floor.props[getPosX()][getPosY()-1] = this;
                floor.props[getPosX()][getPosY()] = null;
                setPosY(getPosY()-1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX()][getPosY()-1];
                handleMovement(moveTo);
            }
        }
        this.setImageID(Main.PLAYER_UP_SRC);
        floor.update();
    }
    public void moveLeft(){
        if(getPosX() == 0){
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if(floor.props[getPosX()-1][getPosY()] == null){ //If the space im moving into is empty
                floor.props[getPosX()-1][getPosY()] = this;
                floor.props[getPosX()][getPosY()] = null;
                setPosX(getPosX()-1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX()-1][getPosY()];
                handleMovement(moveTo);
            }
        }
        this.setImageID(Main.PLAYER_LEFT_SRC);
        floor.update();
    }
    public void moveRight() {
        if(getPosX() >= floor.getWidth()-1){
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if(floor.props[getPosX()+1][getPosY()] == null){ //If the space im moving into is empty
                floor.props[getPosX()+1][getPosY()] = this;
                floor.props[getPosX()][getPosY()] = null;
                setPosX(getPosX()+1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX()+1][getPosY()];
                handleMovement(moveTo);
            }
        }
        this.setImageID(Main.PLAYER_RIGHT_SRC);
        floor.update();
    }
    public void handleMovement(Prop moveTo){
        if(moveTo.isPassable()){ //If it is passable
            floor.setPropSwitch(this, moveTo);
        } else { //If it is not passable
            Main.print("You bumped into a " + moveTo.getID());
            if(moveTo.getID() == ID.Spike){
                gameStateManager.setCurGameState(GameStateManager.GameState.GAMEOVER);
            }

        }
    }


}
