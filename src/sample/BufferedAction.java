package sample;

public class BufferedAction {
    public Prop prop;
    public int direction;
    public boolean resolved;
    public BufferedAction(Prop prop,  int direction) {
        this.prop = prop;
        this.direction = direction;
        resolved = false;
    }

    @Override
    public String toString() {
        return "BufferedAction{" +
                "prop=" + prop +
                ", direction=" + direction +
                '}';
    }
}
