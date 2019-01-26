import java.awt.*;

public class GameOverWindow implements Window{
    private Main parentClass;
    public static float score = 0;

    private String scoreStr;
    private Image bgImg, percentImg, rankImg, letterGameOver[] = new Image[100];

    public GameOverWindow(Main main){
        parentClass = main;

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

        // 画像読み込み(背景とか)
        bgImg = parentClass.getImage(parentClass.getCodeBase(), "./res/bg_garden.png");
    }

    public void init(){
        System.out.println("Init GameOverWindow");
    }

    public void draw(Graphics g){
        g.drawImage(bgImg, 0, 0, 700, 700, parentClass);

        // GameOverの文字
        int space = scoreStr.length() * 80 / 2;
        for(int idx = 0; idx < scoreStr.length() + 1; idx++){
            g.drawImage(letterGameOver[idx], idx * 80 + space, 200, 80, 80, parentClass);
        }

        // ランク
        g.drawImage(rankImg, 300, 350, 100, 100, parentClass);
    }

    public void keyPressed(char key){
        Main.changeWindow("Title");
    }

    public void keyReleased(char key){
    }
}
