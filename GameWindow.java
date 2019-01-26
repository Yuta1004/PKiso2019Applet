import java.applet.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.lang.Math;
import java.io.*;
import javax.sound.sampled.*;

public class GameWindow implements Window{
    private Main parentClass;
    private ArrayList<Note> notes = new ArrayList<Note>();
    private int frameCount = 0;

    // スタートアニメーション
    private int startAnimationPos = 0;
    private Font startAnimationFont = new Font("Monospaced", Font.BOLD, 40);

    // 音ゲ設定
    private int score = 0, delay, noteNum;
    private float bpm = 160;
    private String title, musicFile;
    private boolean f_KeyPressing = false, j_KeyPressing = false;

    // 描画関連
    private Effect effects[] = {new Effect(100, 225), new Effect(100, 475)};
    private Image bgImage, noteImg, flowerImg;
    private int bgImageX = 0, bgScrollSpeed;
    private Clip music;
    private Font scoreDrawFont = new Font("Monospaced", Font.BOLD, 80);

    // コンストラクタ
    public GameWindow(Main main){
        parentClass = main;

        // 画像読み込み
        bgImage = parentClass.getImage(parentClass.getCodeBase(), "./res/bg_mori_1.png");
        noteImg = parentClass.getImage(parentClass.getCodeBase(), "./res/bug_hachi_doku.png");
        flowerImg = parentClass.getImage(parentClass.getCodeBase(), "./res/flower.png");

        // 譜面データ読み込み
        loadData("./res/hardcore.txt");
        noteNum = notes.size();

        // 曲読み込み
        music = getAudioClip(new File("./res/hardcore.wav"));
    }

    // 画面のイニシャライザ
    public void init(){
        System.out.println("Init GameWindow");
    }

    // 描画
    public void draw(Graphics g){
        frameCount ++;

        // 背景
        bgImageX -= bgScrollSpeed;
        if(bgImageX <= -700) bgImageX = 0;
        g.drawImage(bgImage, bgImageX, 0, 700, 700, parentClass);
        g.drawImage(bgImage, bgImageX+700, 0, 700, 700, parentClass);

        // 200フレームまでゲーム再生前演出
        if(frameCount < 200){
            int alpha = Math.max(0, 250 - Math.max(0, startAnimationPos - 600) / 2);

            // ぼかし
            g.setColor(new Color(255, 255, 255, Math.max(0, alpha-70)));
            g.fillRect(0, 0, 700, 700);

            // 曲名とそれっぽい棒
            g.setColor(new Color(0, 0, 0, alpha));
            g.setFont(startAnimationFont);
            g.drawString(title, startAnimationPos-100, startAnimationPos-10);
            g.drawLine(800 - startAnimationPos, 0, startAnimationPos - 100, 700);
            g.drawLine(1300 - startAnimationPos, 0, startAnimationPos - 500, 700);
            g.drawLine(startAnimationPos - 400, 0, 1400 - startAnimationPos, 700);
            g.drawLine(startAnimationPos - 200, 0, startAnimationPos + 200, 700);

            // アニメーション
            if(startAnimationPos < 270 || 370 < startAnimationPos){
                startAnimationPos += 15;
            }else{
                startAnimationPos += 1;
            }
            return;
        }

        // ゲームスタート
        if(frameCount == 200){
            // ノーツ移動スレッドを建てる -> 音楽再生
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(()->{
                for(Note note: notes){
                    note.move();
                }
            }, delay, 10, TimeUnit.MILLISECONDS);
            music.start();
        }

        // 各種情報
        g.setFont(scoreDrawFont);
        g.setColor(new Color(0, 0, 0, 40));
        g.drawString(String.format("%.2f %%", (float)score / noteNum), 230, 360);

        // 判定円
        g.setColor(Color.black);
        g.drawImage(flowerImg, 60, 185, 80, 80, parentClass);
        g.drawImage(flowerImg, 60, 435, 80, 80, parentClass);

        // エフェクト
        effects[0].draw(g);
        effects[1].draw(g);

        // ノーツ
        g.setColor(Color.black);
        for(Note drawNote : notes){
           Pos notePos = drawNote.getDrawPos();
           if(notePos.x != -1){
                g.drawImage(noteImg, notePos.x, notePos.y, 100, 100, parentClass);
           }
        }
    }

    // キーが押された時
    public void keyPressed(char key){
        // キー押しっぱなしは無効
        if((key == 'f' && f_KeyPressing) || (key == 'j' && j_KeyPressing)) return;

        // ジャッジ
        boolean judgedFlag = false;
        int idx = 0;
        for(; idx < notes.size() && !judgedFlag && notes.get(idx).getDrawPos().x < 300; idx++){
            if(key == 'f'){
                judgedFlag = notes.get(idx).judge(0);
            }else if(key == 'j'){
                judgedFlag = notes.get(idx).judge(1);
            }
        }

        // スコア反映
        if(judgedFlag){
            score += 100;
            notes.remove(idx-1);
        }

        // キー状態更新 + エフェクト再生
        if(key == 'f'){
            f_KeyPressing = true;
            effects[0].start();
        }else if(key == 'j'){
            j_KeyPressing = true;
            effects[1].start();
        }
    }

    // キーが離された時
    public void keyReleased(char key){
        if(key == 'f') f_KeyPressing = false;
        else if(key == 'j') j_KeyPressing = false;
    }

    // 音楽ファイル読み込み
    private Clip getAudioClip(File path){
        Clip clip;

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){
            // 音楽データ読み込み
            AudioFormat af = ais.getFormat();
            DataLine.Info dataline = new DataLine.Info(Clip.class, af);

            // データに対応するラインを取得 -> 返す
            clip = (Clip)AudioSystem.getLine(dataline);
            clip.open(ais);
            return clip;
        }
        catch(Exception e){
            System.err.println("Music File Load Error!!");
            return null;
        }
    }

    // 譜面読み込み
    private boolean loadData(String path){
        File gameDataFile;
        FileReader fileReader;
        BufferedReader br;

        // ファイルを開く
        try{
            gameDataFile = new File(path);
            fileReader = new FileReader(gameDataFile);
            br = new BufferedReader(fileReader);
        }catch(FileNotFoundException e){
            return false;
        }

        // ファイル読み込み
        try{
            String line;
            int tempo = 4;
            float putPos = 0;

            // 1行ずつ読み込んでいく
            while((line = br.readLine()) != null){
                // コメント or 空行
                if(line.equals("") || line.contains("//")) continue;

                // 設定読み込み
                if(line.contains("#")){
                    if(line.contains("#BPM")){
                        bpm = Integer.parseInt(line.split(" ")[1]);
                    }else if(line.contains("#DELAY")){
                        delay = Integer.parseInt(line.split(" ")[1]);
                    }else if(line.contains("#TITLE")){
                        title = line.split(" ")[1];
                    }else if(line.contains("#MUSICFILE")){
                        musicFile = line.split(" ")[1];
                    }else if(line.contains("#BGSPEED")){
                        bgScrollSpeed = Integer.parseInt(line.split(" ")[1]);
                    }else if(line.contains("#TEMPO")){
                        tempo = Integer.parseInt(line.split(" ")[1]);
                    }
                    continue;
                }

                // ノーツ読み込み
                for(String note: line.split(",")){
                    if(note.equals("0") || note.equals("1")){
                        notes.add(new Note(Integer.parseInt(note), putPos, bpm));
                    }
                    putPos += 200 * (float)(4.0 / tempo);
                }
            }
        }catch(IOException e){
            return false;
        }

        return true;
    }
}

