package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private final boolean debug = true;
    public int fillTile;
    public int wallCnt = 0;
    public int personCnt = 0;
    private ArrayList<Prop> toRemove;
    public Floor(){
        System.out.println(props.length);
        fillTile = FLOOR_SRC;
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
            toRemove = new ArrayList<>();
            getChildren().addAll(floor[i]);
            getChildren().addAll(propImageViews[i]);
        }
    }

//    public void setPropXYSwitch(int x1, int y1, int x2, int y2){
//        props[x1][y1].setXY(x2,y2); //player.setXY(x2,y2)
//        props[x2][y2] = props[x1][y1]; //player is put into
//        props[x2][y2].setXY(x1,y1);
//        update();
//    }
    public void removeProp(Prop prop){
        toRemove.add(prop);
    }
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
    public void moveUp(Prop prop){
        if(prop.getPosY() > 0){
            if(props[prop.getPosX()][prop.getPosY()-1] == null){ //If the space im moving into is empty
                props[prop.getPosX()][prop.getPosY()-1] = prop;
                props[prop.getPosX()][prop.getPosY()] = null;
                prop.setPosY(prop.getPosY()-1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()][prop.getPosY()-1];
                if(moveTo.isPassable()){ //If it is passable
                    setPropSwitch(prop, moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosY() - 1].getID());
                }
            }
        }
    }
    public void moveDown(Prop prop){
        if(prop.getPosY() < getHeight()){
            if(props[prop.getPosX()][prop.getPosY()+1] == null){ //If the space im moving into is empty
                props[prop.getPosX()][prop.getPosY()+1] = prop;
                props[prop.getPosX()][prop.getPosY()] = null;
                prop.setPosY(prop.getPosY()+1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()][prop.getPosY()+1];
                if(moveTo.isPassable()){ //If it is passable
                    setPropSwitch(prop, moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosY() + 1].getID());
                }
            }
        }
    }
    public void moveLeft(Prop prop){
        if(prop.getPosX() > 0){
            if(props[prop.getPosX()][prop.getPosX()-1] == null){ //If the space im moving into is empty
                props[prop.getPosX()][prop.getPosX()-1] = prop;
                props[prop.getPosX()][prop.getPosX()] = null;
                prop.setPosY(prop.getPosX()-1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()][prop.getPosX()-1];
                if(moveTo.isPassable()){ //If it is passable
                    setPropSwitch(prop, moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosX() - 1].getID());
                }
            }
        }
    }
    public void moveRight(Prop prop){
        if(prop.getPosX() < getWidth()){
            if(props[prop.getPosX()][prop.getPosX()+1] == null){ //If the space im moving into is empty
                props[prop.getPosX()][prop.getPosX()+1] = prop;
                props[prop.getPosX()][prop.getPosX()] = null;
                prop.setPosY(prop.getPosX()+1);
            } else { //If the space im moving to has a prop in it
                Prop moveTo = props[prop.getPosX()][prop.getPosX()+1];
                if(moveTo.isPassable()){ //If it is passable
                    setPropSwitch(prop, moveTo);
                } else { //If it is not passable
                    Main.print("You bumped into a " + props[prop.getPosX()][prop.getPosX() +1].getID());
                }
            }
        }
    }
    public void addProp(Prop prop){
//        System.out.println(prop);
//        System.out.println("x: " + prop.getPosX() + " y: " + prop.getPosY());
        if(prop.getID() == ID.Player) return;
        props[prop.getPosX()][prop.getPosY()] = prop;
        setImage(prop.getPosX(),prop.getPosY(), prop.getImageID());
        if(prop.getID().equals(ID.Wall)) wallCnt++;
        else if(prop.getID().equals(ID.Person)) personCnt++;
//        update();
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
    public void update(){
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
                    if(debug) System.out.print(prop);
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
