package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sample.Main.*;

public class Floor extends Group implements Serializable {

    public static final int UP_DIRECTION = 0;
    public static final int LEFT_DIRECTION = 1;
    public static final int RIGHT_DIRECTION = 2;
    public static final int DOWN_DIRECTION = 3;

    public int lvl;
    public final int width = (Main.WIDTH-300)/Main.scale;
    public final int height = Main.HEIGHT/Main.scale;
    public Prop[][] props = new Prop[width][height];
    private final ImageView[][] floor = new ImageView[width][height];
    public ImageView[][] propImageViews = new ImageView[width][height];
    public int fillTile;
    private ArrayList<Prop> toRemove;
    private TranslateTransition transition;
    private ArrayList<BufferedAction> bufferedActions;

    public ArrayList<Prop> path;

    public boolean startUp;

    public Floor(int lvl){
        startUp = true;
        this.lvl = lvl;
        fillTile = FLOOR_SRC;
        this.transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setAutoReverse(false);
        transition.setCycleCount(1);

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                floor[i][j] = new ImageView(new Image(Main.genImages(fillTile)));
                floor[i][j].setLayoutX(i*Main.scale);
                floor[i][j].setLayoutY(j*Main.scale);

                propImageViews[i][j] = new ImageView(new Image(Main.genImages(WALL_CORNER_SRC)));
                propImageViews[i][j].setLayoutX(i*Main.scale);
                propImageViews[i][j].setLayoutY(j*Main.scale);
                propImageViews[i][j].setTranslateZ(2);

                addProp(new Prop(i,j));
            }

            getChildren().addAll(floor[i]);
            getChildren().addAll(propImageViews[i]);
        }
        toRemove = new ArrayList<>();
        bufferedActions = new ArrayList<>();
        startUp = false;
    }

    public void setTransitionDirection(int x, int y){
        transition.setByX(scale * x);
        transition.setByY(scale * y);
    }

    public void addProp(Prop prop){
        props[prop.getPosX()][prop.getPosY()] = prop;
        setImage(prop.getPosX(),prop.getPosY(), prop.getImageID());
        update();
    }

    public void addEmptyProp(int x, int y){
        props[x][y] = new Prop(x,y);
    }

    public void removeProp(Prop prop){
        toRemove.add(prop);
    }

    public void playAnimation(TranslateTransition transition, int x, int y){
        if(propImageViews[x][y].getImage() != null){
            if(debug) System.out.println("Playing animation");
            setImageViewOnTop(x,y);
            transition.setNode(propImageViews[x][y]);
            props[x][y].animating = true;
            transition.play();
        } else {
            System.out.println("The ImageView the animation is applied to at x: " + x + " y: " + y + " is null");
        }

    }

    //Because layer is decided by order of instantiation, to set the image on top, just remake the imageview.
    public void setImageViewOnTop(int x, int y){
        getChildren().remove(propImageViews[x][y]);
        propImageViews[x][y] = new ImageView(new Image(genImages(props[x][y].getImageID())));
        propImageViews[x][y].setLayoutX(x * scale);
        propImageViews[x][y].setLayoutY(y * scale);
        propImageViews[x][y].setTranslateZ(2);
        getChildren().add(propImageViews[x][y]);
    }

    public void clear(){
        props = new Prop[WIDTH/scale][HEIGHT/scale];
        propImageViews = new ImageView[WIDTH/scale][HEIGHT/scale];
        toRemove.clear();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                floor[i][j] = new ImageView(new Image(Main.genImages(fillTile)));
                floor[i][j].setLayoutX(i*Main.scale);
                floor[i][j].setLayoutY(j*Main.scale);
                floor[i][j].setFitHeight(scale);
                floor[i][j].setFitWidth(scale);

                propImageViews[i][j] = new ImageView(new Image(Main.genImages(WALL_CORNER_SRC)));
                propImageViews[i][j].setLayoutX(i*Main.scale);
                propImageViews[i][j].setLayoutY(j*Main.scale);
                propImageViews[i][j].setTranslateZ(2);
                propImageViews[i][j].setFitHeight(scale);
                propImageViews[i][j].setFitWidth(scale);

                addEmptyProp(i,j);
            }
            getChildren().addAll(floor[i]);
            getChildren().addAll(propImageViews[i]);
        }
    }

    public void tileFloor(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                floor[i][j].setImage(new Image(genImages(fillTile)));
            }
        }
    }

    public void moveUp(int x, int y, boolean override){
        if(props[x][y] != null){
            if(debug) System.out.println("Moving " + props[x][y].getID() + " up at " + props[x][y].getPosX() + " "+ props[x][y].getPosY());
            if(props[x][y].isWalkable() || override) moveUp(props[x][y]);
        }

    }
    public void moveRight(int x, int y, boolean override){
        if(props[x][y] != null){
            if(debug) System.out.println("Moving " + props[x][y].getID() + " right at " + props[x][y].getPosX() + " "+ props[x][y].getPosY());
            if(props[x][y].isWalkable() || override) moveRight(props[x][y]);
        }
    }
    public void moveLeft(int x, int y, boolean override){
        if(props[x][y] != null){
            if(debug) System.out.println("Moving " + props[x][y].getID() + " left at " + props[x][y].getPosX() + " "+ props[x][y].getPosY());
            if(props[x][y].isWalkable() || override) moveLeft(props[x][y]);
        }
    }
    public void moveDown(int x, int y, boolean override){
        if(props[x][y] != null){
            if(debug) System.out.println("Moving " + props[x][y].getID() + " down at " + props[x][y].getPosX() + " "+ props[x][y].getPosY());
            if(props[x][y].isWalkable() || override) moveDown(props[x][y]);
        }
    }

    public Prop moveUp(Prop prop){
        if(prop.getPosY() != 0 && !prop.animating){ //here
            TranslateTransition transition = getUpTransition(); //here
            if(props[prop.getPosX()][prop.getPosY()-1] == null){ //here
                transition.setOnFinished(e -> {
                    ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                    imageView.setLayoutY(imageView.getLayoutY()+scale); //here
                    //Actual engine movement
                    props[prop.getPosX()][prop.getPosY()-1] = prop; //here
                    props[prop.getPosX()][prop.getPosY()] = null;
                    prop.setPosY(prop.getPosY()-1); //here
                    prop.animating = false;
                    tick();
                    resolveBufferedActions();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()][prop.getPosY()-1]; //here
                if(moveTo.isWalkable()){ //If it is walkable
                    switchPlaces(prop, moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosY()-1].getID()); //here
                }
                update();
                return moveTo;
            }
        } else if (prop.animating){
            bufferAction(new BufferedAction(prop, UP_DIRECTION));
        }
        update();
        return null;
    }

    public Prop moveDown(Prop prop){
        if(prop.getPosY() < getHeight() && !prop.animating){ //here
            TranslateTransition transition = getDownTransition(); //here
            if(props[prop.getPosX()][prop.getPosY()+1] == null){ //here

                transition.setOnFinished(e -> {
                    ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                    imageView.setLayoutY(imageView.getLayoutY()-scale); //here
                    //Actual engine movement
                    props[prop.getPosX()][prop.getPosY()+1] = prop; //here
                    props[prop.getPosX()][prop.getPosY()] = new Prop(prop.getPosX(), prop.getPosY());
                    prop.setPosY(prop.getPosY()+1); //here
                    prop.animating = false;
                    tick();
                    resolveBufferedActions();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it

                Prop moveTo = props[prop.getPosX()][prop.getPosY()+1]; //here
                if(moveTo.isWalkable()){ //If it is passable
                    switchPlaces(prop, moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosY()+1].getID()); //here
                }
                update();
                return moveTo;
            }
        } else if (prop.animating){
            bufferAction(new BufferedAction(prop, DOWN_DIRECTION));
        }
        update();
        return null;
    }


    public Prop moveLeft(Prop prop){
        if(prop.getPosX() != 0 && !prop.animating){ //here
            TranslateTransition transition = getLeftTransition(); //here
            if(props[prop.getPosX()-1][prop.getPosY()].getID() != ID.Empty){ //here
                transition.setOnFinished(e -> {
                    ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                    imageView.setLayoutX(imageView.getLayoutX()+scale); //here
                    //Actual engine movement
                    props[prop.getPosX()-1][prop.getPosY()] = prop; //here
                    props[prop.getPosX()][prop.getPosY()] = new Prop(prop.getPosX(), prop.getPosY());
                    prop.setPosX(prop.getPosX()-1); //here
                    prop.animating = false;
                    tick();
                    resolveBufferedActions();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()-1][prop.getPosY()];
                if(moveTo.isWalkable()){ //If it is passable
                    switchPlaces(prop, moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()-1][prop.getPosY()].getID());
                }
                update();
                return moveTo;
            }
        } else if (prop.animating){
            bufferAction(new BufferedAction(prop, LEFT_DIRECTION));
        }
        update();
        return null;
    }

    public Prop moveRight(Prop prop){
        if(prop.getPosX() < getWidth() && !prop.animating){ //If the prop is not at the right edge of the screen
            TranslateTransition transition = getRightTransition();
            if(props[prop.getPosX()+1][prop.getPosY()] == null){ //If the space im moving into is empty
                transition.setOnFinished(e -> {
                    ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                    imageView.setLayoutX(imageView.getLayoutX()-scale);
                    //Actual engine movement
                    props[prop.getPosX()+1][prop.getPosY()] = prop;
                    props[prop.getPosX()][prop.getPosY()] = null;
                    prop.setPosX(prop.getPosX()+1);
                    prop.animating = false;
                    tick();
                    resolveBufferedActions();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()+1][prop.getPosY()];
                if(moveTo.isWalkable()){ //If it is passable
                    switchPlaces(prop, moveTo);
//                    transition.setOnFinished(e -> {
//                        ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
//                        imageView.setLayoutX(imageView.getLayoutX()-scale);
//                        prop.setPosX(prop.getPosX()+1);
//                        prop.animating = false;
//                        resolveBufferedActions();
//                    });
//                    playAnimation(transition, prop.getPosX(), prop.getPosY());
//                    moveLeft(moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()+1][prop.getPosY()].getID());
                }
                update();
                return moveTo;
            }
        } else if (prop.animating){
            bufferAction(new BufferedAction(prop, RIGHT_DIRECTION));
        }
        update();
        return null;
    }

    public void move(Prop prop, int direction){
        switch(direction) {
            case UP_DIRECTION -> moveUp(prop);
            case DOWN_DIRECTION -> moveDown(prop);
            case LEFT_DIRECTION -> moveLeft(prop);
            case RIGHT_DIRECTION -> moveRight(prop);
            default -> System.out.println("Direction of "+ direction +" is not valid");
        }
    }

    public Prop getProp(int x, int y){
        return props[x][y];
    }

    public void bufferAction(BufferedAction action){
        System.out.println("Buffering Action: " + action);
        bufferedActions.add(action);
    }

    private void resolveBufferedActions() {
        if (bufferedActions.size() != 0) {
            System.out.println("Number of buffered actions: " + bufferedActions.size());

            for (BufferedAction bufferedAction : bufferedActions) {
                System.out.println("Resolving Buffered Action: " + bufferedAction);
                if (!bufferedAction.resolved) {
                    bufferedAction.resolve();
                }
            }
            bufferedActions.clear();
        }
    }

    public Player getPlayer(){
        for(Prop[] propArray : props){
            for(Prop prop: propArray){
                if(prop != null) {
                    if (prop.getID() == ID.Player) return (Player) prop;
                }
            }
        }
        System.out.println("Player could not be found in floor");
        return null;
    }

    public TranslateTransition getUpTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByY(-Main.scale);
        return transition;
    }
    public TranslateTransition getDownTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByY(Main.scale);
        return transition;
    }
    public TranslateTransition getLeftTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByX(-Main.scale);
        return transition;
    }
    public TranslateTransition getRightTransition() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setByX(Main.scale);
        return transition;
    }
    public void setImage(int x, int y, int imageID){
        propImageViews[x][y].setImage(new Image(Main.genImages(imageID)));
    }

    public List<Prop> getNeighbors(Prop prop){
        List<Prop> neighbors = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;

                int checkX = prop.getPosX() + x;
                int checkY = prop.getPosY() + y;

                if (checkX >= 0 && checkX < width && checkY >= 0 && checkY < height) {
                    neighbors.add(props[checkX][checkY]);
                }
            }
        }

        return neighbors;
    }


    public void switchPlaces(Prop prop, Prop moveTo){
        int propX = prop.getPosX();
        int propY = prop.getPosY();
        int moveToX = moveTo.getPosX();
        int moveToY = moveTo.getPosY();

        props[propX][propY] = moveTo;
        props[moveToX][moveToY] = prop;
        prop.setXY(moveToX,moveToY);
        moveTo.setXY(propX, propY);
        update();
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void tick(){ //Called every time player moves or interacts with anything
        //BufferedActions get resolved first
        resolveBufferedActions();
        for(Prop[] propArray : props){
            for(Prop prop: propArray){
                if(prop != null) prop.tick(this);
            }
        }
        update();
    }

    public void update(){ //May separate this into tick and render methods.
        if(debug) System.out.println("Updating");
        if(!startUp) {
            for (Prop prop : toRemove) {
                System.out.println("Removed " + prop);
                addProp(new Prop(prop.getPosX(), prop.getPosY()));

            }
            toRemove.clear();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Prop prop = props[i][j];
                    if (prop != null) {
                        propImageViews[i][j].setImage(new Image(genImages(prop.getImageID())));
                        if (debug) System.out.print("at Array x: " + i + "y: " + j + prop);
                    } else {
                        propImageViews[i][j].setImage(null);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Floor{" +
                "props=" + Arrays.toString(props) +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
