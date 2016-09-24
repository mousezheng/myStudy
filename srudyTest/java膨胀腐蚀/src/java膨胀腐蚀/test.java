package java≈Ú’Õ∏Ø ¥;

import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class test extends JPanel{
	
	public static void main(String[] args) {
		
		JFrame mFrame = new JFrame();
		mFrame.setVisible(true);
		mFrame.setSize(800, 500);
		mFrame.add(new test());
		
	}
	@Override
	
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		try {
			imerode mImerode = new imerode("imfill.jpg", g);
			//imdilate mImdilate = new imdilate("imfill.jpg", g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	Font myFont = new Font("ÀŒÃÂ", 16, 50);
	//	g.setFont(myFont);
	//	g.drawString("llla ", 100, 450);
		
	}
}
