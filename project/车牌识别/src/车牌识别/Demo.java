package ����ʶ��;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * ����ʶ��ϵͳ
 * 
 * @author С��
 * ʱ�䣺2016��9��11��
 */
public class Demo extends JPanel{
	
	static CarSignUtil myCarSignUtil ;
	public static void main(String[] args)  throws Exception {
		
		myCarSignUtil = new CarSignUtil();
		test();
	}
	
	/**
	 * ����һ�����ڣ��Դ�������е�ͼƬ���в���
	 */
	public static void test(){
		JFrame mFrame = new JFrame();
		mFrame.setVisible(true);
		mFrame.setSize(1500,1000);
		mFrame.add(new Demo());
		
	}
	
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.imageBW), 0, 20, 400, 400, null);
		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.imgFill), 400, 20, 400, 400, null);
		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.imgerode),0, 420, 400, 400, null);
		
		g.drawImage(myCarSignUtil.extractData(),400, 420, myCarSignUtil.extractData().getWidth(), myCarSignUtil.extractData().getHeight(), null);
		
	}
}
