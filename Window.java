import java.awt.*;

interface Window{
    // 画面切り替え時に呼ばれる
    public void init();

    // 毎フレーム呼ばれる
    public void draw(Graphics g);

    // キーが押されたら呼ばれる
    public void keyPressed(char key);

    // キーが離れたら押される
    public void keyReleased(char key);
}
