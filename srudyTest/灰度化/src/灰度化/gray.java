package �ҶȻ�;


//  �ҶȻ�����

/*
 	��ͼƬת��Ϊ�Ҷ�ͼ��
 	
 	�˺�������
 	��1����ͼ
 	��2������������
 	��3�������ظ�ֵ����Ӧ�Ļ�����λ��
 	��4������Ƚ�
 	
 	���룺
 		ͼƬ
 	�����
 		�ҶȻ�����ֵ����ͼƬ
 	
 	
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class gray extends JPanel{

	public static void grayImage(Graphics g) throws IOException{
	   
		//���ļ���ͼƬ�ļ�������Ŀͬ��Ŀ¼��
		File file = new File("specialColor.jpg");
	    BufferedImage image = ImageIO.read(file);

	    int width = image.getWidth();  
	    int height = image.getHeight();  

	    //new һ�� BufferedImage�Ļ�����������ʱ�յģ���ֻ�𵽻������ã�������Ӧ��ͼƬת��
	    BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);     
	    //BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY); 
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = image.getRGB(i, j);  
	        grayImage.setRGB(i, j, rgb);  //�����ش��뻺����
	        
	        }  
	    }  
	    
//		 ��ͼƬ������Ӧ��·���£�	    
//	     File newFile = new File("C:/test/gray.jpg");  
//	     ImageIO.write(grayImage, "jpg", newFile);  
	     
	    //��ͼ
	     g.drawImage(image, 0, 0, 380, 400,null);
	     g.drawImage(grayImage,400,0, 380,400,null);
	}
	
	public static void main(String args[]){
		
		//��������
		JFrame mFrame = new JFrame();
		mFrame.setSize(800, 500);
		mFrame.setVisible(true);
		mFrame.add(new gray());
	   	}
	
	//��дpaint ���� ��ͼ
	public void paint(Graphics g){
		
		try {
			//grayImage(g);
			
			new specialColor().processPicture(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
