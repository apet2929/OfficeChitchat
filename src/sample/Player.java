package sample;


public class Player extends BasicPerson {
    public enum Status {
        Default,
        Looking
    };

    public static final String UP_IMAGE = "player_up";
    public static final String DOWN_IMAGE = "player_down";
    public static final String LEFT_IMAGE = "player_left";
    public static final String RIGHT_IMAGE = "player_right";

    private Status status;
    public GameStateManager gameStateManager;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player( int x, int y, Floor floor, Status status, GameStateManager gameStateManager) {
        super(x,y, 50, 50, floor);
        this.setID(ID.Player);
        this.setDescription( "It's you");
        this.status = status;
        this.gameStateManager = gameStateManager;
    }

    public void moveDown(){
        this.setImageID(Main.PLAYER_DOWN_SRC);
        Prop prop = floor.getProp(getPosX(), getPosY()+1);
        floor.moveDown(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
            if(prop.getID() == ID.FloorUp) System.out.println("yes");
        }
        floor.tick();
        floor.update();
    }

    public void moveUp(){
        this.setImageID(Main.PLAYER_UP_SRC);
        Prop prop = floor.getProp(getPosX(), getPosY()-1);
        floor.moveUp(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
            if(prop.getID() == ID.FloorUp) System.out.println("yes");
        }
        floor.tick();
        floor.update();
    }
    public void moveLeft(){
        this.setImageID(Main.PLAYER_LEFT_SRC);
        Prop prop = floor.getProp(getPosX()-1, getPosY());
        floor.moveLeft(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
            if(prop.getID() == ID.FloorUp){
                System.out.println("yes");
            }
        };
        floor.tick();
        floor.update();
    }

    public void moveRight() {
        this.setImageID(Main.PLAYER_RIGHT_SRC);
        Prop prop = floor.getProp(getPosX()+1, getPosY());
        floor.moveRight(this);
        if(prop != null) {
            if (prop.getID() == ID.Spike) die();
            else if (prop.getID() == ID.FloorUp){
                Main.print("yes");
                gameStateManager.setFloor(Main.lvl + 1);
            } else if (prop.getID() == ID.FloorDown) {
                gameStateManager.setFloor(Main.lvl - 1);
            }
        }
        floor.tick();
        floor.update();
    }

    private void die() {
        gameStateManager.setCurGameState(GameStateManager.GameState.GAMEOVER);
    }






}
