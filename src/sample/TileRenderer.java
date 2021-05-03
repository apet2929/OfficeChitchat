package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileRenderer {
    private final int tileSizeX = 32;
    private final int tileSizeY = 32;
    private Image[] tilePalette;
    private final Class<? extends TileRenderer> c = getClass();

    public TileRenderer(){
        tilePalette = ImageHandler.images;
    }
    public void renderMap(int[] map, int mapWidth, int startX, int startY, GraphicsContext g){
        int tileColumn = 1; //Keeps track of x position
        int tileRow = 1; //Keeps track of y position
        for (int j : map) { //Loops through map
            g.drawImage(tilePalette[j], (tileColumn - 1) * tileSizeX + startX, (tileRow - 1) * tileSizeY + startY); //Draws image at position offset by start position

            tileColumn++;
            if (tileColumn > mapWidth) {
                tileColumn = 1;
                tileRow++;
            }
        }
    }


    public Image getImage(int index){
        return tilePalette[index];
    }
}
