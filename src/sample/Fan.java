package sample;

import static sample.Floor.*;

public class Fan extends Prop{

    public int direction;
    public Fan(int x, int y, Floor floor, int direction) {
        super(ID.Fan, false, x, y, 50, 50, "It's a fan.", Main.WALL_CORNER_SRC, floor);
        this.direction = direction;
    }

    @Override
    public void tick(Floor floor) {
        switch(direction){
            case UP_DIRECTION: {
                floor.moveUp(getPosX(), getPosY()-1,false);
                floor.moveUp(getPosX()+1, getPosY()-1,false);
                floor.moveUp(getPosX()-1, getPosY()-1,false);
            }
            case LEFT_DIRECTION: {
                floor.moveLeft(getPosX()-1, getPosY(),false);
                floor.moveLeft(getPosX()-1, getPosY()+1,false);
                floor.moveLeft(getPosX()-1, getPosY()-1,false);
            }
            case RIGHT_DIRECTION: {
                floor.moveRight(getPosX() + 1, getPosY(),false);
                floor.moveRight(getPosX() + 1, getPosY()+1,false);
                floor.moveRight(getPosX() + 1, getPosY()-1,false);
            }
            case DOWN_DIRECTION: {
                floor.moveDown(getPosX(), getPosY()+1,false);
                floor.moveDown(getPosX()+1, getPosY()+1,false);
                floor.moveDown(getPosX()-1, getPosY()+1,false);
            }
        }
    }


}
