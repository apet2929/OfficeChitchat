package sample.state;

import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.MainMenu;

public class MainMenuState extends EmptyState{

    public MainMenuState(StateMachine stateMachine){
        root = new MainMenu(stateMachine).setup();
    }

    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);
    }

    @Override
    public void render() {

        super.render();
    }

    @Override
    public void onEnter() {
        super.onEnter();

    }

    @Override
    public void onExit()
    {
        super.onExit();
    }
}
