package ����ʶ��;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.transform.Templates;

/**
 * ����ʶ��ϵͳ
 * 
 * @author С��
 * ʱ�䣺2016��9��11��
 */
public class Demo extends JPanel{
	
	static CarSignUtil myCarSignUtil ;
	static ImageTemplate template ;
	public static void main(String[] args)  throws Exception {
		
		myCarSignUtil = new CarSignUtil();
		template = new ImageTemplate();
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
		
		g.drawImage(myCarSignUtil.getImage(template.templateImage),500, 700, 200, 200, null);
		
		for (int i = 0; i < 7; i++) {
			g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[i]),1000, i*130, 100, 100, null);
		}
		
//		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[1]),1000, 0, 100, 100, null);
//		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[2]),1000, 0, 100, 100, null);
//		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[3]),1000, 0, 100, 100, null);
//		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[4]),1000, 0, 100, 100, null);
//		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[5]),1000, 0, 100, 100, null);
//		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[6]),1000, 0, 100, 100, null);
//		g.drawImage(myCarSignUtil.getImage(myCarSignUtil.charImage[7]),1000, 0, 100, 100, null);
		
		g.drawImage(myCarSignUtil.extractData(),400, 420, myCarSignUtil.extractData().getWidth(), myCarSignUtil.extractData().getHeight(), null);
		
	}
}
