package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class Main extends Application {
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 1000;
//    private Rectangle rect;
    private Floor floor = new Floor();
    public static final int scale = 50;
    private Player player;
    private GameStateManager gameStateManager;
    private Scene game;
    private Scene menu;
    private Scene cutscene;
    //Sidebar
    private static TextArea output = new TextArea();

    //Cutscenes
    private static final int NUMCUTSCENES = 1;
    private CutsceneManager cutsceneManager;
    private Cutscene testCutscene;
    private Text cutsceneText;
    private ImageView cutSceneImage;

    //Editor
    private Prop selectedProp;

    //Sprite sources


    //Loading level
    private LevelBuilder levelBuilder;
    private File saveFile;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Group gameRoot = new Group();
        Group menuRoot = new Group();
        Group cutsceneRoot = new Group();
        game = new Scene(gameRoot, WIDTH, HEIGHT);
        menu = new Scene(menuRoot, WIDTH, HEIGHT);
        cutscene = new Scene(cutsceneRoot, WIDTH, HEIGHT);
        gameStateManager = new GameStateManager(primaryStage, game,menu);
        gameStateManager.setCurGameState(GameStateManager.GameState.MAINMENU);

        saveFile = new File("save.fun");
        generateMainMenu(menuRoot);

        primaryStage.setScene(menu);

        gameRoot.getChildren().add(floor);
        gameRoot.getChildren().add(output);

        menu.setOnKeyPressed(this::handleInput);
        menu.setOnMouseClicked(this::handleInput);
        game.setOnKeyPressed(this::handleInput);
        game.setOnMouseClicked(this::handleInput);


        //Cutscene testing
//        String[] texts = {"This is test #1","This is test #2","This is test #3","This is test #4"};
//        int[] flags = {2,4};
//        Image[] images = {new Image(genImages("wall")), new Image(genImages("player_up"))};
//        testCutscene = new Cutscene(images, texts, flags);
//        cutsceneManager = new CutsceneManager(testCutscene);
//        generateCutsceneLayout(cutsceneRoot);
//        cutscene.setOnMouseClicked(e -> handleInput(e));
//        primaryStage.setScene(cutscene);


        primaryStage.show();
    }

    public void generateGame(){
//                try{
//            levelBuilder = (LevelBuilder) SaveManager.load("test2.fun");
//            int[] walls = levelBuilder.objects.get("Walls");
//            int[] people = levelBuilder.objects.get("People");
//            int[] player = levelBuilder.objects.get("Player");
//            if(walls != null) {
//                for (int i = 0; i < walls.length - 1; i += 2) floor.addProp(new Wall(walls[i], walls[i + 1]));
//            } else System.out.println("Walls is null");
//            for(int i = 0; i < people.length-1; i+=2)floor.addProp(generateProp(ID.Person, people[i], people[i+1]));
//            this.player = new Player(player[0], player[1], floor, Player.Status.Default);
//            floor.addProp(this.player);
//            }
//        catch (Exception e){
//            System.out.println("Couldn't load: " + e.getMessage());
//
//            e.printStackTrace();
//            player = new Player(1, 1, this.floor, Player.Status.Default);
//            floor.addProp(player);
//            floor.addProp(new BasicPerson( 10,10,50,50, PLAYER_SRC, floor));
//            Wall wall = new Wall( 5,5);
//            floor.addProp(wall);
//        }
        player = new Player(1, 1, this.floor, Player.Status.Default);
        floor.addProp(player);
        floor.addProp(new BasicPerson( 10,10,50,50, floor));
        Wall wall = new Wall( 5,5);
        floor.addProp(wall);
        floor.update();
    }

    private void handleInput(MouseEvent e){
//        System.out.println("yes");
        switch(gameStateManager.getCurGameState()){
            case MAINMENU -> {
                handleMenu(e);
            }
            case CUTSCENE -> {
                handleCutscene(e);
            }
            case EDITOR -> {
                handleEditor(e);
            }

        }
    } //Mouse Input
    private void handleInput(KeyEvent e) {
        switch(gameStateManager.getCurGameState()){
            case GAME -> handleMovement(e);
            case MAINMENU -> handleMenu(e);
            case EDITOR -> handleEditor(e);
        }
        if(e.getCode() == KeyCode.R) floor.update();


    } //Keyboard Input

    private void handleEditor(MouseEvent e) {
        int x = (int) ((e.getX()/(WIDTH-300))*floor.getWidth());
        int y = (int) ((e.getY()/HEIGHT)*floor.getHeight());
        System.out.println("X :" + x + " Y: " + y);
        if(selectedProp != null){
            selectedProp = generateProp(selectedProp.getID(), x, y);
            assert selectedProp != null;
            floor.addProp(selectedProp);
            System.out.println(selectedProp);
        } else {
            System.out.println("Please select a prop using the controls");
        }
    }
    private void handleEditor(KeyEvent e){
        switch (e.getCode()){
            case A -> {
                selectedProp = Prop.clone(Prop.getEmptyProp());
            }
            case B -> {
                selectedProp = Prop.clone(Prop.WALL);
            }
            case C -> {
                selectedProp = Prop.clone(Prop.PERSON);
                BasicPerson test = (BasicPerson) selectedProp;
                test.floor = floor;
                test.updatePosition();
            }
            case D -> {
                if(player != null){
                    selectedProp = player; //Make sure player is initialized before you do this.
                    System.out.println("Make sure you only put one player down, or else the program will break");
                } else System.out.println("Make sure you initialize Player before you select him in the editor");

            }
            case ENTER -> {
                System.out.println("Name your level: ");
                Scanner input = new Scanner(System.in);
                String name = input.next();
                saveLevel(new File(name));
            }
        }
        if(selectedProp != null && e.getCode() != KeyCode.ENTER){
            System.out.println("You have selected a " + selectedProp.getID());
        }
    }

    private Prop generateProp(ID id, int x, int y){
        if(x >= 0 && x < floor.getWidth() && y >= 0 && y < floor.getHeight()) {
            switch (id) {
                case Empty:
                    return null;
                case Wall:
                    return new Wall(x, y);
                case Person:
                    return new BasicPerson(x, y, 50, 50, floor);
            }
        } else System.out.println("The X value of " + x + " or the Y value of " + y + " may be out of bounds");
        System.out.println("The ID you have selected is not a valid ID");
        return null;
    }

    private void handleCutscene(MouseEvent e) {
        if(e.getButton() == MouseButton.PRIMARY){
            cutsceneManager.update(1);
            cutsceneText.setText(cutsceneManager.getCurText());
            cutSceneImage.setImage(cutsceneManager.getCurImage());
            if(cutsceneManager.finished) gameStateManager.setCurGameState(GameStateManager.GameState.GAME);
        }
    }


    public void generateSidebar(){
        //When you click on the sidebar, the game soft locks
        output.setPrefHeight(HEIGHT-80);
        output.setPrefWidth(290);
        output.setWrapText(true);
        output.setFont(Font.font(24));
        output.setEditable(false);
        output.setFocusTraversable(false);
        output.setLayoutX(WIDTH-290);
        output.setLayoutY(0);
        output.appendText("Output area");
        output.appendText("\n");

    }

    public void generateMainMenu(Group menuRoot){
        Font buttonFont = Font.font(24);
        Text mainMenuText = new Text((WIDTH-300)/2, 50, "MAIN MENU");
        mainMenuText.setFont(Font.font(50));

        Text playText = new Text(110,130,"PLAY");

        playText.setFont(buttonFont);
        Rectangle playButton = new Rectangle(100,100,100,50);
        playButton.setFill(null);
        playButton.setStroke(Color.BLACK);
        playButton.setOnMouseClicked(this::handleMenu);

        Text exitText = new Text(110,270, "EXIT");
        exitText.setFont(buttonFont);
        Rectangle exitButton = new Rectangle(100,250,100,50);
        exitButton.setFill(null);
        exitButton.setStroke(Color.BLACK);
        exitButton.setOnMouseClicked(e -> Platform.exit());


        menuRoot.getChildren().addAll(mainMenuText, playButton, playText, exitButton, exitText);
    }

    private void generateCutsceneLayout(Group cutsceneRoot){
        Rectangle textBox = new Rectangle(0, HEIGHT-250, WIDTH, HEIGHT);
        textBox.setFill(new Color(0,0,0,0.5));
        cutsceneText = new Text(cutsceneManager.getCurText());
        cutsceneText.setLayoutX(20);
        cutsceneText.setLayoutY(HEIGHT-200);
        cutsceneText.setFont(Font.font(40));
        cutSceneImage = new ImageView(cutsceneManager.getCurImage());
        cutSceneImage.setFitHeight(HEIGHT);
        cutSceneImage.setFitWidth(WIDTH);
        cutsceneRoot.getChildren().addAll(cutSceneImage, textBox, cutsceneText);
    }

    private void handleMenu(KeyEvent e) {

        switch (e.getCode()){
            case BACK_SPACE -> {
                gameStateManager.setCurGameState(GameStateManager.GameState.GAME);
            }
            case ESCAPE -> {
                Platform.exit();
            }
        }
    }
    private void handleMenu(MouseEvent e) {
        if(e.getButton() == MouseButton.PRIMARY) {
            generateGame();
            generateSidebar();
            gameStateManager.setCurGameState(GameStateManager.GameState.GAME);

        }
    }


//    public static Cutscene getCutscene(int curCutscene) {
//        if(curCutscene <= NUMCUTSCENES) {
//            switch (curCutscene) {
//                case 0: return new Cutscene(null,null,null);
//            }
//        } else{
//            System.out.println("Cutscene number " + curCutscene + " is out of bounds of range " + NUMCUTSCENES);
//            return null;
//        }
//        return null;
//    }

    private void handleMovement(KeyEvent e) {
        switch (e.getCode()) {
            case W -> {
                if (player.getStatus() == Player.Status.Default) player.moveUp();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (floor.props[player.getPosX()][player.getPosY() - 1] == null) print("It's Empty");
                     else {
                         print(floor.props[player.getPosX()][player.getPosY() - 1].getDescription());
                         System.out.println(floor.props[player.getPosX()][player.getPosY() - 1]);
                         player.setStatus(Player.Status.Default);
                    }
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutY(player.getPosY()*scale);
            }
            case A -> {
                if (player.getStatus() == Player.Status.Default) player.moveLeft();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (floor.props[player.getPosX()-1][player.getPosY()] == null) print("It's Empty");
                    else {
                        print(floor.props[player.getPosX()-1][player.getPosY()].getDescription());
                        System.out.println(floor.props[player.getPosX()-1][player.getPosY()]);
                        player.setStatus(Player.Status.Default);
                    }
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutX(player.getPosX()*scale);
            }
            case S -> {
                if (player.getStatus() == Player.Status.Default) player.moveDown();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (floor.props[player.getPosX()][player.getPosY() + 1] == null) print("It's Empty");
                    else {
                        print(floor.props[player.getPosX()][player.getPosY() + 1].getDescription());
                        System.out.println(floor.props[player.getPosX()][player.getPosY() + 1]);
                        player.setStatus(Player.Status.Default);
                    }
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutY(player.getPosY()*scale);
            }
            case D -> {
                if (player.getStatus() == Player.Status.Default) player.moveRight();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (floor.props[player.getPosX()+1][player.getPosY()] == null) print("It's Empty");
                    else {
                        print(floor.props[player.getPosX()+1][player.getPosY()].getDescription());
                        System.out.println(floor.props[player.getPosX()+1][player.getPosY()]);
                        player.setStatus(Player.Status.Default);
                    }
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
            case E -> {
                for (int i = 0; i < floor.props[0].length; i++) {
                    System.out.println(Arrays.toString(floor.props[i]));
                }
            }
            case SHIFT -> {
                gameStateManager.setCurGameState(GameStateManager.GameState.EDITOR);
                System.out.println("You are now in the editor. Please refer to the editor guide for how to make a new floor.");
            }
            case ENTER -> {
                try {
                    System.out.println("Saving");
                    if(saveFile.delete()) {
                        saveFile.createNewFile();
                        FileOutputStream fos = new FileOutputStream(saveFile);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        player.floor = null;
                        oos.writeObject(player);
                        for(Prop[] props : floor.props) {
                            for (Prop prop : props) {
                                if (prop != null) {
                                    if(prop.getID() == ID.Person){
                                        BasicPerson person = (BasicPerson)(prop);
                                        person.floor = null;
                                        oos.writeObject(person);
                                        person.floor = this.floor;
                                    }
                                    else if (prop.getID() != ID.Player) {
                                        oos.writeObject(prop);
                                    }
                                }
                            }
                        }
                        oos.close();
                        fos.close();
                        player.floor = this.floor;

                    } else {
                        System.out.println("Couldn't delete save file");
                    }
                }
                catch (IOException i){
                    System.out.println("Failed to save state");
                    i.printStackTrace();
                }
            }
            case L -> {
                try {
                    System.out.println("Loading");
                    FileInputStream fileStream = new FileInputStream(saveFile);
                    ObjectInputStream objectStream = new ObjectInputStream(fileStream);

                    //Loading Player
                    floor.removeProp(player);
                    player = (Player) objectStream.readObject();
                    player.setFloor(floor);
                    floor.addProp(player);
                    floor.update();
                    floor.setImage(player.getPosX(), player.getPosY(), player.getImageID());

                    //Loading all other props
                    boolean cont = true;
                    while(cont){
                        if(objectStream.available() == 0){
                            cont = false;
                        }
                        else {
                            Prop temp = (Prop) (objectStream.readObject());
                            floor.addProp(temp);
                            floor.update();
                            floor.setImage(temp.getPosX(), temp.getPosY(), temp.getImageID());
                        }
                    }

                    objectStream.close();
                    fileStream.close();

//                    floor.update();
                }
                catch (IOException | ClassNotFoundException ioException) {
                    System.out.println("Failed to load state");
                    ioException.printStackTrace();
                }

            }

            default -> System.out.println("default");
        }
        System.out.println(player.getPosX() + " " + player.getPosY());
    }

    public static FileInputStream genImages(String id) { //wtf this works?
        try {
            return new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\"+id+".png");
//            return switch (id) {
//                case "player_up" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_up.png");
//                case "player_down" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_down.png");
//                case "player_left" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_left.png");
//                case "player_right" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_right.png");
//                case "wall" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\wall.png");
//                case "wall_corner" -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\wall_corner.png");
//                case
//                default -> throw new IllegalStateException("Unexpected value: " + id);
//            };
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }
    public static final int FLOOR_SRC = 0;
    public static final int WALL_SRC = 1;
    public static final int PLAYER_UP_SRC = 2;
    public static final int PLAYER_LEFT_SRC = 3;
    public static final int PLAYER_DOWN_SRC = 4;
    public static final int PLAYER_RIGHT_SRC = 5;
    public static final int WALL_CORNER_SRC = 6;

    public static FileInputStream genImages(int id){
        try{
            return switch(id){
                case 0 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\floor.png");
                case 1 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\wall.png");
                case 2 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_up.png");
                case 3 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_left.png");
                case 4 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_down.png");
                case 5 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_right.png");
                case 6 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\wall_corner.png");
                default -> throw new IllegalStateException("Unexpected value: " + id);
            };
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveLevel(File file) {
//        int[] walls = new int[floor.wallCnt * 2];
//        int b = 0;
//        int[] people = new int[floor.personCnt * 2];
//        int en = 0;
//        for(Prop[] propRay: floor.props) {
//            for (Prop prop : propRay) {
//                if (prop.getID() == ID.Wall) {
//                    walls[b] = prop.getPosX();
//                    walls[b + 1] = prop.getPosY();
//                    b += 2;
//                } else if (prop.getID() == ID.Person) {
//                    people[en] = prop.getPosX();
//                    people[en + 1] = prop.getPosY();
//                    en += 2;
//                }
//            }
//        }
//        LevelBuilder lvl = new LevelBuilder(
//                name + ".fun", walls, people,
//                //player spawn
//                new int[]{1, 1}
//        );
//
//        try{
//            SaveManager.save(lvl, lvl.name);
//        }
//        catch (Exception e){
//            System.out.println("Couldn't save: "+ e.getMessage());
//        }

    }

    public Scene getGame() {
        return game;
    }

    public Scene getMenu() {
        return menu;
    }

    public static void print(String text){
        output.appendText("\n" + text);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
