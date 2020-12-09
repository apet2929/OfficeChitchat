package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
    private TranslateTransition transition;
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
        transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setAutoReverse(false);

    }

    public void moveDown(){
        if(getPosY() >= floor.getHeight()-1){
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if(floor.props[getPosX()][getPosY()+1] == null){ //If the space im moving into is empty
                //Animation
                transition.setByY(Main.scale);
                transition.setByX(0);
                transition.setOnFinished(e -> {
                    floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX()*Main.scale);
                    floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY()*Main.scale);
                    floor.props[getPosX()][getPosY()+1] = this;
                    floor.props[getPosX()][getPosY()] = null;
                    setPosY(getPosY()+1);
                });
                floor.playAnimation(transition, getPosX(), getPosY());
                //Actual engine movement

            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX()][getPosY()+1];
                if (handleMovement(moveTo)) {
                    //Animation
                    transition.setByX(Main.scale);
                    transition.setOnFinished(e -> {
                        floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
                        floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
                    });
                    floor.playAnimation(transition, getPosX(), getPosY());
                }
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
                //Animation
                transition.setByY(-Main.scale);
                transition.setByX(0);
                transition.setOnFinished(e -> {
                    floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX()*Main.scale);
                    floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY()*Main.scale);
                    //Actual engine movement
                    floor.props[getPosX()][getPosY()-1] = this;
                    floor.props[getPosX()][getPosY()] = null;
                    setPosY(getPosY()-1);
                });
                floor.playAnimation(transition, getPosX(), getPosY());

            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX()][getPosY()-1];
                if (handleMovement(moveTo)) {
                    //Animation
                    transition.setByX(Main.scale);
                    transition.setOnFinished(e -> {
                        floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
                        floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
                    });
                    floor.playAnimation(transition, getPosX(), getPosY());
                }
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
                //Animation
                transition.setByX(-Main.scale);
                transition.setByY(0);
                transition.setOnFinished(e -> {
                    floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX()*Main.scale);
                    floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY()*Main.scale);
                    //Actual engine movement
                    floor.props[getPosX()-1][getPosY()] = this;
                    floor.props[getPosX()][getPosY()] = null;
                    setPosX(getPosX()-1);
                });
                floor.playAnimation(transition, getPosX(), getPosY());

            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX() - 1][getPosY()];
                if (handleMovement(moveTo)) {
                    //Animation
                    transition.setByX(-Main.scale);
                    transition.setOnFinished(e -> {
                        floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
                        floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
                    });
                    floor.playAnimation(transition, getPosX(), getPosY());
                }
            }
        }
        this.setImageID(Main.PLAYER_LEFT_SRC);
        floor.update();
    }

    public void moveRight() {
        if (getPosX() >= floor.getWidth() - 1) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if (floor.props[getPosX() + 1][getPosY()] == null) { //If the space im moving into is empty
                //Animation
                transition.setByX(Main.scale);
                transition.setByY(0);
                transition.setOnFinished(e -> {
                    floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
                    floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
                    //Actual engine movement
                    floor.props[getPosX() + 1][getPosY()] = this;
                    floor.props[getPosX()][getPosY()] = null;
                    setPosX(getPosX() + 1);
                });
                floor.playAnimation(transition, getPosX(), getPosY());

            } else { //If the space im moving to has a prop in it
                Prop moveTo = floor.props[getPosX() + 1][getPosY()];
                if (handleMovement(moveTo)) {
                    //Animation
                    transition.setByX(Main.scale);
                    transition.setOnFinished(e -> {
                        floor.propImageViews[getPosX()][getPosY()].setLayoutX(getPosX() * Main.scale);
                        floor.propImageViews[getPosX()][getPosY()].setLayoutY(getPosY() * Main.scale);
                    });
                    floor.playAnimation(transition, getPosX(), getPosY());
                }
            }
            this.setImageID(Main.PLAYER_RIGHT_SRC);
            floor.update();
        }
    }

    //Returns true if they switch, false otherwise
    public boolean handleMovement(Prop moveTo){
        if(moveTo.isPassable()){ //If it is passable
            floor.setPropSwitch(this, moveTo);
            return true;
        } else { //If it is not passable
            Main.print("You bumped into a " + moveTo.getID());
            if(moveTo.getID() == ID.Spike){
                gameStateManager.setCurGameState(GameStateManager.GameState.GAMEOVER);
            }
            return false;

        }
    }


}
