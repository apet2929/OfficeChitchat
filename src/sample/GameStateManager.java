package sample;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameStateManager {
    private GameState curGameState = GameState.MAINMENU;
    private Stage primaryStage;
    private Scene game;
    private Scene menu;
    public enum GameState{
        MAINMENU,
        GAME,
        EDITOR,
        CREDITS,
        BATTLE
    }
    public GameStateManager(Stage primaryStage, Scene game, Scene menu){
        this.primaryStage = primaryStage;
        this.game = game;
        this.menu = menu;
    }

    public GameState getCurGameState() {
        return curGameState;
    }

    public void setCurGameState(GameState curGameState) {
        this.curGameState = curGameState;
        switch (curGameState){
            case MAINMENU: primaryStage.setScene(menu);
            case GAME: primaryStage.setScene(game);
        }
    }
}
