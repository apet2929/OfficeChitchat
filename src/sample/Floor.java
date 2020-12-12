package sample;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static sample.Main.*;


public class Floor extends Group implements Serializable {

    private final int width = (Main.WIDTH-300)/Main.scale;
    private final int height = Main.HEIGHT/Main.scale;
    public Prop[][] props = new Prop[width][height];
    private final ImageView[][] floor = new ImageView[width][height];
    public ImageView[][] propImageViews = new ImageView[width][height];
    private final boolean debug = false;
    public int fillTile;
    public int personCnt = 0;
    private ArrayList<Prop> toRemove;
    private TranslateTransition transition;
    public Floor(){
        System.out.println(props.length);
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

            }

            getChildren().addAll(floor[i]);
            getChildren().addAll(propImageViews[i]);
        }
        toRemove = new ArrayList<>();
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

    public void removeProp(Prop prop){
        toRemove.add(prop);
    }

    //Important note: This function is not required to be 2 props next to each other, they can be diagonal, across the map, whatever.
    public void setPropSwitch(Prop prop1, Prop prop2){
        System.out.println("Switching " + prop1 + " and " + prop2);
        int prop1X = prop1.getPosX();
        int prop1Y = prop1.getPosY();
        int prop2X = prop2.getPosX();
        int prop2Y = prop2.getPosY();
        prop1.setXY(prop2X, prop2Y);
        prop2.setXY(prop1X, prop1Y);
        props[prop2X][prop2Y] = prop1;
        props[prop1X][prop1Y] = prop2;
        update();
    }

    public void playAnimation(TranslateTransition transition, int x, int y){
        if(propImageViews[x][y] != null){
            if(debug) System.out.println("Playing animation");
            setImageViewOnTop(x,y);
            transition.setNode(propImageViews[x][y]);
            props[x][y].animating = true;
            transition.play();
        } else {
            System.out.println("The ImageView the animation is applied to is null");
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
            }
            getChildren().addAll(floor[i]);
            getChildren().addAll(propImageViews[i]);
        }
    }

    public void setPropXYNoSwitch(Prop prop, int x2, int y2){
        props[x2][y2] = prop;
        prop.setXY(x2,y2);
        update();
    }

    public void tileFloor(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                floor[i][j].setImage(new Image(genImages(fillTile)));
            }
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
                    update();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()][prop.getPosY()-1]; //here
                if(moveTo.isPassable()){ //If it is passable
                    transition.setOnFinished(e -> {
                        ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                        imageView.setLayoutY(imageView.getLayoutY()+scale); //here
                        prop.setPosY(prop.getPosY()-1); //here
                        prop.animating = false;
                    });
                    playAnimation(transition, prop.getPosX(), prop.getPosY());
                    moveDown(moveTo); //here
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosY()-1].getID()); //here
                }
                update();
                return moveTo;
            }
        }
        update();
        return null;
    }
    public Prop moveDown(Prop prop){
        if(prop.getPosY() < getHeight() && !prop.animating){ //here
            TranslateTransition transition = getDownTransition(); //here
            if(props[prop.getPosX()][prop.getPosY()+1] == null){ //here

                transition.setOnFinished(e -> {
                    System.out.println("Down animation finished");
                    ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                    imageView.setLayoutY(imageView.getLayoutY()-scale); //here
                    //Actual engine movement
                    props[prop.getPosX()][prop.getPosY()+1] = prop; //here
                    props[prop.getPosX()][prop.getPosY()] = null;
                    prop.setPosY(prop.getPosY()+1); //here
                    prop.animating = false;
                    update();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it
                System.out.println("Yes");
                Prop moveTo = props[prop.getPosX()][prop.getPosY()+1]; //here
                if(moveTo.isPassable()){ //If it is passable
                    transition.setOnFinished(e -> {
                        ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                        imageView.setLayoutY(imageView.getLayoutY()-scale); //here
                        prop.setPosY(prop.getPosY()+1); //here
                        prop.animating = false;
                    });
                    playAnimation(transition, prop.getPosX(), prop.getPosY());
                    moveUp(moveTo); //here
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosY()+1].getID()); //here
                }
                update();
                return moveTo;
            }
        }
        update();
        return null;
    }


    public Prop moveLeft(Prop prop){
        if(prop.getPosX() != 0 && !prop.animating){ //here
            TranslateTransition transition = getLeftTransition(); //here
            if(props[prop.getPosX()-1][prop.getPosY()] == null){ //here
                transition.setOnFinished(e -> {
                    ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                    imageView.setLayoutX(imageView.getLayoutX()+scale); //here
                    //Actual engine movement
                    props[prop.getPosX()-1][prop.getPosY()] = prop; //here
                    props[prop.getPosX()][prop.getPosY()] = null;
                    prop.setPosX(prop.getPosX()-1); //here
                    prop.animating = false;
                    update();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()-1][prop.getPosY()];
                if(moveTo.isPassable()){ //If it is passable
                    transition.setOnFinished(e -> {
                        ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                        imageView.setLayoutX(imageView.getLayoutX()-scale);
                        prop.setPosX(prop.getPosX()-1);
                        prop.animating = false;
                    });
                    playAnimation(transition, prop.getPosX(), prop.getPosY());
                    moveRight(moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()-1][prop.getPosY()].getID());
                }
                update();
                return moveTo;
            }
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
                    update();
                });
                playAnimation(transition, prop.getPosX(), prop.getPosY());
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()+1][prop.getPosY()];
                if(moveTo.isPassable()){ //If it is passable
                    transition.setOnFinished(e -> {
                        ImageView imageView = propImageViews[prop.getPosX()][prop.getPosY()];
                        imageView.setLayoutX(imageView.getLayoutX()-scale);
                        prop.setPosX(prop.getPosX()+1);
                        prop.animating = false;
                    });
                    playAnimation(transition, prop.getPosX(), prop.getPosY());
                    moveLeft(moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()+1][prop.getPosY()].getID());
                }
                update();
                return moveTo;
            }
        }
        update();
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
//    public void switchPlaces(int x1, int y1, int x2, int y2){
//
//        Prop temp = props[x1][y1];
//        props[x1][y1] = props[x2][y2];
//        props[x2][y2] = temp;
//        props[x2][y2].setXY(x2,y2);
//        props[x1][y1].setXY(x1,y1);
//
//        update();
//    }

    public void switchPlaces(Prop prop1, Prop prop2){
        int prop1X = prop1.getPosX();
        int prop1Y = prop1.getPosY();
        int prop2X = prop2.getPosX();
        int prop2Y = prop2.getPosY();
        props[prop1X][prop1Y] = prop2;
        props[prop2X][prop2Y] = prop1;
        prop2.setXY(prop1X, prop1Y);
        prop1.setXY(prop2X, prop2Y);

    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(){ //May seperate this into tick and render methods.
        System.out.println("Updating");
        for(Prop prop: toRemove){
            System.out.println("Removed " + prop);
            props[prop.getPosX()][prop.getPosY()] = null;
        }
        toRemove.clear();
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Prop prop = props[i][j];
                if (prop != null) {
                    propImageViews[i][j].setImage(new Image(Objects.requireNonNull(genImages(prop.getImageID()))));
                    if(debug) System.out.print("at Array x: " + i + "y: " + j + prop);
                } else {
                    propImageViews[i][j].setImage(null);
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
