package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Sprite {
    private Image image;
    private double x, y;
    private double velX, velY;
    private double width, height;

    public Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.velX = 0;
        this.velY = 0;
        this.width = width;
        this.height = height;
    }

    public void update(double elapsedTime){
        x += velX * elapsedTime;
        y += velY * elapsedTime;
    }
    public void render(GraphicsContext g){
        g.drawImage(image, x, y);
    }
    public Rectangle2D getBounary(){
        return new Rectangle2D(x, y, width, height);
    }

    public boolean intersects(Sprite other){
        return other.getBounary().intersects(this.getBounary());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }


}
