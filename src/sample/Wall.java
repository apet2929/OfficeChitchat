package sample;

public class Wall extends Prop{

    public Wall(Sprite sprite, int x, int y) {
        super(ID.Wall, false, sprite, x, y,50,50);
        setLayoutX(x*Main.scale);
        setLayoutY(y*Main.scale);
    }
}
