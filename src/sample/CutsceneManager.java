package sample;

import javafx.scene.image.Image;

public class CutsceneManager {
    private int curText;
    private int curImage;
    private Cutscene cutscene;
    public boolean finished = false;
    public CutsceneManager(Cutscene cutscene) {
        this.curText = 0;
        this.curImage = 0;
        this.cutscene = cutscene;
    }
    public void finishCutscene(){
        this.curText = 0;
        this.curImage = 0;
        finished = true;
    }

    public void update(int updateBy){
        if(updateBy < 0) System.out.println("You can only go forwards");
        curText+=updateBy;
        while(curText > cutscene.getFlags()[curImage] && curImage < cutscene.getFlags().length){
            curImage++;
        }

        if(curText == cutscene.getFlags()[cutscene.getFlags().length-1]) finishCutscene();
    }
    public String getCurText(){
        return cutscene.getText(curText);
    }
    public Image getCurImage(){
        return cutscene.getImage(curImage);
    }
    public void setCutscene(Cutscene cutscene){
        this.cutscene = cutscene;
    }

}
