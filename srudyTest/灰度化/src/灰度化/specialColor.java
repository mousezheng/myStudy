package 灰度化;


/*
 	(1)输入一张图片（jpg已经测试）
 	(2)创建一个图片的缓存区   grayImage
 	(3)对输入图片各个像素进行处理，
 		逻辑：
 			当red > testnum || green > testnum || blue > testnum
 			red + green < testnum*1.5  &&   red + blue < testnum*1.5
			&& green + blue < testnum*1.5  &&   red + green + blue < testnum*1.5
 		改变此逻辑即可实现对特殊 RGB 的二值化处理
 	(4)输出图片
 	
 	输入：
 		RGB 彩色图片
 	输出：
 		特殊色彩区域为白色，其他区域为黑色
 	
 	
 */


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/*
 * 灰度化处理函数，，特殊的灰度化处理，，使用了加权平均数方法，使得处理后的图像更加真实清晰
 */
public class specialColor {
	private static int colorToRGB(int alpha, int red, int green, int blue) {

		int testnum = 0xbb;
		if (red > testnum || green > testnum || blue > testnum)
			if(red + green < testnum*1.5
				&& red + blue < testnum*1.5
				&& green + blue < testnum*1.5
				&& red + green + blue < testnum*1.5)
				
				return 0xffffffff;
			else return 0xff000000;
		else return 0xff000000;
				
		//	return 0xff000000;	//黑色
		//else return 0xffffffff;	//白色
		

	}
	public void processPicture(Graphics graphics) throws IOException {
		BufferedImage bufferedImage 
			= ImageIO.read(new File("specialColor.jpg"));
		BufferedImage grayImage = 
			new BufferedImage(bufferedImage.getWidth(), 
							  bufferedImage.getHeight(), 
							  bufferedImage.getType());
	
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				final int color = bufferedImage.getRGB(i, j);
				final int r = (color >> 16) & 0xff;
				final int g = (color >> 8) & 0xff;
				final int b = color & 0xff;
				//int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);;
				//System.out.println(i + " : " + j + " " + gray);
				int newPixel = colorToRGB(255, r, g, b);
				grayImage.setRGB(i, j, newPixel);
				
				//System.out.print(r+" "+g+" "+b);return;
			}
		}
		File newFile = new File("C:/test/specialGray.jpg");
		ImageIO.write(grayImage, "jpg", newFile);
		//Runtime.getRuntime().exec("cmd /c start C:/test");
		
		graphics.drawImage(bufferedImage, 0, 0, 380, 400,null);
	    graphics.drawImage(grayImage,400,0, 380,400,null);
	}
}
