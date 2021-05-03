package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
import javafx.stage.Stage;
import sample.state.EmptyState;
import sample.state.MainMenuState;
import sample.state.StateMachine;

import static javafx.application.Application.launch;

public class Main extends Application {

    public Group root = new Group();
    private Scene scene = new Scene(root);
    private StateMachine gameState = new StateMachine(root, scene);
    private long startNanoTime;
    private GraphicsContext g;

    private InputHandler inputHandler;
    private SpriteHandler spriteHandler;
    private TileRenderer tileRenderer = new TileRenderer();

    int[] testMap = {0, 0, 1, 1, 2, 2};
    long lastNanoTime = System.nanoTime();

    Sprite testSprite;
    @Override
    public void start(Stage stage) throws Exception {
        root.prefWidth(400);
        root.prefHeight(400);
        setupStates();


        stage.setScene(scene);
        stage.setTitle("Test");
        Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);
        g = canvas.getGraphicsContext2D();

        inputHandler = new InputHandler(scene);
        spriteHandler = new SpriteHandler();

        testSprite = new Sprite(tileRenderer.getImage(3), 0, 0, 32, 32);
        spriteHandler.add(testSprite);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ESCAPE) {
                stage.close();
            }
        });


        //Game loop
        startNanoTime = System.nanoTime();
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
        gameState.add("Game", new EmptyState());
        gameState.change("MainMenu");
    }

    public void update(long currentNanoTime){
        double t = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;
        g.clearRect(0,0, 400, 400);


        if(inputHandler.getInput().contains("A")){
            testSprite.setVelX(-250);
        }

        if(inputHandler.getInput().contains("D")){
            testSprite.setVelX(250);
        }
        if(inputHandler.getInput().contains("W")){
            testSprite.setVelY(-250);
        }
        if(inputHandler.getInput().contains("S")){
            testSprite.setVelY(250);
        }
        if(inputHandler.getInput().contains("SPACE")){
            gameState.change("Game");
        }

        gameState.update((float) t);
        //Don't change this order, this order is very important. Otherwise tiles are 1 frame out of sync with sprites
        spriteHandler.update(t);
        tileRenderer.renderMap(testMap, 2, (int)testSprite.getX(), (int)testSprite.getY(), g);
        spriteHandler.render(g);
    }

    public static void main(String[] args){
        launch(args);
    }

}
