package ѧϰGrapics;

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
      
      //��֤���ڹر�ʱ����ֹͣ����
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //���ڿɼ���
      f.setVisible(true);
      
      
   }

   //ϵͳ�ٴ��������ڴ˷����µ��û��� g ����ʹͼ����ʾ�ڴ�����
   public void paint(Graphics g) {
      
		useGrapics myGrapics = new useGrapics();
		myGrapics.paintCompoent(g);
   }
   
   
}