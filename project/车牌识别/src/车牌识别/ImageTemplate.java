package ����ʶ��;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * ���
 * 		��50*100 ��ģ���к���ɨ��õ���ֵ����150��
 * @author С��
 * ʱ�䣺2016��9��25��
 */
public class ImageTemplate {
	int[] temolate;
	int[][] templateImage;
	int[][] imageData;
	int[][] tempImageData;
	
	public ImageTemplate() throws Exception{
		temolate = new int[150];
		templateImage = new int[50][100];
		imageData = new int[50][100];
		
		get2Int();
	}
	
	public int[][] get2Int() throws Exception {
		int r;
		int g;
		int b;
		int data;
		// ��ȡͼƬ��Ϣ
		File file = new File("image/shan.jpg");
		BufferedImage shan = ImageIO.read(file);
		for (int i = 0; i < shan.getWidth(); i++) {
			for (int j = 0; j < shan.getHeight(); j++) {
				data = shan.getRGB(i, j);
				r = (data >> 16) & 0xff;
				g = (data >> 8) & 0xff;
				b = (data >> 0) & 0xff;
				
				if (b > 150 && g > 150 && r > 150)		imageData[i][j] = 1;
			}
		}
		
		return templateImage;
	}
	
	/**
	 * ��ͼƬ��׼����50*100��
	 * ��1����������
	 * ��2����������
	 * 
	 */
	public void revision(){
//		int start[] = new int[15];
//		int end[] = new int[15];
		int startConut = -1;
		int width = 0,height = 0;
//		int endConut = -1;
		tempImageData = new int[50][imageData.length];
		
		width = imageData[0].length/50;
		height = imageData.length/100;
		
		//�Ƚ������ҵ�����
		for (int j = 0; j < imageData.length; j++) {
			for (int i = 1; i < imageData[0].length; i++) {
				if (startConut == 15 || endConut == 15) {
					System.err.println("����Խ�磬start��end");
				}
//				if (imageData[j][i-1] == 0  && imageData[i][j] == 1) start[startConut++] = i*width;
//				if (imageData[j][i-1] == 1  && imageData[i][j] == 0) end[endConut++] = i*width;
				
				
			}
			
			
			
		}
	}
	
}




