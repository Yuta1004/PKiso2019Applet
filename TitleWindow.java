import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;
import java.lang.Math.*;

public class TitleWindow implements Window{
    private Main parentClass;

    private Image bgImg, rectImg, manImg, womanImg, titleLetters[] = new Image[4];
    private Clip music;
    private int frameCount = 0;

    public TitleWindow(Main main){
        parentClass = main;

        // 画像読み込み
        bgImg = loadImage("./res/bg_title.png");
        rectImg = loadImage("./res/fabric_mark_rectangle.png");
        manImg = loadImage("./res/music_norinori_man.png");
        womanImg = loadImage("./res/music_norinori_woman.png");
        titleLetters[0] = loadImage("./res/letter_o.png");
        titleLetters[1] = loadImage("./res/letter_to.png");
        titleLetters[2] = loadImage("./res/letter_ge.png");
        titleLetters[3] = loadImage("./res/letter_yokobou.png");

        // 音楽読み込み
        music = getAudioClip(new File("./res/morinokumasan.wav"));
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void init(){
        System.out.println("Init TitleWindow");
    }

    public void draw(Graphics g){
        frameCount ++;

        // 背景
        g.drawImage(bgImg, 0, 0, 700, 700, parentClass);
        g.drawImage(manImg, 0, 500 + (int)(20 * Math.sin(frameCount / 5.0 + 500)), parentClass);
        g.drawImage(womanImg, 520, 500 + (int)(20 * Math.sin(frameCount / 5.0)), parentClass);

        // タイトル
        g.drawImage(rectImg, 120, 115, 460, 170, parentClass);
        for(int idx = 0; idx < 4; idx++){
            g.drawImage(titleLetters[idx], idx * 100 + 150, 150, 100, 100, parentClass);
        }
    }

    public void keyPressed(char key){
        Main.changeWindow("Game");
    }

    public void keyReleased(char key){
    }

    // 画像読み込み関数
    private Image loadImage(String path){
        return parentClass.getImage(parentClass.getCodeBase(), path);
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
}
