import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/* <applet code="Main.class" width=700 height=700></applet> */

public class Main extends Applet implements Runnable, KeyListener{
    private Thread drawThread;
    private static String nowDrawingWindow = "";
    private static HashMap<String, Window> windows = new HashMap<String, Window>();
    
    // システム初期化
    public void init(){
        nowDrawingWindow = "Title";
        windows.put("Title", new TitleWindow());
        
        addKeyListener(this);
    }

    // スレッド初期化
    public void start(){
        drawThread = new Thread(this);
        drawThread.start();
    }

    // 画面更新担当
    public void run(){
        try{
            repaint();
            Thread.sleep(10); 
        }catch(InterruptedException e){}
    }

    // 描画画面切り替え
    public static boolean changeWindow(String windowID){
        if(windows.containsKey(windowID)){
            nowDrawingWindow = windowID;
            return true;
        }else{
            return false;
        }
    }
    
    // 各画面の描画メソッドを呼ぶ
    public void paint(Graphics g){
        windows.get(nowDrawingWindow).draw(g); 
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
