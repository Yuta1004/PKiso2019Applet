import java.awt.*;

public class Effect{
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
