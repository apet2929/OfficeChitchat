package sample.state;

import javafx.scene.Group;
import javafx.scene.Scene;

public abstract class State {
    protected Group root;
    public abstract void update(float elapsedTime);
    public abstract void render();
    public abstract void onEnter();
    public abstract void onExit();
}
