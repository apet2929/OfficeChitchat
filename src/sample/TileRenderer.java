package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileRenderer {
    public static double scaleX = 1;
    public static double scaleY = 1;
    public final double tileSizeX = 32;
    public final double tileSizeY = 32;
    private final Image[] tilePalette;

    private static boolean scaled = false;
    public TileRenderer(){
        tilePalette = ImageHandler.images;
    }

    public void renderMap(int[] map, int mapWidth, int startX, int startY, GraphicsContext g){
        //Draws entire map unscaled, then scales the entire thing
        int tileColumn = 1; //Keeps track of x position
        int tileRow = 1; //Keeps track of y position
        double x = startX;
        double y = startY;
        for (int j : map) { //Loops through map
            g.drawImage(tilePalette[j], x, y); //Draws image at position offset by start position
            x += tileSizeX;

            tileColumn++;
            if (tileColumn > mapWidth) {
                tileColumn = 1;
                tileRow++;
                x = 0;
                y += tileSizeY;
            }
        }

        if(scaled){
            g.scale(scaleX, scaleY);
            scaled = false;
        }

    }

    public static void scale(double sX, double sY){
        scaleX = sX;
        scaleY = sY;
        scaled = true;
        System.out.println(scaleX + " " + scaleY);
    }


}
