package sample;

import javafx.scene.image.Image;

public class Cutscene {
    private Image[] images;
    private String[] text;
    private int[] flags; //These flags will be used to let the system know when to change the picture, based on what line of text we are on.
                         //flags.length will always be equal to images.length

    public Cutscene(Image[] images, String[] text, int[] flags) {
        this.images = images;
        this.text = text;
        this.flags = flags;
        if(flags.length != images.length) System.out.println("Cutscene may not have been initialized correctly");
    }

    public Image getImage(int index) {
        if(index <= images.length){
            return images[index];
        }
        else System.out.println("Image number " + index + " is out of bounds of range " + images.length);
        return null;
    }

    public String[] getText() {
        return text;
    }

    public String getText(int index){
        if(index < text.length){
            return text[index];
        }
        else System.out.println("Text number " + index + " is out of bounds of range " + text.length);
        return "End of cutscene";
    }
    public int[] getFlags() {
        return flags;
    }

}
