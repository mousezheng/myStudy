package ͼ������;



/*
 	(1)����һ��ͼƬ��jpg�Ѿ����ԣ�
 	(2)����һ��ͼƬ�Ļ�����   grayImage
 	(3)������ͼƬ�������ؽ��д���
 		�߼���
 			��red > testnum || green > testnum || blue > testnum
 			red + green < testnum*1.5  &&   red + blue < testnum*1.5
			&& green + blue < testnum*1.5  &&   red + green + blue < testnum*1.5
 		�ı���߼�����ʵ�ֶ����� RGB �Ķ�ֵ������
 	(4)���ͼƬ
 	
 	���룺
 		RGB ��ɫͼƬ
 	�����
 		����ɫ������Ϊ��ɫ����������Ϊ��ɫ
 	
 	
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/*
 * ������ɫ��ȡ
 */
public class specialColor {
	
	BufferedImage bufferedImage;
	int[][] imageGray;
	public specialColor(String file) throws IOException {
		// TODO Auto-generated constructor stub
		bufferedImage = ImageIO.read(new File(file));
		imageGray = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
		processPicture();
	}
	private static int colorToRGB(int alpha, int red, int green, int blue) {

		int testnum = 0xbb;
		if (red > testnum || green > testnum || blue > testnum)
			if(red + green < testnum*1.5
				&& red + blue < testnum*1.5
				&& green + blue < testnum*1.5
				&& red + green + blue < testnum*1.5)
				
					return 1;
			else return 0;
		else return 0;
				
//				return 0xffffffff;
//			else return 0xff000000;
//		else return 0xff000000;
				
		//	return 0xff000000;	//��ɫ
		//else return 0xffffffff;	//��ɫ
		
	}
	public void processPicture() throws IOException {
		
		
	
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				final int color = bufferedImage.getRGB(i, j);
				final int r = (color >> 16) & 0xff;
				final int g = (color >> 8) & 0xff;
				final int b = color & 0xff;
				//int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);;
				//System.out.println(i + " : " + j + " " + gray);
				int newPixel = colorToRGB(255, r, g, b);
				//grayImage.setRGB(i, j, newPixel);
				imageGray[i][j] = newPixel;
			
				//System.out.print(r+" "+g+" "+b);return;
			}
		}
		
	//	File newFile = new File("C:/test/specialGray.jpg");
	//	ImageIO.write(grayImage, "jpg", newFile);
	//	//Runtime.getRuntime().exec("cmd /c start C:/test");
	//	
	//	graphics.drawImage(bufferedImage, 0, 0, 380, 400,null);
	//    graphics.drawImage(grayImage,400,0, 380,400,null);
	}
	public int[][] getImageGray(){
		return imageGray;
	}
}
