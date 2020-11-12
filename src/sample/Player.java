package sample;

public class Player extends Prop{
    private Floor floor;

    public Player(Sprite sprite, int x, int y, Floor floor) {
        super(ID.Player, true, sprite,x,y, 50, 50);
        this.floor = floor;
    }

    public void moveDown(){
        if(getPosY() >= floor.getHeight()) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else {
            if (floor.props[this.getPosX()][this.getPosY() + 1].isPassable()) {
                floor.props[this.getPosX()][this.getPosY()] = floor.props[this.getPosX()][this.getPosY()+1];
                floor.props[this.getPosX()][this.getPosY()+1] = this;
                setPosY(getPosY() + 1);
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX()][this.getPosY() + 1].getID());
            }
        }
    }
    public void moveUp(){
        if(getPosY() <= 0) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
        } else{
            Prop moveTo = floor.props[this.getPosX()][this.getPosY()-1];
            if (moveTo.isPassable()) {
                floor.props[this.getPosX()][this.getPosY()] = floor.props[this.getPosX()][this.getPosY()-1];
                floor.props[this.getPosX()][this.getPosY()-1] = this;
                setPosY(getPosY() - 1);
            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX()][this.getPosY() - 1].getID());
            }
        }
    }
    public void moveLeft(){
        if(getPosX() <= 0) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
            System.out.println(floor.getWidth() + " " + floor.getHeight());
        } else{
            if (floor.props[this.getPosX() - 1][this.getPosY()].isPassable()) {
                floor.props[this.getPosX()][this.getPosY()] = floor.props[this.getPosX() - 1][this.getPosY()];
                floor.props[this.getPosX()-1][this.getPosY()] = this;
                setPosX(getPosX() - 1);

            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX() - 1][this.getPosY()].getID());
            }
        }
    }
    public void moveRight() {
        if (getPosX() >= floor.getWidth()) {
            System.out.println("You are at the edge of the room x: " + getPosX() + " y: " + getPosY());
            System.out.println(floor.getWidth() + " " + floor.getHeight());
        } else{
            if (floor.props[this.getPosX() + 1][this.getPosY()].isPassable()) {
                floor.props[this.getPosX()][this.getPosY()] = floor.props[this.getPosX() + 1][this.getPosY()]; //The thing to the right is moved to the player's position
                floor.props[this.getPosX()+1][this.getPosY()] = this; //The player is moved to the right
                setPosX(getPosX() + 1);

            } else {
                System.out.println("You bumped into a " + floor.props[this.getPosX() + 1][this.getPosY()].getID());
            }
        }
    }

}
