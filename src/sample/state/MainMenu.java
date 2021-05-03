package sample.state;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.ImageHandler;
import sample.Main;

import java.awt.*;

public class MainMenu extends Group {
    private StateMachine stateMachine;
    public MainMenu(StateMachine stateMachine) {
        super();
        this.stateMachine = stateMachine;
    }

    public MainMenu setup(){
        Text text = new Text("Main Menu");
        text.setFont(Font.font(200));
        text.setFill(Color.BLACK);
        System.out.println(Main.HEIGHT);
        text.setY(Main.HEIGHT/2);

        text.setOnMouseClicked(e -> {
            System.out.println("True");
            stateMachine.change("Game");
            this.clear();
        });

        getChildren().add(new ImageView(ImageHandler.images[4]));

        getChildren().add(text);

        return this;
    }

    public void clear(){
        getChildren().removeAll();
    }
}
