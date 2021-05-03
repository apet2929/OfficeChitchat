package sample.state;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.ImageHandler;
import sample.Main;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StateMachine {
    public HashMap<String, State> statesMap;
    public State currentState;
    private Group root = new Group();
    private Scene scene;

    public StateMachine(Scene scene){
        this.scene = scene;
        statesMap = new HashMap<>();
        currentState = new EmptyState();
        setDimensions();
    }

    public void update(float elapsedTime){
        currentState.update(elapsedTime);
    }

    public void render(){
        currentState.render();
    }

    public void change(String stateName){
        if(currentState != statesMap.get(stateName)) {
            System.out.println("State Change to: " + stateName);
            currentState.onExit();
            currentState = statesMap.get(stateName);
            this.root = currentState.root;
            setDimensions();
            scene.setRoot(root);
            currentState.onEnter();
        }
    }

    public void setDimensions(){
        this.root.prefWidth(Main.WIDTH);
        this.root.prefHeight(Main.HEIGHT);
    }

    public void add(String stateName, State state){
        statesMap.put(stateName, state);
    }



}
