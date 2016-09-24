package java¶þÖµÍ¼ÏñÌî³ä;

import java.awt.Font;
import java.awt.Graphics;

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
		
		imfll mImfll = new imfll("color1.jpg", g);
		Font myFont = new Font("ËÎÌå", 16, 50);
		g.setFont(myFont);
		g.drawString("llla ", 100, 450);
		
	}
}
