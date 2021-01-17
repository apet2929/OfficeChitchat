package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.CacheHint;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BasicPerson extends Prop{

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    };
    public enum Status {
        Default,
        Looking,
        Patrolling,
        Cautious
    };

    public Direction direction;
    public int range;
    public Status status;
    public boolean moves;

    public BasicPerson( int x, int y, int width, int height, Floor floor) {
        super(ID.Person, true, x, y, width, height, "Your coworker and co-sufferer", Main.PLAYER_UP_SRC, floor);
        this.direction = Direction.Up;
        this.range = 3;
        this.status = Status.Patrolling;
        moves = false;
    }

    public BasicPerson(int x, int y, Direction direction, Floor floor){
        super(ID.Person, true, x, y, Main.scale, Main.scale, "Your coworker and co-sufferer", Main.PLAYER_UP_SRC, floor);
        this.direction = direction;
        this.range = 3;
        this.status = Status.Patrolling;
    }



    @Override
    public void tick(Floor floor) {
        super.tick(floor);
        //Stealth
        switch (direction) {
            case Up -> {
                for(int i = 1; i <= range; i++){
                    if(checkSight(floor.getProp(getPosX(), getPosY()-i))){
                        Main.print("I see you!");
                        Player player = floor.getPlayer();
                        player.spotted();
//                        ImageView imageView = floor.propImageViews[player.getPosX()][player.getPosY()];
//                        imageView.setClip(new ImageView(imageView.getImage()));
//                        ColorAdjust monochrome = new ColorAdjust();
//                        monochrome.setSaturation(-1.0);
//                        Blend blush = new Blend(
//                                BlendMode.MULTIPLY,
//                                monochrome,
//                                new ColorInput(
//                                        0,
//                                        0,
//                                        imageView.getImage().getWidth(),
//                                        imageView.getImage().getHeight(),
//                                        Color.RED
//                                )
//                        );
//                        imageView.setEffect(blush);
                    } else{
                        Player player = floor.getPlayer();
                        ImageView imageView = floor.propImageViews[player.getPosX()][player.getPosY()];
                        imageView.setEffect(null);
                    }
                }
            }
            case Down -> {
                for(int i = 1; i <= range; i++){
                    if(floor.props[getPosX()][getPosY()+i] != null) {
                        Main.print("I see you!");

                    }
                }
            }
            case Left -> {
                for(int i = 1; i <= range; i++){
                    if(floor.props[getPosX()-i][getPosY()] != null){
                        Main.print("I see you!");

                    }
                }
            }
            case Right -> {
                for(int i = 1; i <= range; i++){
                    if(floor.props[getPosX()+i][getPosY()] != null){
                        Main.print("I see you!");
                    }
                }
            }
        }

    }
    public boolean checkSight(Prop prop){
        if(prop == null) return false;
        if(prop.getID() != ID.Player) return false;
        switch (direction) {
            case Up -> {
                return prop.getPosX() == getPosX() && isWithinRange(prop.getPosY(), getPosY() - 1, getPosY() - range);
            }
            case Down -> {
                return prop.getPosX() == getPosX() && isWithinRange(prop.getPosY(), getPosY() + 1, getPosY() + range);
            }
            case Left -> {
                return prop.getPosY() == getPosY() && isWithinRange(prop.getPosX(), getPosX() - 1, getPosX() - range);
            }
            case Right -> {
                return prop.getPosY() == getPosY() && isWithinRange(prop.getPosX(), getPosX() + 1, getPosX() + range);
            }
            default -> {
                return false;
            }
        }
    }

    public static void setColorFilter(ImageView imageView){

        imageView.setClip(new ImageView(imageView.getImage()));
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);

        Blend blush = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        0,
                        0,
                        imageView.getImage().getWidth(),
                        imageView.getImage().getHeight(),
                        Color.RED
                )
        );

        imageView.effectProperty().setValue(blush);

    }


    public boolean isWithinRange(int value, int min, int max){
        return (value > max || value < min);
    }
}
