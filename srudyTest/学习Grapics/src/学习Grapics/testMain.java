package 学习Grapics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class testMain extends JPanel {

   public static void main(String[] a) {
      JFrame f = new JFrame();
      f.setSize(800, 800);
      f.add(new testMain());
      f.setBackground(Color.BLACK);
      
      //保证窗口关闭时程序停止运行
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //窗口可见性
      f.setVisible(true);
      
      
   }

   //系统再带方法，在此方法下调用画笔 g 方可使图像显示在窗体上
   public void paint(Graphics g) {
      
		useGrapics myGrapics = new useGrapics();
		myGrapics.paintCompoent(g);
   }
   
   
}