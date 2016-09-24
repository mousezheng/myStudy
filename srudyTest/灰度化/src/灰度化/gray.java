package 灰度化;


//  灰度化处理

/*
 	将图片转换为灰度图像
 	
 	此函数作用
 	（1）读图
 	（2）创建缓冲区
 	（3）将像素赋值到相应的缓冲区位置
 	（4）输出比较
 	
 	输入：
 		图片
 	输出：
 		灰度化（二值化）图片
 	
 	
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
	   
		//读文件，图片文件放在项目同级目录下
		File file = new File("specialColor.jpg");
	    BufferedImage image = ImageIO.read(file);

	    int width = image.getWidth();  
	    int height = image.getHeight();  

	    //new 一个 BufferedImage的缓冲区，，即时空的，，只起到缓冲作用，，将相应的图片转换
	    BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);     
	    //BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY); 
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = image.getRGB(i, j);  
	        grayImage.setRGB(i, j, rgb);  //将像素存入缓冲区
	        
	        }  
	    }  
	    
//		 将图片存入相应的路径下：	    
//	     File newFile = new File("C:/test/gray.jpg");  
//	     ImageIO.write(grayImage, "jpg", newFile);  
	     
	    //画图
	     g.drawImage(image, 0, 0, 380, 400,null);
	     g.drawImage(grayImage,400,0, 380,400,null);
	}
	
	public static void main(String args[]){
		
		//创建窗口
		JFrame mFrame = new JFrame();
		mFrame.setSize(800, 500);
		mFrame.setVisible(true);
		mFrame.add(new gray());
	   	}
	
	//重写paint 方法 画图
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
