package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Arrays;


import static sample.Main.FLOOR_SRC;


public class Floor extends Group {

    private final int width = (Main.WIDTH-300)/Main.scale;
    private final int height = Main.HEIGHT/Main.scale;
    public Prop[][] props = new Prop[width][height];
    ImageView[][] imageViews = new ImageView[width][height];
    private boolean debug = false;
    public Image fillTile;
    public int wallCnt = 0;
    public int personCnt = 0;
    public Floor(){
        System.out.println(props.length);
        fillTile = FLOOR_SRC;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                imageViews[i][j] = new ImageView();
                imageViews[i][j].setLayoutX(i*Main.scale);
                imageViews[i][j].setLayoutY(j*Main.scale);
            }
            getChildren().addAll(imageViews[i]);
        }
        fillEmptyTile();
    }

    public void fillEmptyTile(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
//                if(i == 0 || j == 0 || i == width-1 || j == height-1) addProp(new Wall(i, j));
//                else {
//
//                }
                Prop floor = Prop.clone(Prop.FLOOR);
                setPropXYNoSwitch(floor,i,j);
                addProp(floor);
                tileFloor();
            }
        }
    }

    public void setPropXYSwitch(int x1, int y1, int x2, int y2){
        props[x1][y1].setXY(x2,y2);
        props[x2][y2] = props[x1][y1];
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
                imageViews[i][j].setImage(fillTile);
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
                    if(prop.getID().equals(ID.Wall)){
                        imageViews[i][j].setImage(Main.WALL_SRC);
//                        System.out.println(prop);
                    }
                    if(prop.getID().equals(ID.Empty)){
                        imageViews[i][j].setImage(FLOOR_SRC);
//                        System.out.println(prop);
                    }
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
