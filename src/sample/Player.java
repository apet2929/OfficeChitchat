package sample;


import java.util.Locale;

public class Player extends BasicPerson {

    private Status status;
    public GameStateManager gameStateManager;
    public int health;

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
        this.direction = Direction.Up;
        this.health = 10;
    }

    public void spotted(){
        health -= 1;
        System.out.println("Player spotted, player health is now " + health);
    }

    public void moveDown(){
        this.direction = Direction.Down;
        Prop prop = floor.getProp(getPosX(), getPosY()+1);
        floor.moveDown(this);
        if(prop != null) handleCollision(prop);
        floor.tick();
        floor.update();
    }

    public void moveUp(){
        this.direction = Direction.Up;
        Prop prop = floor.getProp(getPosX(), getPosY()-1);
        floor.moveUp(this);
        if(prop != null) handleCollision(prop);
        floor.tick();
        floor.update();
    }

    public void moveLeft(){
        this.direction = Direction.Left;
        Prop prop = floor.getProp(getPosX()-1, getPosY());
        floor.moveLeft(this);
        if(prop != null) handleCollision(prop);
        floor.tick();
        floor.update();
    }

    public void moveRight() {
        this.direction = Direction.Right;
        Prop prop = floor.getProp(getPosX()+1, getPosY());
        floor.moveRight(this);
        if(prop != null) handleCollision(prop);
        floor.tick();
        floor.update();
    }

    private void die() {
        gameStateManager.setCurGameState(GameStateManager.GameState.GAMEOVER);
    }

    private void handleCollision(Prop prop){
        if(!prop.isWalkable()) Main.print("You bumped into a " + prop.getID().toString().toLowerCase(Locale.ROOT));
        if(prop.getID().equals(ID.Spike)){
            die();
        } else if(prop.getID().equals(ID.FloorUp)){
            gameStateManager.goUp();
        } else if(prop.getID().equals(ID.FloorDown)){
            gameStateManager.goDown();
        }
    }

    @Override
    public void tick(Floor floor) {
        switch (direction) {
            case Up -> this.setImageID(Main.PLAYER_UP_SRC);
            case Down -> this.setImageID(Main.PLAYER_DOWN_SRC);
            case Left -> this.setImageID(Main.PLAYER_LEFT_SRC);
            case Right -> this.setImageID(Main.PLAYER_RIGHT_SRC);
        }
    }
}
