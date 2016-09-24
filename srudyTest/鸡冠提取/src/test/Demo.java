package test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Demo extends JPanel{

	static BufferedImage myTestImg;
	static SpecialCloro mySpecialColor;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File testFile = new File("testImage.jpg");
		myTestImg = ImageIO.read(testFile);
		
		mySpecialColor = new SpecialCloro(myTestImg);
		test();
	}
	
	
	public static void test(){
		JFrame mFrame = new JFrame();
		mFrame.setVisible(true);
		mFrame.setSize(1200,600);
		mFrame.add(new Demo());
		
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
		g.drawImage(myTestImg,0, 20, 500, 500, null);
		g.drawImage(mySpecialColor.resultImage,500, 20, 500, 500, null);
	}

}
