package java联通图像计数;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class test {

	public static void main(String[] args) throws IOException {
		
//		new bwlable(getCircle(10));
		BufferedImage mBufferedImage = ImageIO.read(new File("specialGray.jpg"));
		
		int image[][];
		image = new int[mBufferedImage.getWidth()][mBufferedImage.getHeight()];
		int numRGB = 0xff222222;
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				if (mBufferedImage.getRGB(i, j) > numRGB)	image[i][j] = 1;
				else image[i][j] = 0;
			}
		}
		new bwlable(image);
		
	}
	
//	private static int[][] getCircle(int i) {
//		// TODO Auto-generated method stub
//		int Circle[][];
//		Circle = new int[i][i];
//		
//		for (int j = 0; j < Circle.length; j++) {
//			for (int j2 = 0; j2 < Circle[0].length; j2++) {
//				if (Math.abs((i/2-j)*(i/2-j)+(i/2-j2)*(i/2-j2)) <= (i/2)*(i/2)) {
//					Circle[j][j2] = 0;
//				}
//				else Circle[j][j2] = 1;
//			}
//		}
//		return Circle;
//	}
}
