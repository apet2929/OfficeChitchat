package sample.state;

import javafx.scene.Group;

public class EmptyState extends State {
    public EmptyState(){
        root = new Group();
    }


    @Override
    public void update(float elapsedTime) {

    }

    @Override
    public void render() {

    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }
}
