package sample;

public class Entrance extends Prop{
    int pathTo;
    public boolean up; //Specifies whether it is an upstairs or a downstairs, purely for decorative purposes, makes no difference.
    public Entrance( int x, int y, Floor floor, int pathTo, boolean up) {
        super(up ? ID.FloorUp : ID.FloorDown, false, x, y, Main.scale,Main.scale, "A stairway leading " + (up ? "up" : "down"), Main.WALL_CORNER_SRC, floor);
        this.pathTo = pathTo;
        this.up = up;
        Main.preLoadFloor(pathTo);
    }



}
