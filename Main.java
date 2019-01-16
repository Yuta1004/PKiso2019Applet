import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/* <applet code="Main.class" width=700 height=700></applet> */

public class Main extends Applet implements Runnable, KeyListener{
    private Thread drawThread;
    private static String nowDrawingWindow = "";
    private static HashMap<String, Window> windows = new HashMap<String, Window>();
    private Image bufImage;
    
    // システム初期化
    public void init(){
        nowDrawingWindow = "Title";
        windows.put("Title", new TitleWindow(this));
        windows.put("Game", new GameWindow(this));
        windows.put("GameOver", new GameOverWindow(this));

        bufImage = createImage(700, 700);

        addKeyListener(this);
    }

    // スレッド初期化
    public void start(){
        drawThread = new Thread(this);
        drawThread.start();
    }

    // 画面更新担当
    public void run(){
        while(true){
            try{
                repaint();
                Thread.sleep(10); 
            }catch(InterruptedException e){}
        }
    }

    // 描画画面切り替え
    public static boolean changeWindow(String windowID){
        if(windows.containsKey(windowID)){
            nowDrawingWindow = windowID;
            windows.get(nowDrawingWindow).init();
            return true;
        }
        else{
            return false;
        }
    }
    
    // 各画面の描画メソッドを呼ぶ
    public void paint(Graphics g){
        // ダブルバッファ
        bufImage = createImage(700, 700);
        Graphics gb = bufImage.getGraphics();

        // アンチエイリアシング
        Graphics2D g2 = (Graphics2D)gb;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        windows.get(nowDrawingWindow).draw(g2);

        g.drawImage(bufImage, 0, 0, 700, 700, this);
    }

    // キー入力(押)
    public void keyPressed(KeyEvent e){
        windows.get(nowDrawingWindow).keyPressed(e.getKeyChar()); 
    }

    // キー入力(離)
    public void keyReleased(KeyEvent e){
        windows.get(nowDrawingWindow).keyReleased(e.getKeyChar());
    }

    // キー入力(使わない)
    public void keyTyped(KeyEvent e){}
}
