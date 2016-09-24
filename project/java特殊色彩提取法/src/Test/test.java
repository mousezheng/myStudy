package Test;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import 图像处理函数.bwlable;
import 图像处理函数.imerode;
import 图像处理函数.imfll;
import 图像处理函数.specialColor;

//	(1)首先进行特殊的色彩提取处理，同时将图片做成黑白的二值图像矩阵
//		（矩阵形式传入下面处理）
//		如
//		 *			
//		 *		0 0 0 0 0 0 0 0 0 0
//		 *		0 0 0 0 0 0 0 0 0 0
//		 *		0 0 1 1 1 1 1 1 0 0
//		 *		0 0 1 1 1 1 1 1 0 0
//		 *		0 0 1 1 1 1 1 1 0 0    
//		 *		0 0 1 1 1 1 1 1 0 0
//		 *		0 0 1 1 1 1 1 1 0 0
//		 *		0 0 1 1 1 1 1 1 0 0
//		 *		0 0 0 0 0 0 0 0 0 0
//		 *		0 0 0 0 0 0 0 0 0 0
//	（2）接下来对传入矩阵进行填充操作，填充空白区域	（访问距离）
//	（3）接下来进行腐蚀操作（腐蚀程度）
//	（4）进行联通区域计数（4/8）

public class test extends JPanel{
		static int imageGray[][];
		static specialColor mSpecialColor;
		static imerode mImerode;
		static imfll mImfll;
	public static void main(String[] args) throws IOException {
		
		
		mSpecialColor = new specialColor("test.jpg"); 		//(1)
		imageGray =  mSpecialColor.getImageGray();		
		mImfll = new imfll(imageGray,15);						//(3)
		imageGray = mImfll.getImageGray();
		mImerode = new imerode(imageGray,25);      		  	//(2)
		imageGray = mImerode.getImageGray();
		mImerode = new imerode(imageGray,6);      		  	//(2)
		imageGray = mImerode.getImageGray();
		bwlable mBwlable = new bwlable(imageGray);			//(4)
		
//		for (int i = 0; i < imageGray.length; i++) {
//			for (int j = 0; j < imageGray[0].length; j++) {
//				System.out.print(imageGray[i][j]);
//			}
//			System.out.println();
//		}
//		Jfarm();
	}
	
//	private static void Jfarm() {
//		JFrame mFrame = new JFrame();
//		mFrame.setSize(800, 800);
//		mFrame.setVisible(true);
//		mFrame.add(new test());
//	}
//	
//	@Override
//	public void paint(Graphics arg0) {
//		// TODO Auto-generated method stub
//		super.paint(arg0);
//		
//		new makeImage(arg0);}

}
