package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.state.EmptyState;
import sample.state.GameState;
import sample.state.MainMenuState;
import sample.state.StateMachine;

import static javafx.application.Application.launch;

public class Main extends Application {

    public Group root = new Group();
    private Scene scene = new Scene(new Group());
    private StateMachine gameState = new StateMachine(scene);

    private InputHandler inputHandler;

    long lastNanoTime = System.nanoTime();

    public static double WIDTH;
    public static double HEIGHT;

    @Override
    public void start(Stage stage) {
        stage.setMaximized(true);
        Screen screen = Screen.getPrimary();
        WIDTH = screen.getBounds().getWidth();
        HEIGHT = screen.getBounds().getHeight();

        setupStates();

        System.out.println(WIDTH + " " +  HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Test");

        inputHandler = new InputHandler(scene);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ESCAPE) {
                stage.close();
            }
        });

        //Game loop
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                update(l);
            }
        }.start();

        stage.show();
    }

    private void setupStates(){
        gameState.add("MainMenu", new MainMenuState(gameState));
        gameState.add("Game", new GameState(inputHandler));
        gameState.change("MainMenu");
    }

    public void update(long currentNanoTime){
        double t = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        if(inputHandler.getInput().contains("SPACE")){
            gameState.change("Game");
        }

        gameState.update((float) t);
        gameState.render();

    }

    public static void main(String[] args){
        launch(args);
    }

}
