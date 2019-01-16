import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow implements Window{
    private Main parentClass;
    private ArrayList<Note> notes = new ArrayList<Note>();
    private Random random;
    
    public GameWindow(Main main){
        parentClass = main;

        random = new Random();
        for(int i = 0; i < 100; i++){
           notes.add(new Note(random.nextInt(2), random.nextInt(1000)+300));
        }
    }

    public void init(){
        System.out.println("Init GameWindow");
    }

    public void draw(Graphics g){
        // 判定円
        g.drawOval(75, 200, 50, 50);
        g.drawOval(75, 450, 50, 50);

        // ノーツ
        for(Note drawNote : notes){
           drawNote.draw(g);
        }
    }

    public void keyPressed(char key){
        Main.changeWindow("GameOver");
    }

    public void keyReleased(char key){
    }
}

class Note{
    private int lane;
    private int yBias;
    private float offset;

    public Note(int lane, float offset){
        this.lane = lane;
        this.offset = offset;

        if(lane == 0){
            this.yBias = 150;
        }else{
            this.yBias = 400;
        }
    }

    public void draw(Graphics g){
        offset --;
        if(offset < -100 || 700 < offset) return;

        g.drawOval((int)offset + 100 - 20, (int)(0.015 * (offset % 150 - 75) * (offset % 150 - 75)) + yBias - 20, 40, 40);
    }
}
