import java.awt.*;
import java.util.ArrayList;

public class GameWindow implements Window{
    private Main parentClass;
    private ArrayList<Note> notes = new ArrayList<Note>();
    
    private int score = 0;
    private float bpm = 60;
    private Effect effects[] = {new Effect(100, 225), new Effect(100, 475)};
    private Image bgImage, noteImg;

    // コンストラクタ    
    public GameWindow(Main main){
        parentClass = main;

        // 画像読み込み
        bgImage = parentClass.getImage(parentClass.getCodeBase(), "./res/bg_mori_1.png");
        noteImg = parentClass.getImage(parentClass.getCodeBase(), "./res/ishi_stone.png");

        // ノーツ生成(デバッグ用)
        for(int i = 0; i < 100; i++){
           notes.add(new Note(0, i * 200 + 1000, bpm));
        }
    }

    // 画面のイニシャライザ
    public void init(){
        System.out.println("Init GameWindow");
    }

    // 描画
    public void draw(Graphics g){
        // 背景
        g.drawImage(bgImage, 0, 0, 700, 700, parentClass);

        // 各種情報
        g.drawString("Score : " + Integer.toString(score), 600, 30);

        // 判定円
        g.drawOval(75, 200, 50, 50);
        g.drawOval(75, 450, 50, 50);

        // エフェクト
        effects[0].draw(g);
        effects[1].draw(g);

        // ノーツ 
        g.setColor(Color.black);
        for(Note drawNote : notes){
           Pos notePos = drawNote.getDrawPos();
           if(notePos.x != -1){
                g.drawImage(noteImg, notePos.x, notePos.y, 60, 60, parentClass);
           }
        }
    }

    // キーが押された時
    public void keyPressed(char key){
        // ジャッジ
        boolean judgedFlag = false;
        for(int idx = 0; idx < notes.size() && !judgedFlag; idx++){
            if(key == 'f'){
                judgedFlag = notes.get(idx).judge(0);
            }else if(key == 'j'){
                judgedFlag = notes.get(idx).judge(1);
            }
        }

        // スコア反映
        if(judgedFlag) score += 100;

        // エフェクト再生
        if(key == 'f') effects[0].start();
        else if(key == 'j') effects[1].start();
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
    private float bpm;
    private boolean isAlive = true;
    private float noteXSpeed;

    // コンストラクタ
    public Note(int lane, float offset, float bpm){
        this.lane = lane;
        this.offset = offset;
        this.bpm = bpm;
        this.noteXSpeed = (float)(240.0) / (float)(3000.0 / bpm);

        if(lane == 0){
            this.yBias = 130;
        }else{
            this.yBias = 380;
        }
    }

    // ノーツの座標を返す
    public Pos getDrawPos(){
        offset -= noteXSpeed;
        if(offset < -100 || 700 < offset || !isAlive) return new Pos(-1, -1);

        return new Pos((int)offset + 100 - 30, (int)(0.01 * (offset % 200 - 100) * (offset % 200 - 100)) + yBias - 30);
    }

    // ジャッジ
    public boolean judge(int pressedLane){
        // 判定レーンが違う
       if(pressedLane != lane) return false; 

       // +-10フレームでパーフェクト 
       if(-10 * noteXSpeed < offset && offset < 10 * noteXSpeed){
           isAlive = false;
           return true;
       }else{
           return false;
       }
    }
}

// 座標管理クラス
class Pos{
    public int x, y;
    
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }
}

// エフェクト
class Effect{
    private int x;
    private int y;
    private int size = 500;

    // コンストラクタ
    public Effect(int x, int y){
        this.x = x;
        this.y = y;
    }

    // エフェクト描画
    public void draw(Graphics g){
        if(size < 150){
            g.setColor(new Color(0, 0, 0, (int)(255-size*1.5)));
            g.drawOval(x-size/2, y-size/2, size, size);
            size += 5;
        }
    }

    // エフェクト起動
    public void start(){
        size = 50;
    }
}
