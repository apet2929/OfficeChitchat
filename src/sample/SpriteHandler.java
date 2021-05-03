package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class SpriteHandler {
    private List<Sprite> sprites;
    public SpriteHandler(){
        sprites = new ArrayList<>();
    }
    public void update(double elapsedTime){
        for (Sprite sprite : sprites) {
            sprite.update(elapsedTime);
        }
    }
    public void render(GraphicsContext g){
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
    }
    public void add(Sprite sprite){
        if(!sprites.contains(sprite)){
            sprites.add(sprite);
        }
    }
    public void remove(Sprite sprite){
        if(sprites.contains(sprite)){
            sprites.remove(sprite);
        }
    }
    public Image getImageFromID(SpriteID id){
        switch(id) {
            case PLAYER -> {
                return ImageHandler.images[3];
            }
        }
        throw new IllegalArgumentException();
    }

}
