import java.awt.*;

public class GameOverWindow implements Window{
    private Main parentClass;
    public static float score = 0;

    private String scoreStr;
    private Image bgImg, scoreboardImg, percentImg, rankImg, letterGameOver[] = new Image[100];

    public GameOverWindow(Main main){
        parentClass = main;

        // 画像読み込み(背景とか)
        scoreboardImg = parentClass.getImage(parentClass.getCodeBase(), "./res/score_board.png");
        bgImg = parentClass.getImage(parentClass.getCodeBase(), "./res/bg_sky.jpg");
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

    }

    public void draw(Graphics g){
        g.drawImage(bgImg, 0, 0, 700, 700, parentClass);
        g.drawImage(scoreboardImg, 80, 300, 540, 300, parentClass);

        // GameOverの文字
        int space = (scoreStr.length() + 1) * 80 / 2;
        for(int idx = 0; idx < scoreStr.length() + 1; idx++){
            g.drawImage(letterGameOver[idx], idx * 80 - space + 350, 350, 80, 80, parentClass);
        }

        // ランク
        g.drawImage(rankImg, 300, 460, 100, 100, parentClass);
    }

    public void keyPressed(char key){
        Main.changeWindow("Title");
    }

    public void keyReleased(char key){
    }
}
