package sample;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Floor extends Group {
//    List<Prop> props = new ArrayList<>();
    public Prop[][] props = new Prop[(Main.WIDTH/Main.scale)][(Main.HEIGHT/Main.scale)];
    private final int width = Main.WIDTH/Main.scale;
    private final int height = Main.HEIGHT/Main.scale;
    public Floor(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                props[i][j] = Prop.getEmptyProp(i,j);
                getChildren().add(props[i][j]);
            }
        }
    }

    public void addProp(Prop prop){
        System.out.println(prop);
        props[prop.getPosX()][prop.getPosY()] = prop;
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
