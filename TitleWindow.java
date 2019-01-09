import java.awt.*;

public class TitleWindow implements Window{
    public TitleWindow(){
    }

    public void init(){
        System.out.println("Init TitleWindow");
    }

    public void draw(Graphics g){
        g.drawString("Hello TitleWindow!!", 100, 100);
    }

    public void keyPressed(char key){
        Main.changeWindow("Game");
    }

    public void keyReleased(char key){
    }
}
