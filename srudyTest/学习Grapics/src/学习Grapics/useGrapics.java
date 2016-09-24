package 学习Grapics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;



public class useGrapics {
	
	
	public void paintCompoent(Graphics g)
	{
		
//  	画矩形***************
//		g.setColor(Color.BLUE);
//		g.drawRect(100, 100, 50, 50);
//		g.dispose();
		
//		反锯齿矩形************Graphics2d
		
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		g2d.setPaint(Color.red);
//		g.drawRect(100, 100, 50, 50);
//		g.dispose();
		
//		* **  **  **  **  **  **  心得    **  **  **  **  **  
//		*
//		*  	关于锯齿问题，我不知如何说，输出来的东西总之肉眼无法区别
//		*   但是在圆形，或者其他曲线上就可以体现出来了，反锯齿较为圆滑
//		*
//		* **  **  **  **  **  **  **  **  **  **  **  **
		
//		＃　＃＃　＃＃　＃＃　＃＃　＃＃　＃＃
//		＃　
//		＃　　　满天星图    
//		#    之月下少女
//		＃
//		＃　＃＃　＃＃　＃＃　＃＃　＃＃　＃＃
		
		int placeX;  //存放x坐标
		int placeY;	 //存放y坐标
		int size;    //存放字体大小，星星是直接输出的 *，用字体控制其大小
		
		//画星星
		for (int i = 0; i < 300; i++) {
			
			placeX = (int) (800*Math.random());
			placeY = (int) (800*Math.random());
			size = (int) (20*Math.random());
			
			g.setColor(Color.WHITE);
			g.drawString("*", placeX, placeY);
		}
		
		//画月亮
		placeX = (int) (750*Math.random());
		g.fillOval(placeX, 100, 100, 100);
		g.setColor(Color.BLACK);
		g.fillOval(placeX-20, 80, 100, 100);	
		
		//画地面
		g.setColor(Color.WHITE);
		g.fillOval(-400, 710, 1000, 100);
		
		Image mImage = new ImageIcon("walk.png").getImage();
		//g.drawImage(mImage, 500, 500, null);
		g.drawImage(mImage, 80, 500, 300, 300, null);
	}

}




















