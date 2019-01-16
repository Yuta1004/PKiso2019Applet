import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow implements Window{
    private Main parentClass;
    private ArrayList<Note> notes = new ArrayList<Note>();
    private Random random;

    // コンストラクタ    
    public GameWindow(Main main){
        parentClass = main;

        // ノーツ生成(デバッグ用)
        random = new Random();
        for(int i = 0; i < 100; i++){
           notes.add(new Note(random.nextInt(2), random.nextInt(1000)+300));
        }
    }

    // 画面のイニシャライザ
    public void init(){
        System.out.println("Init GameWindow");
    }

    // 描画
    public void draw(Graphics g){
        // 判定円
        g.drawOval(75, 200, 50, 50);
        g.drawOval(75, 450, 50, 50);

        // ノーツ
        for(Note drawNote : notes){
           drawNote.draw(g);
        }
    }

    // キーが押された時
    public void keyPressed(char key){
        Main.changeWindow("GameOver");
    }

    // キーが離された時
    public void keyReleased(char key){
    }
}

// ノールクラス
class Note{
    private int lane;
    private int yBias;
    private float offset;

    // コンストラクタ
    public Note(int lane, float offset){
        this.lane = lane;
        this.offset = offset;

        if(lane == 0){
            this.yBias = 150;
        }else{
            this.yBias = 400;
        }
    }

    // ノーツ描画
    public void draw(Graphics g){
        offset --;
        if(offset < -100 || 700 < offset) return;

        g.drawOval((int)offset + 100 - 20, (int)(0.015 * (offset % 150 - 75) * (offset % 150 - 75)) + yBias - 20, 40, 40);
    }
}
