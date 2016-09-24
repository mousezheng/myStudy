package 灰度化;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/*
 * 灰度化处理函数，，特殊的灰度化处理，，使用了加权平均数方法，使得处理后的图像更加真实清晰
 */
public class coloroToRGB {
	private static int colorToRGB(int alpha, int red, int green, int blue) {

		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;

		return newPixel;

}
public static void main(String[] args) throws IOException {
	BufferedImage bufferedImage 
		= ImageIO.read(new File("apple.1.1.jpg"));
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
			int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);;
			System.out.println(i + " : " + j + " " + gray);
			int newPixel = colorToRGB(255, gray, gray, gray);
			grayImage.setRGB(i, j, newPixel);
		}
	}
	File newFile = new File("C:/test/app.jpg");
	ImageIO.write(grayImage, "jpg", newFile);
}
}
