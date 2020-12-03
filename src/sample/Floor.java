package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
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
    public int wallCnt = 0;
    public int personCnt = 0;
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
            getChildren().addAll(floor[i]);
            getChildren().addAll(propImageViews[i]);
        }
    }

    public void setPropXYSwitch(int x1, int y1, int x2, int y2){
        props[x1][y1].setXY(x2,y2);
        props[x2][y2] = props[x1][y1];
        props[x2][y2].setXY(x1,y1);
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
    public void addProp(Prop prop){
//        System.out.println(prop);
//        System.out.println("x: " + prop.getPosX() + " y: " + prop.getPosY());
        props[prop.getPosX()][prop.getPosY()] = prop;
        if(prop.getID().equals(ID.Wall)) wallCnt++;
        else if(prop.getID().equals(ID.Person)) personCnt++;
        update();
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
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Prop prop = props[i][j];
                if (prop != null) {
                    propImageViews[i][j].setImage(new Image(Objects.requireNonNull(genImages(prop.getImageID()))));
//                    else{
//                        imageViews[i][j].setImage(Main.FLOOR_SRC);
//                    }
//                    switch (prop.getID()) {
//                        case Wall: {
//                            imageViews[i][j].setImage(Main.WALL_SRC);
//                            System.out.println(prop);
//                        }
//                        case Empty:
//                            imageViews[i][j].setImage(Main.FLOOR_SRC);
//                        case Person:
//                            imageViews[i][j].setImage(Main.PLAYER_SRC);
//                        default: {
//                            imageViews[i][j].setImage(Main.FLOOR_SRC);
//                            System.out.println(prop + " default");
//                        }
//                    }
                    if(debug) System.out.print(prop);
                } else {
                    propImageViews[i][j].setImage(null);
                }
            }
            if(debug) System.out.println();
        }
        System.out.println("Updating");
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
