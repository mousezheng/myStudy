package test;

import java.awt.image.BufferedImage;

public class SpecialCloro {

	BufferedImage resultImage;
	public SpecialCloro(BufferedImage testImage) {
		
		resultImage = new BufferedImage(testImage.getWidth(), testImage.getHeight(), testImage.getType());
		
		for (int i = 0; i < resultImage.getWidth(); i++) {
			for (int j = 0; j < resultImage.getHeight(); j++) {
				final int color = testImage.getRGB(i, j);
				final int r = (color >> 16) & 0xff;
				final int g = (color >> 8) & 0xff;
				final int b = color & 0xff;
				//int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);;
				//System.out.println(i + " : " + j + " " + gray);
				int newPixel = colorToRGB(r, g, b);
				//grayImage.setRGB(i, j, newPixel);
				resultImage.setRGB(i, j, newPixel);
				//System.out.print(r+" "+g+" "+b);return;
				
				if (i == 500)  System.out.println(r+"  "+g+"  "+b);
			}
		}
	}
		
		private static int colorToRGB( int red, int green, int blue) {

//			int testnum = 150;
			if (red > 100 && green < 100 && blue < 100)					
						return 0xffffffff;
			else return 0xff000000;
					
//					return 0xffffffff;
//				else return 0xff000000;
//			else return 0xff000000;
					
			//	return 0xff000000;	//ºÚÉ«
			//else return 0xffffffff;	//°×É«
			
		}
}
