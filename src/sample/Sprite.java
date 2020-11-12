package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;

public class Sprite {

        private Image image;
        private double positionX;
        private double positionY;
        public double width;
        public double height;
        public ID type;

        public Sprite(Image image)
        {
            this.image = image;
        }

        public void setImage(Image i)
        {
            image = i;
            width = i.getWidth();
            height = i.getHeight();
        }

        public void setImage(String filename)
        {
            Image i = new Image(filename);
            setImage(i);
        }

        public void setImage(FileInputStream fileInputStream)
        {
            Image i = new Image(fileInputStream);
            setImage(i);
        }

        public void setPosition(double x, double y)
        {
            positionX = x;
            positionY = y;
        }


        public Rectangle2D getBoundary()
        {
            return new Rectangle2D(positionX,positionY,width,height);
        }

        public boolean intersects(Sprite s)
        {
            return s.getBoundary().intersects( this.getBoundary() );
        }

        public String toString()
        {
            return " Type: " + type + ", Position: [" + positionX + "," + positionY + "]";

        }

        public double getPositionX(){
            return positionX;
        }

        public double getPositionY() {
            return positionY;
        }

}
