package sample.state;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StateMachine {
    public HashMap<String, State> statesMap;
    public State currentState;
    private Group root;
    private Scene scene;

    public StateMachine(Group root, Scene scene){
        statesMap = new HashMap<>();
        currentState = new EmptyState();
        this.root = root;
        this.scene = scene;
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
            scene.setRoot(currentState.root);
            currentState.onEnter();
        }
    }
    public void add(String stateName, State state){
        statesMap.put(stateName, state);
    }

}
