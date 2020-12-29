package sample;

public class BufferedAction {
    public Prop prop;
    public int direction;
    public String directionString;
    public boolean resolved;
    public BufferedAction(Prop prop,  int direction) {
        this.prop = prop;
        this.direction = direction;
        switch (direction) {
            case Floor.UP_DIRECTION:  directionString = "up";
            case Floor.DOWN_DIRECTION: directionString = "down";
            case Floor.LEFT_DIRECTION: directionString = "left";
            case Floor.RIGHT_DIRECTION: directionString = "right";
        }
        resolved = false;
    }
    public void resolve(){
        prop.floor.move(prop, direction);
        resolved = true;
    }

    @Override
    public String toString() {
        return "BufferedAction{" +
                "prop=" + prop +
                ", direction=" + directionString +
                '}';
    }
}
