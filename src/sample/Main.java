package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main extends Application {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    private Rectangle rect;
    private Floor floor = new Floor();
    public static final int scale = 50;
    private Player player;
    private ImageManager imageManager;
    private Scene game;
    //Sidebar
    private static TextArea output = new TextArea();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        game = new Scene(root, WIDTH + 300, HEIGHT);
        primaryStage.setScene(game);
        root.getChildren().add(floor);
        root.getChildren().add(output);

        generateGame();
        generateSidebar();

        primaryStage.show();
    }

    public void generateGame() throws FileNotFoundException {
        List<Line> lineList = new ArrayList<>();
        for(int i = 0; i < WIDTH/scale; i++){
            lineList.add(new Line(i*scale, 0, i*scale, HEIGHT));
        }
        for(int j = 0; j < HEIGHT / scale; j++){
            lineList.add(new Line(0, j*scale, WIDTH, j*scale));
        }
        floor.getChildren().addAll(lineList);

        imageManager = new ImageManager();
        player = new Player(new ImageView(new Image(imageManager.playerUp)), 0, 0, this.floor, Player.Status.Default);
        floor.addProp(player);

        Wall wall = new Wall(new ImageView(new Image(genImages(Wall.IMAGE))), 5,5);
        floor.addProp(wall);

        game.setOnKeyPressed(this::handleInput);
    }

    public void generateSidebar(){
        output.setPrefHeight(1000-80);
        output.setFont(Font.font(24));
        output.setEditable(false);
        output.setFocusTraversable(false);
        output.setLayoutX(1000);
        output.setLayoutY(0);
        output.appendText("Output area");
        output.appendText("\n");

    }

    private void handleInput(KeyEvent e) {

        switch (e.getCode()) {
            case W -> {
                if(player.getStatus() == Player.Status.Default) player.moveUp();
                else if(player.getStatus() == Player.Status.Looking){
                    print(floor.props[player.getPosX()][player.getPosY()-1].getDescription());
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutY(player.getPosY()*scale);
            }
            case A -> {
                if(player.getStatus() == Player.Status.Default) player.moveLeft();
                else if(player.getStatus() == Player.Status.Looking) {
                    print(floor.props[player.getPosX()-1][player.getPosY()].getDescription());
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutX(player.getPosX()*scale);
            }
            case S -> {
                if(player.getStatus() == Player.Status.Default) player.moveDown();
                else if(player.getStatus() == Player.Status.Looking) {
                    print(floor.props[player.getPosX()][player.getPosY()+1].getDescription());
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutY(player.getPosY()*scale);

            }
            case D -> {
                if(player.getStatus() == Player.Status.Default) player.moveRight();
                else if(player.getStatus() == Player.Status.Looking) {
                    print(floor.props[player.getPosX()+1][player.getPosY()].getDescription());
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutX(player.getPosX()*scale);
            }
            case SPACE -> {
//                for(int i = 0; i < floor.props[0].length; i++){
//                    System.out.println(Arrays.toString(floor.props[i]));
//                }
                print("Select a direction");
                player.setStatus(Player.Status.Looking);

            }
            default -> System.out.println("default");
        }
    }
    public static FileInputStream genImages(String id) { //wtf this works?
        try {
            return switch (id) {
                case "player_up" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_up.png");
                case "player_down" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_down.png");
                case "player_left" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_left.png");
                case "player_right" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_right.png");
                case "wall" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\wall.png");
                default -> throw new IllegalStateException("Unexpected value: " + id);
            };
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }
    public static void print(String text){
        output.appendText("\n" + text);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
