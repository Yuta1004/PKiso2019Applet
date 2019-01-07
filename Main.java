import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/* <applet code="Main.class" width=700 height=700></applet> */

public class Main extends Applet implements Runnable, KeyListener{
    Thread drawThread;
    
    // システム初期化
    public void init(){
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
    public static void changeWindow(){
    
    }
    
    // 各画面の描画メソッドを呼ぶ
    public void paint(Graphics g){
    
    }

    // キー入力(押)
    public void keyPressed(KeyEvent e){
     
    }

    // キー入力(離)
    public void keyReleased(KeyEvent e){

    }

    // キー入力(使わない)
    public void keyTyped(KeyEvent e){}
}
