import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;

public class GameOverWindow implements Window{
    private Main parentClass;
    public static float score = 0;

    private String scoreStr;
    private Image bgImg, scoreboardImg, percentImg, rankImg, resultImg;
    private Image letterGameOver[] = new Image[100];
    private Clip music;

    public GameOverWindow(Main main){
        parentClass = main;

        // 画像読み込み(背景とか)
        scoreboardImg = parentClass.getImage(parentClass.getCodeBase(), "./res/score_board.png");
        bgImg = parentClass.getImage(parentClass.getCodeBase(), "./res/bg_sky.jpg");
        resultImg = parentClass.getImage(parentClass.getCodeBase(), "./res/gameresult.png");
    }

    public void init(){
        System.out.println("Init GameOverWindow");

        // 画像読み込み(スコア表示用フォント)
        scoreStr = String.format("%.2f", score).replace(".", "@");
        for(int idx = 0; idx < scoreStr.length(); idx++){
            letterGameOver[idx] = parentClass.getImage(parentClass.getCodeBase(), "./res/letter/" + scoreStr.charAt(idx) + ".png");
        }
        letterGameOver[scoreStr.length()] = parentClass.getImage(parentClass.getCodeBase(), "./res/letter/p.png");

        // 画像読み込み(ランク)
        if(score > 95.0f) rankImg = parentClass.getImage(parentClass.getCodeBase(), "./res/letter/S.png");
        else if(score > 90.0f) rankImg = parentClass.getImage(parentClass.getCodeBase(), "./res/letter/A.png");
        else if(score > 80.0f) rankImg = parentClass.getImage(parentClass.getCodeBase(), "./res/letter/B.png");
        else if(score > 70.0f) rankImg = parentClass.getImage(parentClass.getCodeBase(), "./res/letter/C.png");
        else rankImg = parentClass.getImage(parentClass.getCodeBase(), "./res/letter/D.png");

        // 音楽読み込み
        music = getAudioClip(new File("./res/syabondama.wav"));
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void draw(Graphics g){
        g.drawImage(bgImg, 0, 0, 700, 700, parentClass);
        g.drawImage(scoreboardImg, 80, 300, 540, 300, parentClass);
        g.drawImage(resultImg, 125, 100, 450, 100, parentClass);

        // GameOverの文字
        int space = (scoreStr.length() + 1) * 80 / 2;
        for(int idx = 0; idx < scoreStr.length() + 1; idx++){
            g.drawImage(letterGameOver[idx], idx * 80 - space + 350, 350, 80, 80, parentClass);
        }

        // ランク
        g.drawImage(rankImg, 300, 460, 100, 100, parentClass);
    }

    public void keyPressed(char key){
        music.stop();
        Main.changeWindow("Title");
    }

    public void keyReleased(char key){
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
