import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/* <applet code="Main.class" width=700 height=700></applet> */

public class Main extends Applet implements Runnable{
    Thread drawThread;

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
    
    public void paint(Graphics g){
    
    }
}
