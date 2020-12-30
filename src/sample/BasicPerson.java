package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicPerson extends Prop{

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    };
    public Direction direction;

    public BasicPerson( int x, int y, int width, int height, Floor floor) {
        super(ID.Person, true, x, y, width, height, "Your coworker and co-sufferer", Main.PLAYER_UP_SRC, floor);
        this.direction = Direction.Up;
    }

    public BasicPerson(int x, int y, Direction direction, Floor floor){
        super(ID.Person, true, x, y, Main.scale, Main.scale, "Your coworker and co-sufferer", Main.PLAYER_UP_SRC, floor);
        this.direction = direction;
    }

}
