package sample.state;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.*;

public class GameState extends EmptyState{
    private Canvas canvas;
    private GraphicsContext g;
    private SpriteHandler spriteHandler;
    private TileRenderer tileRenderer;
    private InputHandler inputHandler;

    private int[] currentMap;

    private Sprite testSprite;

    public GameState(InputHandler inputHandler){
        this.inputHandler = inputHandler;
        root = setup();
    }

    public Group setup() {
        Group root = new Group();
        canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
        g = canvas.getGraphicsContext2D();
        spriteHandler = new SpriteHandler();
        tileRenderer = new TileRenderer();
        root.getChildren().add(canvas);

        testSprite = new Sprite(ImageHandler.images[3], 0, 0, 32, 32);
        currentMap = new int[]{
            0, 0,
            1, 1,
            2, 2
        };

        return root;
    }

    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);

        spriteHandler.update(elapsedTime);

//        g.clearRect(0,0, 400, 400);

//        if(inputHandler.getInput().contains("A")){
//            testSprite.setVelX(-250);
//        }
//        if(inputHandler.getInput().contains("D")){
//            testSprite.setVelX(250);
//        }
//        if(inputHandler.getInput().contains("W")){
//            testSprite.setVelY(-250);
//        }
//        if(inputHandler.getInput().contains("S")){
//            testSprite.setVelY(250);
//        }

    }

    @Override
    public void render() {
        super.render();
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        tileRenderer.renderMap(currentMap, 2, 0, 0, g);
        spriteHandler.render(g);

        g.strokeText("Game", 100, 100);
    }

    @Override
    public void onEnter() {
        super.onEnter();
//        g.setStroke(Color.BLUEVIOLET);
//        g.strokeRect(0,0, Main.WIDTH-50,Main.HEIGHT-50);
    }

    @Override
    public void onExit()
    {
        super.onExit();
    }
}
