package sample;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameStateManager {
    private GameState curGameState = GameState.MAINMENU;
    private Stage primaryStage;
    private Scene game;
    private Scene menu;
    private Scene gameOver;
    private Main main;
    public enum GameState{
        MAINMENU,
        GAME,
        EDITOR,
        CREDITS,
        CUTSCENE,
        GAMEOVER,
        BATTLE
    }
    public GameStateManager(Stage primaryStage, Scene game, Scene menu, Scene gameOver, Main main){
        this.primaryStage = primaryStage;
        this.game = game;
        this.menu = menu;
        this.gameOver = gameOver;
        this.main = main;
    }

    public void goUp(){
        main.goUp();
    }
    public void goDown(){
        main.goDown();
    }

    public GameState getCurGameState() {
        return curGameState;
    }

    public void setCurGameState(GameState curGameState) {
        System.out.println("Switching curGameState to "+ curGameState);
        this.curGameState = curGameState;
        switch (curGameState) {
            case MAINMENU -> {
                primaryStage.setScene(menu);
            }
            case GAME, EDITOR -> {
                primaryStage.setScene(game);
            }
            case GAMEOVER -> {
                Main.output.clear();
                primaryStage.setScene(gameOver);
            }
        }
    }
}
