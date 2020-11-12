package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageManager {
    public FileInputStream playerUp;
    public FileInputStream playerDown;
    public FileInputStream playerLeft;
    public FileInputStream playerRight;

    public ImageManager() throws FileNotFoundException {
        playerUp = new FileInputStream("F:\\Documents\\Code\\OfficeChitchat\\src\\sample\\assets\\player_up.png");
    }

}
