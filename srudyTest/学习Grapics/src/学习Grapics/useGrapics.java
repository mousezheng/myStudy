package ѧϰGrapics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;



public class useGrapics {
	
	
	public void paintCompoent(Graphics g)
	{
		
//  	������***************
//		g.setColor(Color.BLUE);
//		g.drawRect(100, 100, 50, 50);
//		g.dispose();
		
//		����ݾ���************Graphics2d
		
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		g2d.setPaint(Color.red);
//		g.drawRect(100, 100, 50, 50);
//		g.dispose();
		
//		* **  **  **  **  **  **  �ĵ�    **  **  **  **  **  
//		*
//		*  	���ھ�����⣬�Ҳ�֪���˵��������Ķ�����֮�����޷�����
//		*   ������Բ�Σ��������������ϾͿ������ֳ����ˣ�����ݽ�ΪԲ��
//		*
//		* **  **  **  **  **  **  **  **  **  **  **  **
		
//		��������������������������������������
//		����
//		��������������ͼ    
//		#    ֮������Ů
//		��
//		��������������������������������������
		
		int placeX;  //���x����
		int placeY;	 //���y����
		int size;    //��������С��������ֱ������� *��������������С
		
		//������
		for (int i = 0; i < 300; i++) {
			
			placeX = (int) (800*Math.random());
			placeY = (int) (800*Math.random());
			size = (int) (20*Math.random());
			
			g.setColor(Color.WHITE);
			g.drawString("*", placeX, placeY);
		}
		
		//������
		placeX = (int) (750*Math.random());
		g.fillOval(placeX, 100, 100, 100);
		g.setColor(Color.BLACK);
		g.fillOval(placeX-20, 80, 100, 100);	
		
		//������
		g.setColor(Color.WHITE);
		g.fillOval(-400, 710, 1000, 100);
		
		Image mImage = new ImageIcon("walk.png").getImage();
		//g.drawImage(mImage, 500, 500, null);
		g.drawImage(mImage, 80, 500, 300, 300, null);
	}

}




















