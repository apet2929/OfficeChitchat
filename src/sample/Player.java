package sample;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Objects;


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
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player( int x, int y, Floor floor, Status status, GameStateManager gameStateManager) {
        super(x,y, 50, 50, floor);
        this.setID(ID.Player);
        this.setDescription( "It's you");
        this.status = status;
        this.gameStateManager = gameStateManager;

    }

    public void moveDown(){
//        if(getPosY() >= floor.getHeight()-1){
//            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
//        } else {
//            if(floor.props[getPosX()][getPosY()+1] == null){ //If the space im moving into is empty
//                floor.props[getPosX()][getPosY()+1] = this;
//                floor.props[getPosX()][getPosY()] = null;
//                setPosY(getPosY()+1);
//            } else { //If the space im moving to has a prop in it
//                Prop moveTo = floor.props[getPosX()][getPosY()+1];
////                handleMovement(moveTo);
//            }
//        }
//        this.setImageID(Main.PLAYER_DOWN_SRC);
//        floor.update();
        this.setImageID(Main.PLAYER_DOWN_SRC);
        Prop prop = floor.moveDown(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
        }
    }

    public void moveUp(){
//        if(getPosY() == 0){
//            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
//        } else {
//            if(floor.props[getPosX()][getPosY()-1] == null){ //If the space im moving into is empty
//                floor.props[getPosX()][getPosY()-1] = this;
//                floor.props[getPosX()][getPosY()] = null;
//                setPosY(getPosY()-1);
//            } else { //If the space im moving to has a prop in it
//                Prop moveTo = floor.props[getPosX()][getPosY()-1];
////                handleMovement(moveTo);
//            }
//        }
//        this.setImageID(Main.PLAYER_UP_SRC);
//        floor.update();
        this.setImageID(Main.PLAYER_UP_SRC);
        Prop prop = floor.moveUp(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
        }
    }
    public void moveLeft(){
//        if(getPosX() == 0){
//            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
//        } else {
//            if(floor.props[getPosX()-1][getPosY()] == null){ //If the space im moving into is empty
//                floor.props[getPosX()-1][getPosY()] = this;
//                floor.props[getPosX()][getPosY()] = null;
//                setPosX(getPosX()-1);
//            } else { //If the space im moving to has a prop in it
//                Prop moveTo = floor.props[getPosX()-1][getPosY()];
////                handleMovement(moveTo);
//            }
//        }
//        this.setImageID(Main.PLAYER_LEFT_SRC);
//        floor.update();
        this.setImageID(Main.PLAYER_LEFT_SRC);
        Prop prop = floor.moveLeft(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
        };
    }
    public void moveRight() {
//        if(getPosX() >= floor.getWidth()-1){
//            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
//        } else {
//            if(floor.props[getPosX()+1][getPosY()] == null){ //If the space im moving into is empty
//                floor.props[getPosX()+1][getPosY()] = this;
//                floor.props[getPosX()][getPosY()] = null;
//                setPosX(getPosX()+1);
//            } else { //If the space im moving to has a prop in it
//                Prop moveTo = floor.props[getPosX()+1][getPosY()];
//                handleMovement(moveTo, getRightTransition());
//            }
//        }
//        this.setImageID(Main.PLAYER_RIGHT_SRC);
//        floor.update();
        this.setImageID(Main.PLAYER_RIGHT_SRC);
        Prop prop = floor.moveRight(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
        }
    }

    private void die() {
        gameStateManager.setCurGameState(GameStateManager.GameState.GAMEOVER);
    }

    //Returns true if the player moves, false otherwise
    public boolean handleMovement(Prop moveTo, TranslateTransition transition){
        if(moveTo == null){
            floor.playAnimation(transition, getPosX(), getPosY());
            animating = true;
        } else {
            if(moveTo.isPassable()){ //If it is passable
                floor.playAnimation(transition, getPosX(), getPosY());
                floor.setPropSwitch(this, moveTo);
                return true;
            } else { //If it is not passable
                Main.print("You bumped into a " + moveTo.getID());
                if (moveTo.getID() == ID.Spike) {
                    gameStateManager.setCurGameState(GameStateManager.GameState.GAMEOVER);
                }
                return false;
            }
        }
        return false;
    }

    public TranslateTransition getUpTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByY(-Main.scale);
        transition.setOnFinished(e -> {
            floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
            floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
            //Actual engine movement
            floor.props[getPosX()][getPosY()-1] = this;
            floor.props[getPosX()][getPosY()] = null;
            setPosY(getPosY() - 1);
            animating = false;
        });
        return transition;
    }
    public TranslateTransition getDownTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByY(Main.scale);
        transition.setOnFinished(e -> {
            floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
            floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
            //Actual engine movement
            floor.props[getPosX()][getPosY()+1] = this;
            floor.props[getPosX()][getPosY()] = null;
            setPosY(getPosY() + 1);
            animating = false;
        });
        return transition;
    }
    public TranslateTransition getLeftTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByX(-Main.scale);
        transition.setOnFinished(e -> {
            floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
            floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
            //Actual engine movement
            floor.props[getPosX() - 1][getPosY()] = this;
            floor.props[getPosX()][getPosY()] = null;
            setPosX(getPosX() - 1);
            animating = false;
        });
        return transition;
    }
    public TranslateTransition getRightTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByX(Main.scale);
        transition.setOnFinished(e -> {
            floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
            floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
            //Actual engine movement
            floor.props[getPosX() + 1][getPosY()] = this;
            floor.props[getPosX()][getPosY()] = null;
            setPosX(getPosX() + 1);
            animating = false;
        });
        return transition;
    }





}
