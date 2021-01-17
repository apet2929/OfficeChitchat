package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.effect.*;
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
import java.util.*;

import static sample.Floor.UP_DIRECTION;


public class Main extends Application {
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 1000;
    public static final int scale = 50;

    //Floors
    private static Floor[] floors;
    private Floor currentFloor;


    //Game
    private Player player;
    private static GameStateManager gameStateManager;
    private Scene game;
    private Group gameRoot;
    private Scene menu;
    private Scene cutscene;
    private Scene gameOver;
    public static TextArea output = new TextArea();

    //Cutscenes
    private static final int NUMCUTSCENES = 1;
    private CutsceneManager cutsceneManager;
    private Cutscene testCutscene;
    private Text cutsceneText;
    private ImageView cutSceneImage;

    //Editor
    private ID selectedProp;

    //Debug
    public static final boolean debug = false;

    //Loading level
    private LevelBuilder levelBuilder;
    private File saveFile;

    //Pathfinding
    Pathfinding pathfinding;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Creating groups and scenes, setup
        Group gameRoot = new Group();
        Group menuRoot = new Group();
        Group cutsceneRoot = new Group();
        Group gameOverRoot = new Group();
        game = new Scene(gameRoot, WIDTH, HEIGHT);
        menu = new Scene(menuRoot, WIDTH, HEIGHT);
        cutscene = new Scene(cutsceneRoot, WIDTH, HEIGHT);
        gameOver = new Scene(gameOverRoot, WIDTH, HEIGHT);
        floors = new Floor[3];
        currentFloor = new Floor(0);
        floors[0] = currentFloor;

        //Setting gamestate
        gameStateManager = new GameStateManager(primaryStage, game,menu, gameOver, this);
        gameStateManager.setCurGameState(GameStateManager.GameState.MAINMENU);

        //Setting up save file
        saveFile = new File("save.fun");
        //Generating Layouts
        generateMainMenu(menuRoot);
        generateGameOver(gameOverRoot);

        //Setting the scene
        primaryStage.setScene(menu);

        //Setting up game
        gameRoot.getChildren().add(currentFloor);
        gameRoot.getChildren().add(output);

        //Pathfinding
        pathfinding = new Pathfinding(currentFloor);

        //Handling input
        menu.setOnKeyPressed(this::handleInput);
        menu.setOnMouseClicked(this::handleInput);
        game.setOnKeyPressed(this::handleInput);
        game.setOnMouseClicked(this::handleInput);
        gameOver.setOnMouseClicked(this::handleInput);


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

//    @Override
//    public void start(Stage stage) throws Exception {
//        Image image = new Image(
//                "http://icons.iconarchive.com/icons/designbolts/smurfs-movie/128/smurfette-icon.png"
//        );
//
//        ImageView imageView = new ImageView(image);
//        imageView.setClip(new ImageView(image));
//
//        ColorAdjust monochrome = new ColorAdjust();
//        monochrome.setSaturation(-1.0);
//
//        Blend blush = new Blend(
//                BlendMode.MULTIPLY,
//                monochrome,
//                new ColorInput(
//                        0,
//                        0,
//                        imageView.getImage().getWidth(),
//                        imageView.getImage().getHeight(),
//                        Color.RED
//                )
//        );
//
//        imageView.effectProperty().bind(
//                Bindings
//                        .when(imageView.hoverProperty())
//                        .then((Effect) blush)
//                        .otherwise((Effect) null)
//        );
//
//        imageView.setCache(true);
//        imageView.setCacheHint(CacheHint.SPEED);
//
//        stage.setScene(new Scene(new Group(imageView), Color.AQUA));
//        stage.show();
//    }


    public static void preLoadFloor(int lvl){
        Floor flr = loadFloor(lvl);
        if(flr != null) {
//            floors.add(flr);
        }
    }
    public static void addFloor(Floor floor, int lvl){
        System.out.println("Adding floor");
        if(lvl > 0 && lvl < floors.length) {
            floors[lvl] = floor;
        } else {
            System.out.println("The level of " + lvl + " is out of bounds");
        }
    }

    public void generateGame(){
        loadGame(1);
        new Entrance(player.getPosX(), player.getPosY()-1, currentFloor, 2, true);
    }

    public void loadGame(File file){ //1st is player, 2nd is lvl, 3rd is number of props
//        try {
//            System.out.println("Loading " + file.getName());
//            FileInputStream fileStream = new FileInputStream(file);
//            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
//
//            currentFloor.clear();
//
//            //Loading Player
//            player = (Player) objectStream.readObject();
//            player.floor = currentFloor;
//            player.gameStateManager = gameStateManager;
//            currentFloor.addProp(player);
//            currentFloor.lvl = (Integer)objectStream.readObject();
//
//            currentFloor.setImage(player.getPosX(), player.getPosY(), player.getImageID());
//
//            //Loading all other props
//            Integer numProps = (Integer) (objectStream.readObject());
//            System.out.println("Number of props to be loaded: " + numProps);
//            for (int i = 0; i < numProps; i++) {
//                Prop temp = (Prop) (objectStream.readObject()); //We need to do this because we cannot serialize floors. Maybe fix this later?
//                System.out.print(temp);
//                temp.setFloor(currentFloor);
//            }
//
//            objectStream.close();
//            fileStream.close();
////            currentFloor = new Floor(0);
//        }
//        catch (IOException | ClassNotFoundException | ClassCastException exception) {
//            System.out.println("Failed to load state");
//            exception.printStackTrace();
            player = new Player(1, 12, this.currentFloor, Player.Status.Default, gameStateManager);
            currentFloor.addProp(player);
            currentFloor.addProp(new BasicPerson( 10,10,scale,scale, currentFloor));
            Wall wall = new Wall( 5,5, currentFloor);
            currentFloor.addProp(wall);
            Spike spike = new Spike(2,2, currentFloor);
            currentFloor.update();
//        }
    }

    public void loadGame(int lvl){
        File file = new File("floor" + lvl + ".fun");
        if(file.exists()){
            loadGame(file);
        } else {
            System.out.println("The file to be loaded doesn't exist");
        }
    }

    public void saveGame(int lvl){
        saveGame(new File("floor" + lvl + ".fun"));
    }

    public void saveGame(File file){ //1st is player, 2nd is lvl, 3rd is number of props,
        try {
            System.out.println("Saving");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            player.floor = null;
            player.gameStateManager = null;
            oos.writeObject(player);
            oos.writeObject(currentFloor.lvl);
            Integer numProps = -1;
            for(Prop[] props : currentFloor.props) {
                for (Prop prop : props) {
                    if (prop.getID() != ID.Empty) {
                        numProps++;
                    }
                }
            }
            System.out.println("Number of props saved: " + numProps);
            oos.writeObject(numProps);

            for(Prop[] props : currentFloor.props) {
                for (Prop prop : props) {
                    if (prop.getID() != ID.Empty && prop.getID() != ID.Player) {
                        prop.floor = null;
                        oos.writeObject(prop);
                    }
                }
            }
            oos.close();
            fos.close();
            gameStateManager.setCurGameState(GameStateManager.GameState.GAME);
            player.floor = this.currentFloor;
            player.gameStateManager = gameStateManager;

        }
        catch (IOException i){
            System.out.println("Failed to save state");
            i.printStackTrace();
        }
    }
    public void oldLoadGame(){
        try{
            String floorName = "test2.fun";
            levelBuilder = (LevelBuilder) SaveManager.load(floorName);
            int[] walls = levelBuilder.objects.get("Walls");
            int[] people = levelBuilder.objects.get("People");
            int[] spikes = levelBuilder.objects.get("Spikes");
            int[] player = levelBuilder.objects.get("Player");
            if(walls != null) {
                for (int i = 0; i < walls.length - 1; i += 2) currentFloor.addProp(new Wall(walls[i], walls[i + 1], currentFloor));
            } else System.out.println("Walls is null");
            if(people != null) {
                for (int i = 0; i < people.length - 1; i += 2)
                    currentFloor.addProp(generateProp(ID.Person, people[i], people[i + 1]));
            } else System.out.println("People is null");
            if(spikes != null) {
                for (int i = 0; i < spikes.length - 1; i += 2)
                    currentFloor.addProp(generateProp(ID.Spike, spikes[i], spikes[i + 1]));
            } else System.out.println("People is null");
            if(player != null) {
                this.player = new Player(player[0], player[1], currentFloor, Player.Status.Default, gameStateManager);
                currentFloor.addProp(this.player);
            } else System.out.println("Player is null");

        }
        catch (Exception e){
            System.out.println("Couldn't load: " + e.getMessage());

            e.printStackTrace();
            player = new Player(1, 1, this.currentFloor, Player.Status.Default, gameStateManager);
            currentFloor.addProp(player);
            currentFloor.addProp(new BasicPerson( 10,10,50,50, currentFloor));
            Wall wall = new Wall( 5,5, currentFloor);
            currentFloor.addProp(wall);
        }
        currentFloor.update();
    }

    private void handleInput(MouseEvent e){
//        System.out.println("yes");
        System.out.println("Handling input. Current game state is " + gameStateManager.getCurGameState());
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
            case GAMEOVER -> {
                handleMenu(e);
            }

        }
    } //Mouse Input
    private void handleInput(KeyEvent e) {
        switch(gameStateManager.getCurGameState()){
            case GAME -> handleMovement(e);
            case MAINMENU -> handleMenu(e);
            case EDITOR -> handleEditor(e);
            case GAMEOVER -> handleMenu(e);
        }
        if(e.getCode() == KeyCode.R) currentFloor.update();


    } //Keyboard Input

    private void handleEditor(MouseEvent e) {
        int x = (int) ((e.getX()/(WIDTH-300))* currentFloor.getWidth());
        int y = (int) ((e.getY()/HEIGHT)* currentFloor.getHeight());
        System.out.println("X :" + x + " Y: " + y);
        if(selectedProp != null){
            if(selectedProp == ID.Empty){
                currentFloor.removeProp(currentFloor.props[x][y]);
                currentFloor.update();
            } else {
                Prop prop = generateProp(selectedProp, x, y);
                currentFloor.addProp(prop);
                System.out.println(selectedProp);
            }
        } else {
            System.out.println("Please select a prop using the controls");
        }
    }
    private void handleEditor(KeyEvent e){
        switch (e.getCode()){
            case A -> {
                selectedProp = ID.Empty;
            }
            case B -> {
                selectedProp = ID.Wall;
            }
            case C -> {
                selectedProp = ID.Person;
            }
            case D -> {
                selectedProp = ID.Spike;
            }
            case E -> {
                selectedProp = ID.Fan;
            }

            case ENTER -> {
                System.out.println("What floor is your level on?");
                Scanner input = new Scanner(System.in);
                int name = input.nextInt();
                currentFloor.lvl = name;
                saveGame(name);
            }
        }

        if(selectedProp != null && e.getCode() != KeyCode.ENTER){
            System.out.println("You have selected a " + selectedProp);
        }
    }

    private Prop generateProp(ID id, int x, int y){
        if(x >= 0 && x < currentFloor.getWidth() && y >= 0 && y < currentFloor.getHeight()) {
            switch (id) {
                case Empty:
                    return null;
                case Wall:
                    return new Wall(x, y, currentFloor);
                case Person:
                    return new BasicPerson(x, y, 50, 50, currentFloor);
                case Spike:
                    return new Spike(x, y, currentFloor);
                case Fan:
                    return new Fan(x,y, currentFloor, UP_DIRECTION);
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
        exitText.setOnMouseClicked(e -> Platform.exit());
        exitButton.setOnMouseClicked(e -> Platform.exit());


        menuRoot.getChildren().addAll(mainMenuText, playButton, playText, exitButton, exitText);
    }
    private void generateGameOver(Group gameOverRoot) {
        Font buttonFont = Font.font(24);
        Text gameOverText = new Text((WIDTH-300)/2, 50, "GAME OVER");
        gameOverText.setFont(Font.font(50));

        Text playText = new Text(110,130,"PLAY");

        playText.setFont(buttonFont);
        Rectangle playButton = new Rectangle(100,100,100,50);
        playButton.setFill(null);
        playButton.setStroke(Color.BLACK);
        playButton.setOnMouseClicked(this::handleGameOver);

        Text exitText = new Text(110,270, "EXIT");
        exitText.setFont(buttonFont);
        Rectangle exitButton = new Rectangle(100,250,100,50);
        exitButton.setFill(null);
        exitButton.setStroke(Color.BLACK);
        exitButton.setOnMouseClicked(e -> Platform.exit());

        gameOverRoot.getChildren().addAll(gameOverText, playButton, playText, exitButton, exitText);
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
    private void handleGameOver(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY) {
            loadGame(saveFile);
            generateSidebar();
            gameStateManager.setCurGameState(GameStateManager.GameState.GAME);
        }
    }

    public static Cutscene getCutscene(int curCutscene) {
        if(curCutscene <= NUMCUTSCENES) {
            switch (curCutscene) {
                case 0: return new Cutscene(null,null,null);
            }
        } else{
            System.out.println("Cutscene number " + curCutscene + " is out of bounds of range " + NUMCUTSCENES);
            return null;
        }
        return null;
    }

    public void goUp(){
        setCurrentFloor(loadFloor(currentFloor.lvl+1));
    }

    public void goDown(){
        setCurrentFloor(loadFloor(currentFloor.lvl-1));
    }

    public void setCurrentFloor(Floor floor){
        System.out.println("Current lvl is " + currentFloor.lvl + ", and the floor being loaded in on lvl " + floor.lvl);
        currentFloor.lvl = floor.lvl;
        currentFloor.props = floor.props;
        player = currentFloor.getPlayer();
        player.setFloor(currentFloor);
        currentFloor.update();
    }

    public static Floor loadFloor(int lvl){
        return loadFloor(new File("floor" + lvl + ".fun"));
    }

    public static Floor loadFloor(File file){
        try {
            System.out.println("Loading " + file.getName());
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            //Loading Player

            Player player = (Player) objectStream.readObject();
            Floor floor = new Floor((Integer)(objectStream.readObject()));
            player.setFloor(floor);
            player.gameStateManager = gameStateManager;

            //Loading all other props
            Integer numProps = (Integer) (objectStream.readObject());
            System.out.println("Number of props to be loaded: " + numProps);
            for (int i = 0; i < numProps; i++) {
                Prop temp = (Prop) (objectStream.readObject()); //We need to do this because we cannot serialize floors. Maybe fix this later?
                System.out.println(temp);
                temp.setFloor(floor);
            }

            objectStream.close();
            fileStream.close();
            return floor;
//                    floor.update();
        }
        catch (IOException | ClassNotFoundException exception) {
            System.out.println("File " + file.getName() + " not found.");
            exception.printStackTrace();
            return null;
        }
    }

    private void handleMovement(KeyEvent e) {
        switch (e.getCode()) {
            case W -> {
                if (player.getStatus() == Player.Status.Default && !player.animating) player.moveUp();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (currentFloor.props[player.getPosX()][player.getPosY() - 1] == null) print("It's Empty");
                     else {
                         print(currentFloor.props[player.getPosX()][player.getPosY() - 1].getDescription());
                         System.out.println(currentFloor.props[player.getPosX()][player.getPosY() - 1]);
                         player.setStatus(Player.Status.Default);
                    }
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutY(player.getPosY()*scale);
            }
            case A -> {
                if (player.getStatus() == Player.Status.Default && !player.animating) player.moveLeft();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (currentFloor.props[player.getPosX()-1][player.getPosY()] == null) print("It's Empty");
                    else {
                        print(currentFloor.props[player.getPosX()-1][player.getPosY()].getDescription());
                        System.out.println(currentFloor.props[player.getPosX()-1][player.getPosY()]);
                        player.setStatus(Player.Status.Default);
                    }
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutX(player.getPosX()*scale);
            }
            case S -> {
                if (player.getStatus() == Player.Status.Default && !player.animating) player.moveDown();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (currentFloor.props[player.getPosX()][player.getPosY() + 1] == null) print("It's Empty");
                    else {
                        print(currentFloor.props[player.getPosX()][player.getPosY() + 1].getDescription());
                        System.out.println(currentFloor.props[player.getPosX()][player.getPosY() + 1]);
                        player.setStatus(Player.Status.Default);
                    }
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutY(player.getPosY()*scale);
            }
            case D -> {
                if (player.getStatus() == Player.Status.Default && !player.animating) player.moveRight();
                else if (player.getStatus() == Player.Status.Looking) {
                    if (currentFloor.props[player.getPosX()+1][player.getPosY()] == null) print("It's Empty");
                    else {
                        print(currentFloor.props[player.getPosX()+1][player.getPosY()].getDescription());
                        System.out.println(currentFloor.props[player.getPosX()+1][player.getPosY()]);
                        player.setStatus(Player.Status.Default);
                    }
                    player.setStatus(Player.Status.Default);
                }
//                rect.setLayoutX(player.getPosX()*scale);
            }
            case SPACE -> {
                if (player.getStatus() == Player.Status.Default && !player.animating) {
                    print("Select a direction");
                    player.setStatus(Player.Status.Looking);
                }

            }
            case E -> {
                for (int i = 0; i < currentFloor.props[0].length; i++) {
                    System.out.println(Arrays.toString(currentFloor.props[i]));
                }
            }
            case SHIFT -> {
                gameStateManager.setCurGameState(GameStateManager.GameState.EDITOR);
                System.out.println("You are now in the editor. Please refer to the editor guide for how to make a new floor.");
            }
            case ENTER -> {
                if (player.getStatus() == Player.Status.Default && !player.animating) saveGame(saveFile);
            }
            case L -> {
                if (player.getStatus() == Player.Status.Default && !player.animating) loadGame(saveFile);
            }
            case T -> {
                BasicPerson test = new BasicPerson(5,10, BasicPerson.Direction.Down,currentFloor);
                pathfinding.startFindPath(test.getPosX(), test.getPosY(), player.getPosX(), player.getPosY());
                System.out.println(currentFloor.path.toString());
            }

            default -> System.out.println("default");
        }
        System.out.println("Player X: " + player.getPosX() + " Player Y: " + player.getPosY());

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
    public static final int SPIKE_SRC = 7;

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
                case 7 -> new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\spike.png");
                default -> throw new IllegalStateException("Unexpected value: " + id);
            };
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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
