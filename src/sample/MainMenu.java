package sample;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import sample.state.StateMachine;

public class MainMenu extends Group {
    private StateMachine stateMachine;
    public MainMenu(StateMachine stateMachine) {
        super();
        this.stateMachine = stateMachine;
    }
    public MainMenu setup(){
        Button text = new Button("Main Menu");
        text.setFont(Font.font(24));
        text.setOnMouseClicked(e -> {
            stateMachine.change("Game");
        });
        getChildren().add(text);
        return this;
    }

    public void clear(){
        getChildren().removeAll();
    }
}
