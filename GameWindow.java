import java.awt.*;

public class GameWindow implements Window{
    public GameWindow(){
    }

    public void init(){
        System.out.println("Init GameWindow");
    }

    public void draw(Graphics g){
        g.drawString("Hello GameWindow!!", 100, 100);
    }

    public void keyPressed(char key){
        Main.changeWindow("GameOver");
    }

    public void keyReleased(char key){
    }
}
