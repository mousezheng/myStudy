package 简单像素操作学习;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class getRGB {
	
/*
 * 读取图片RGB信息
 * 类似于matlab中的imread
 * 
 * 此函数作用为：
 * （1）接受图片路径，
 * （2）读取图片内容，即像素点所对应的RGB值
 * 
 * 输入：
 * 		图片的路径
 * 
 * 输出：  二维数组
 * 
 * **普通图片各个像素编码一共  8x4个二进制位，也就是8个16进制数，即  ARGB  各两位
 * 
 * 此处去掉开头两位  使用方法     ARGB  &  0111
 * 
 * 输出二维数组为  RGB
 * 
 * 
 */
	public int[][] getImageGRB(String filePath) {
	    File file  = new File(filePath);
	    int[][] result = null;
	    if (!file.exists()) {
	         return result;
	    }
	    try {
	         BufferedImage bufImg = ImageIO.read(file);
	         int height = bufImg.getHeight();
	         int width = bufImg.getWidth();
	         
	         System.out.println(height);
	         System.out.println(width);
	         result = new int[width][height];
	        // System.out.println(result.length);
	         
	        // FileWriter outFile = new FileWriter("C:\\out.txt",true);
	         for (int i = 0; i < width; i++) {
	              for (int j = 0; j < height; j++) {
	                    result[i][j] = bufImg.getRGB(i, j) & 0xFFFFFF;
	                   // System.out.print((bufImg.getRGB(i, j) & 0xFFFFFF)+ " ");
	                    
//测试语句
//	                    System.out.print(HexString(result[i][j]));
//	                    outFile.write(HexString(result[i][j])+" ");
	              }
	              System.out.println();
	         }
//	         outFile.close();
	    } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	    }
	    
	    return result;
	}

//*********************
/*
 * 将输入的十进制数转化为16进制的字符串输出
 */
//	private String HexString(int i) {
//		// TODO Auto-generated method stub
//		int tempi = i;
//		char[] tempc;
//		tempc = new char[6];
//		int n = 5;
//		
//		while(tempi>15)
//		{
//			
//			
//				switch (tempi%16) {
//				case 15: tempc[n] = 'F';
//					
//					break;
//				case 14: tempc[n] = 'E';
//				
//					break;
//				case 13: tempc[n] = 'D';
//				
//					break;
//				case 12: tempc[n] = 'C';
//				
//					break;
//				case 11: tempc[n] = 'B';
//				
//					break;
//				case 10: tempc[n] = 'A';
//				
//					break;
//				case 9: tempc[n] = '9';
//				
//					break;
//				case 8: tempc[n] = '8';
//				
//					break;
//				case 7: tempc[n] = '7';
//				
//					break;
//				case 6: tempc[n] = '6';
//				
//					break;
//				case 5: tempc[n] = '5';
//				
//					break;
//				case 4: tempc[n] = '4';
//				
//					break;
//				case 3: tempc[n] = '3';
//				
//					break;
//				case 2: tempc[n] = '2';
//				
//					break;
//				case 1: tempc[n] = '1';
//				
//					break;
//				case 0: tempc[n] = '0';
//				
//					break;		
//				default:
//					break;
//			} //switch (tempi%16)
//			
//			tempi=tempi/16;
//			n--;
//		}//while(tempi>15)
//		return String.valueOf(tempc);
//	}
	
	
}














