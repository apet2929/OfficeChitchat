package sample;

public class Spike extends Prop{

    public Spike( int x, int y, int width, int height, int imageID, Floor floor) {
        super(ID.Spike, false, x, y, width, height, "They're spikes, and they hurt", imageID, floor);
        this.setImageID(imageID);
    }


}
