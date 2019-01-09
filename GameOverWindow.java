import java.awt.*;

public class GameOverWindow implements Window{
    public GameOverWindow(){
    }

    public void init(){
        System.out.println("Init GameOverWindow");
    }

    public void draw(Graphics g){
        g.drawString("Hello GameOverWindow!!", 100, 100);
    }

    public void keyPressed(char key){
        Main.changeWindow("Title");
    }

    public void keyReleased(char key){
    }
}
