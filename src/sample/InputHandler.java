package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class InputHandler {
    private ArrayList<String> input = new ArrayList<>();
    public InputHandler(Scene scene){
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        String code = keyEvent.getCode().toString();
                        if(!input.contains(code))
                            input.add(code);
                    }
                }
        );
        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        String code = keyEvent.getCode().toString();
                        input.remove(code);
                    }
                }
        );
    }

    public ArrayList<String> getInput() {
        return input;
    }

}
