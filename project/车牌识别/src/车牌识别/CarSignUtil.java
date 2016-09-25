package 车牌识别;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * 车牌识别工具类，包含对图像处理的各种函数 主要包括： （1）对车牌信息的提取，二值化等相关处理； （2）对提取到的矩形图片进行分割处理
 * 
 * @author 小北 时间：2016年9月11日
 */
public class CarSignUtil {

	public int[][] imageRGB = null;
	int[][] imageBW = null;
	int[][] imgerode = null;
	int[][] imgFill = null;
	int[][] newGrayImageData;
	BufferedImage buffImage;
	BufferedImage dataImage;
	int[][] licenseData = null;
	int[][][] charImage = null; 
	
	
	static Logger log = Logger.getLogger("随意打");

	public CarSignUtil() throws Exception {
		// TODO Auto-generated constructor stub
		imreadRGB("image/car4.jpg");
		specialColor();
		imageBW = myImageFile(30);
		imerode();
		
		extractData();
		blockImage();
		charImage = data2Image();
		
	}

	/**
	 * 通过图片领获取图片信息，将图片信息存入 imageRGB 中
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public int[][] imreadRGB(String fileName) throws Exception {
		// 读取图片信息
		File file = new File(fileName);
		buffImage = ImageIO.read(file);
		// 将图片信息存入矩阵imageRGB
		imageRGB = new int[buffImage.getWidth()][buffImage.getHeight()];
		for (int i = 0; i < imageRGB.length; i++)
			for (int j = 0; j < imageRGB[0].length; j++)
				imageRGB[i][j] = buffImage.getRGB(i, j);

		log.info("成功获得图片信息，保存imageRGB中");
		return imageRGB;
	}

	/**
	 * 二值化 ，通过特殊色彩方法进行二值化处理
	 * 
	 * @param imageRGB
	 * @return
	 */
	public int[][] specialColor() {
		int r;
		int g;
		int b;
		int data;
		imageBW = new int[imageRGB.length][imageRGB[0].length];

		for (int i = 0; i < imageRGB.length; i++) {
			for (int j = 0; j < imageRGB[0].length; j++) {
				data = imageRGB[i][j];
				r = (data >> 16) & 0xff;
				g = (data >> 8) & 0xff;
				b = (data >> 0) & 0xff;

				// 测试
				// if (j == imageRGB[0].length/2) {
				// System.out.println(data + " " + r + " " + g + " " + b + "( "
				// + i+ "  "+j+" )");
				// }
				// 色彩提取判断逻辑（这可能就是深度学习搞出来的权值吧，手动深度学习0.0 ）
				// if (b > 130 & g < 100 & r < 100) ) {
				if ((b > 150 & g < 160 & r < 50)
						|| (b < 80 & g < 200 & g > 100 & r > 100)) {
					imageBW[i][j] = 1;
					// System.out.println("      "+ i +" "+ j );
					continue;

				}
				imageBW[i][j] = 0;
			}
		}

		log.info("特殊色彩提取完成");
		return imageRGB;
	}

	/**
	 * 对图像矩阵进行横向填充分析
	 * 
	 * 设计思路为： 对每个像素左右进行访问（访问距离大约为图像的 1/10）暂定 10 + 5 满足项条件的像素点填充为白色
	 * 
	 */
	public int[][] myImageFile(int K) {
		int counter = 0;

		// 创建一个新的矩阵，防止与之前矩阵发生冲突
		imgFill = new int[imageBW.length][imageBW[0].length];
		for (int i = 0; i < imageBW.length; i++) {
			for (int j = 0; j < imageBW[0].length; j++) {
				imgFill[i][j] = imageBW[i][j];
			}
		}

		for (int i = 0; i < imageBW.length; i++) {
			for (int j = 0; j < imageBW[0].length; j++) {
				if (i < K || j < K || i > imageBW.length - K - 1
						|| j > imageBW[0].length - K - 1) {
					imgFill[i][j] = 0;
					imageBW[i][j] = 0;
					continue;
				}
				/*
				 * 横向填充
				 */
				counter = 0;
				if (imageBW[i][j] == 1) {
					for (int x = 1; x < K; x++) {
						if (imageBW[i - x][j] == 1)
							counter++;
						if (imageBW[i + x][j] == 1)
							counter++;
					} // x
				}
				if (counter > K / 3) {
					for (int x = 1; x < K; x++) {
						imgFill[i - x][j] = 1;
						imgFill[i + x][j] = 1;
					} // x
				}

			} // j
		} // i
		return imgFill;
	}

	/**
	 * 形态学腐蚀操作（此处可不用）
	 */
	public void imerode() {
		int[][] origin = getCircle(5);
		int k = 10;

		// 创建一个新的矩阵 imgerode ，代替imageBW
		imgerode = new int[imageBW.length][imageBW[0].length];
		for (int i = 0; i < imageBW.length; i++) {
			for (int j = 0; j < imageBW[0].length; j++) {
				imgerode[i][j] = imageBW[i][j];
			}
		}

		// 核心逻辑实现算法，
		for (int i = 0; i < imageBW.length; i++) {
			for (int j = 0; j < imageBW[0].length; j++) {
				if (i <= k || j <= k || i >= imageBW.length - k
						|| j >= imageBW.length - k) {
					imgerode[i][j] = 0;
					continue;
				}
				if (imageBW[i][j] == 1
						&& (imageBW[i + 1][j] != 1 || imageBW[i][j + 1] != 1
								|| imageBW[i - 1][j] != 1 || imageBW[i][j - 1] != 1)) {

					for (int l = 0; l < origin.length; l++) {
						for (int l2 = 0; l2 < origin[0].length; l2++) {
							// imageBW.setRGB(i-origin.length/2,
							// j-origin[0].length/2,
							// grayImage.getRGB(i-origin.length/2,
							// j-origin[0].length/2)
							// & origin[l][l2]);
							// 当origin比较大时，可能出现对边界以外元素的访问
							if (origin[l][l2] == 0) {
								imgerode[i - l + origin.length / 2][j - l2
										+ origin[0].length / 2] = 0;
							}

						}
					}

				}

			}// for (int j = 0; j < imageBW.getHeight(); j++)
		}// for (int i = 0; i < imageBW.getWidth(); i++)

	}

	/**
	 * 得到一个矩阵，白色背景，中间有一个黑色圆形区域，
	 * 
	 * @param i
	 * @return
	 */
	private int[][] getCircle(int i) {
		// TODO Auto-generated method stub
		int Circle[][];
		Circle = new int[i][i];

		for (int j = 0; j < Circle.length; j++) {
			for (int j2 = 0; j2 < Circle[0].length; j2++) {
				if (Math.abs((i / 2 - j) * (i / 2 - j) + (i / 2 - j2)
						* (i / 2 - j2)) <= (i / 2) * (i / 2)) {
					Circle[j][j2] = 0;
				} else
					Circle[j][j2] = 1;
			}
		}
		return Circle;
	}



	/**
	 * 车牌信息提取，此处不考虑特殊情况 例如，提取出现的干扰，未能屏蔽
	 * 
	 * @return
	 */
	public BufferedImage extractData() {

		int control = 2;
		
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
//		for (int i = control; i < imgerode.length - control; i++) {
//			for (int j = control; j < imgerode[0].length - control; j++) {
//				if (imgerode[i][j] == 1) {
//					if (scanning(10, i, j, 1) && scanning(10, i, j, 3)
//							&& scanning(10, i, j, 2) && scanning(10, i, j, 4)) {
//						x1 = i;
//						y1 = j;
//					}
//	
//					if (scanning(10, i, j, 1) && scanning(10, i, j, 3)
//							&& !scanning(10, i, j, 2) && !scanning(10, i, j, 4)) {
//						x2 = i;
//						y2 = j;
//					}
//					
//					if (imgerode[i-2][j] == 0 && imgerode[i][j-2] == 0) {
//						x1 = i;
//						y1 = j;
//					}
//					if (imgerode[i][j+2] == 0 && imgerode[i+2][j] == 0) {
//						x2 = i;
//						y2 = j;
//					}
//				}
//			}
//		}
		lable:for (int i = control; i < imgerode.length - control; i++) {
			for (int j = control; j < imgerode[0].length - control; j++) {
				if (imgerode[i][j] == 1) {
					x1 = i;
					y1 = j;
					break lable;
				}
			}
		}
		for (int i = control; i < imgerode.length - control; i++) {
			for (int j = control; j < imgerode[0].length - control; j++) {
				if (imgerode[i][j] == 1) {
					x2 = i;
					y2 = j;
				}
			}
		}
		
		dataImage = new BufferedImage(Math.abs(x2 - x1), Math.abs(y2 - y1),
				buffImage.getType());
		for (int i = 0; i < dataImage.getWidth(); i++) {
			for (int j = 0; j < dataImage.getHeight(); j++) {
				dataImage.setRGB(i, j, buffImage.getRGB(i + x1, j + y1));
			}
		}

		
//		测试RGB情况， 车牌上的字为白色或者黑色
		/*
		 * R/G/B > 180	(针对于蓝底白字的)
		 * 
		 * R/G/B > 80	（黄底黑字的情况和上相反）
		 * 
		 */
//		int r,g,b;
//		for (int i = 0; i < dataImage.getWidth(); i++) {
//			int data = dataImage.getRGB(i, dataImage.getHeight()/2);
//			r = (data >> 16) & 0xff;
//			g = (data >> 8) & 0xff;
//			b = (data >> 0) & 0xff;
//			System.out.println(r + " " + g + " " + b);
//		}
//		
		return dataImage;

	}

	/**
	 * 主要针对蓝底白字进行提取分析
	 * @return
	 */
	public int[][] blockImage(){
		
		int[] writeColumns = new int[dataImage.getWidth()];
		int[] blueColumns = new int[dataImage.getWidth()];
		int[] writeLines = new int[dataImage.getHeight()];
		int[] blueLines = new int[dataImage.getHeight()];
		int startLine = 0,endLine = 0;
		int startColumns[] = new int[12];	//存放纵向扫的描结果，单个数据的开头
		int endColumns[] = new int[12];		//存放横向扫描的结果，单个数据的结束
		int start_end[] = new int[12];
		int conut = 0,sum = 0;
		int temp1 = 0,temp2 = 0;		
		int r,g,b;
		
		for (int i = 0; i < dataImage.getWidth(); i++) {
			for (int j = 0; j < dataImage.getHeight(); j++) {
				int data = dataImage.getRGB(i,j);
				r = (data >> 16) & 0xff;
				g = (data >> 8) & 0xff;
				b = (data >> 0) & 0xff;
				if (b > 150 & g < 160 & r < 50)		blueColumns[i]++;
				if (b > 180 & g > 180 & r > 180)	writeColumns[i]++;
				
			}
//			System.out.println(i + "  " + blueColumns[i] + " " + writeColumns[i]);
		}
		
		for (int j = 0; j < dataImage.getHeight(); j++) {
			for (int i = 0; i < dataImage.getWidth(); i++) {
				int data = dataImage.getRGB(i,j);
				r = (data >> 16) & 0xff;
				g = (data >> 8) & 0xff;
				b = (data >> 0) & 0xff;
				if (b > 150 & g < 160 & r < 50)		blueLines[j]++;
				if (b > 150 && g > 150 && r > 150)	writeLines[j]++;
				
				
			}
//			System.out.println(j + "  " + blueLines[j] + " " + writeLines[j]);
		}
		
		boolean sign = true;
		for (int i = 0; i < writeLines.length/2; i++) {
			
			if (writeLines[i] ==  0) {
				sign = true;
			}
			
			if (sign && writeLines[i] > 20 ) {
					startLine = i;
					sign = false;
			}
		}
		
		sign = true;
		for (int i = writeLines.length-1; i > writeLines.length/2; i--) {
			if (writeLines[i] ==  0) {
				sign = true;
			}
			
			if (sign && writeLines[i] > 20 ) {
					endLine = i;
					sign = false;
			}
		}
		

		for (int i = 1; i < writeColumns.length; i++) {
			if (temp1>=11) {
				System.err.println("字符提取数超出预期");
				break;
			}
			if (writeColumns[i-1] <= 5 && writeColumns[i] > 5) 	startColumns[++temp1] = i;			
			if (writeColumns[i-1] >= 5 && writeColumns[i] < 5)	endColumns[++temp2] = i;
		}
		
		//初始化记录单个字符宽度的数组
		for (int j = 0; j < start_end.length; j++) 
			start_end[j] = endColumns[j] - startColumns[j];
		
		for (int i = 0; i < start_end.length; i++)
			if (start_end[i] > 5) {
				sum = sum + start_end[i];
				conut++;
			}
		
		sum = sum/conut;
		conut = 0;
		
		for (int i = 0; i < start_end.length; i++) {
			if (start_end[i] >= sum/2 && start_end[i] < sum*2) {
				conut++;
			}
		}
		int startChar[] = new int[conut];
		int endChar[] = new int[conut];
		conut = 0;
		for (int i = 0; i < start_end.length; i++) {
			if (start_end[i] >= sum/2 && start_end[i] < sum*2) {
				startChar[conut] = startColumns[i];
				endChar[conut] = endColumns[i];
				conut++;
				
//				System.out.println(startColumns[i] + "  " + endColumns[i] + "  " + (startColumns[i]-endColumns[i]));
			}
		}
		
		licenseData = new int[conut][4];
//		System.out.println(startLine + "  " + endLine);
		for (int i = 0; i < conut; i++) {
			licenseData[i][0] = startLine;
			licenseData[i][1] = endLine;
			licenseData[i][2] = startChar[i];
			licenseData[i][3] = endChar[i];
		}
		
		return licenseData;
	}
	
	
	public int[][][] data2Image(){
		
		int width = 0,highly = 0;
		int r,g,b;
		
		if (licenseData.length  != 7) {
			System.err.println("提取车牌信息有异常");
			return null;
		}
		for (int i = 0; i < licenseData.length; i++) {
			if (width < (licenseData[i][1]-licenseData[i][0])) 	
				width = licenseData[i][1]-licenseData[i][0];
			
			if (highly < (licenseData[i][3]-licenseData[i][2])) 	
				highly = licenseData[i][3]-licenseData[i][2];
		}
//		System.out.println(licenseData.length + "  "  + width + "  " + highly);
		charImage = new int[licenseData.length][highly][width];
		
		for (int t = 0; t < licenseData.length; t++) {
			for (int i = licenseData[t][0]; i < licenseData[t][1]; i++) {
				for (int j = licenseData[t][2]; j < licenseData[t][3]; j++) {
					int data = dataImage.getRGB(j,i);
					r = (data >> 16) & 0xff;
					g = (data >> 8) & 0xff;
					b = (data >> 0) & 0xff;
//					if (b > 150 && g < 160 && r < 50)		charImage[t][j-licenseData[t][2]][i-licenseData[t][0]] = 0;
					if (b > 150 && g > 150 && r > 150)		charImage[t][j-licenseData[t][2]][i-licenseData[t][0]] = 1;
				}
			}
		}
		
		return charImage;
	}
	
/*
 *	
 *	//	左右扫描，排除简单的排除干扰点
 *	private boolean scanning(int control, int i, int j, int direction) {
 *		int conut = 0;
 *
 *		switch (direction) {
 *		case 1:
 *			for (int t = 0; t < control; t++) {
 *				if (imgerode[i - t][j] == 0)
 *					conut++;
 *			}
 *			if (conut > control / 2)
 *				return true;
 *			break;
 *		case 2:
 *			for (int t = 0; t < control; t++) {
 *				if (imgerode[i + t][j] == 1)
 *					conut++;
 *			}
 *			if (conut > control / 2)
 *				return true;
 *			break;
 *		case 3:
 *			for (int t = 0; t < control; t++) {
 *				if (imgerode[i][j - t]  == 0)
 *					conut++;
 *			}
 *			if (conut > control / 2)
 *				return true;
 *			break;
 *		case 4:
 *			for (int t = 0; t < control; t++) {
 *				if (imgerode[i][j + t] == 1)
 *					conut++;
 *			}
 *			if (conut > control / 2)
 *				return true;
 *			break;
 *		default:
 *			break;
 *		}
 *
 *		return false;
 *	}
 *
 */
	
	
	
	
	/**
	 * 将二值图像转换为图片
	 * 
	 */
	public BufferedImage getImage(int[][] matrix) {

		BufferedImage image = new BufferedImage(matrix.length,
				matrix[0].length, 
				BufferedImage.TYPE_INT_BGR);

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] != 1)
					image.setRGB(i, j, 0xff000000);
				else
					image.setRGB(i, j, 0xffffffff);
			}
		}
		System.out.println(matrix.length + "  " + matrix[0].length);

		return image;
	}
	/**
	 * 提取车牌信息 特殊色彩的提取方法，主要针对 黄色车牌和蓝色车牌的提取. 注意：黄色车子和蓝色车子可能会有干扰，
	 * 
	 * @param imageRGB
	 * @return
	 */

}
