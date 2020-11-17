package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static sample.Main.genImages;

public class Floor extends Group {
//    List<Prop> props = new ArrayList<>();
    private final int width = (Main.WIDTH-300)/Main.scale;
    private final int height = Main.HEIGHT/Main.scale;
    public Prop[][] props = new Prop[width][height];


    public Floor(){
        System.out.println(props.length);
        tileFloor();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(i == 0 || j == 0 || i == width-1 || j == height-1) addProp(new Wall(new ImageView(new Image(genImages(Wall.IMAGE))), i, j));
                else {
                    addProp(Prop.getEmptyProp(i,j));
                }
            }
        }

    }

    public void tileFloor(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                ImageView floor = new ImageView(new Image(Objects.requireNonNull(Main.genImages("floor"))));
                floor.setLayoutX(i);
                floor.setLayoutY(j);
                getChildren().add(floor);
            }
        }
    }
    public void addProp(Prop prop){
//        System.out.println(prop);
//        System.out.println("x: " + prop.getPosX() + " y: " + prop.getPosY());
        props[prop.getPosX()][prop.getPosY()] = prop;
        if(prop.getSprite() != null) {
            getChildren().add(prop.getSprite());
        }
    }
    public void switchPlaces(int x1, int y1, int x2, int y2){
        Prop temp = props[x1][y1];
        props[x1][y1] = props[x2][y2];
        props[x2][y2] = temp;
        props[x2][y2].setXY(x2,y2);
        props[x1][y1].setXY(x1,y1);
        props[x2][y2].updateSprite();
        props[x1][y1].updateSprite();
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
