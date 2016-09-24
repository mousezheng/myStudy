package ����ʶ��;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * ����ʶ�𹤾��࣬������ͼ����ĸ��ֺ��� ��Ҫ������ ��1���Գ�����Ϣ����ȡ����ֵ������ش��� ��2������ȡ���ľ���ͼƬ���зָ��
 * 
 * @author С�� ʱ�䣺2016��9��11��
 */
public class CarSignUtil {

	public int[][] imageRGB = null;
	int[][] imageBW = null;
	int[][] imgerode = null;
	int[][] imgFill = null;
	int[][] newGrayImageData;
	BufferedImage buffImage;
	BufferedImage dataImage;
	
	static Logger log = Logger.getLogger("�����");

	public CarSignUtil() throws Exception {
		// TODO Auto-generated constructor stub
		imreadRGB("image/car4.jpg");
		specialColor();
		imageBW = myImageFile(30);
		imerode();
		
		extractData();
		blockImage();
	}

	/**
	 * ͨ��ͼƬ���ȡͼƬ��Ϣ����ͼƬ��Ϣ���� imageRGB ��
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public int[][] imreadRGB(String fileName) throws Exception {
		// ��ȡͼƬ��Ϣ
		File file = new File(fileName);
		buffImage = ImageIO.read(file);
		// ��ͼƬ��Ϣ�������imageRGB
		imageRGB = new int[buffImage.getWidth()][buffImage.getHeight()];
		for (int i = 0; i < imageRGB.length; i++)
			for (int j = 0; j < imageRGB[0].length; j++)
				imageRGB[i][j] = buffImage.getRGB(i, j);

		log.info("�ɹ����ͼƬ��Ϣ������imageRGB��");
		return imageRGB;
	}

	/**
	 * ��ֵ�� ��ͨ������ɫ�ʷ������ж�ֵ������
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

				// ����
				// if (j == imageRGB[0].length/2) {
				// System.out.println(data + " " + r + " " + g + " " + b + "( "
				// + i+ "  "+j+" )");
				// }
				// ɫ����ȡ�ж��߼�������ܾ������ѧϰ�������Ȩֵ�ɣ��ֶ����ѧϰ0.0 ��
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

		log.info("����ɫ����ȡ���");
		return imageRGB;
	}

	/**
	 * ��ͼ�������к���������
	 * 
	 * ���˼·Ϊ�� ��ÿ���������ҽ��з��ʣ����ʾ����ԼΪͼ��� 1/10���ݶ� 10 + 5 ���������������ص����Ϊ��ɫ
	 * 
	 */
	public int[][] myImageFile(int K) {
		int counter = 0;

		// ����һ���µľ��󣬷�ֹ��֮ǰ��������ͻ
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
				 * �������
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
	 * ��̬ѧ��ʴ�������˴��ɲ��ã�
	 */
	public void imerode() {
		int[][] origin = getCircle(5);
		int k = 10;

		// ����һ���µľ��� imgerode ������imageBW
		imgerode = new int[imageBW.length][imageBW[0].length];
		for (int i = 0; i < imageBW.length; i++) {
			for (int j = 0; j < imageBW[0].length; j++) {
				imgerode[i][j] = imageBW[i][j];
			}
		}

		// �����߼�ʵ���㷨��
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
							// ��origin�Ƚϴ�ʱ�����ܳ��ֶԱ߽�����Ԫ�صķ���
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
	 * �õ�һ�����󣬰�ɫ�������м���һ����ɫԲ������
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
	 * ����ֵͼ��ת��ΪͼƬ
	 * 
	 */
	public BufferedImage getImage(int[][] matrix) {

		BufferedImage image = new BufferedImage(matrix.length,
				matrix[0].length, 5);

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] != 1)
					image.setRGB(i, j, 0xff000000);
				else
					image.setRGB(i, j, 0xffffffff);
			}
		}

		return image;
	}

	/**
	 * ������Ϣ��ȡ���˴�������������� ���磬��ȡ���ֵĸ��ţ�δ������
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

		
//		����RGB����� �����ϵ���Ϊ��ɫ���ߺ�ɫ
		/*
		 * R/G/B > 180	(��������װ��ֵ�)
		 * 
		 * R/G/B > 80	���Ƶ׺��ֵ���������෴��
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
	 * ��Ҫ������׺������
	 * @return
	 */
	public BufferedImage[] blockImage(){
		BufferedImage[] licenseData = null;
		int[] writeColumns = new int[dataImage.getWidth()];
		int[] blueColumns = new int[dataImage.getWidth()];
		int[] writeLines = new int[dataImage.getHeight()];
		int[] blueLines = new int[dataImage.getHeight()];
		
		int r,g,b;
		
		for (int i = 0; i < dataImage.getWidth(); i++) {
			for (int j = 0; j < dataImage.getHeight(); j++) {
				int data = dataImage.getRGB(i,j);
				r = (data >> 16) & 0xff;
				g = (data >> 8) & 0xff;
				b = (data >> 0) & 0xff;
				if (b > 150 & g < 160 & r < 50)		blueColumns[i]++;
				if (b > 180 & g > 180 & r > 180)	blueColumns[i]++;
				
				
			}
//			System.out.println(i + "  " + blueColumns[i] + " " + blueColumns[i]);
		}
		
		for (int j = 0; j < dataImage.getHeight(); j++) {
			for (int i = 0; i < dataImage.getWidth(); i++) {
				int data = dataImage.getRGB(i,j);
				r = (data >> 16) & 0xff;
				g = (data >> 8) & 0xff;
				b = (data >> 0) & 0xff;
				if (b > 150 & g < 160 & r < 50)		blueLines[j]++;
				if (b > 180 & g > 180 & r > 180)	writeLines[j]++;
				
				
			}
			System.out.println(j + "  " + blueLines[j] + " " + writeLines[j]);
		}
		return licenseData;
	}
	
	
	
	/**
	 * ����ɨ�裬�ų��򵥵��ų����ŵ�
	 * 
	 * @return
	 */
	private boolean scanning(int control, int i, int j, int direction) {
		int conut = 0;

		switch (direction) {
		case 1:
			for (int t = 0; t < control; t++) {
				if (imgerode[i - t][j] == 0)
					conut++;
			}
			if (conut > control / 2)
				return true;
			break;
		case 2:
			for (int t = 0; t < control; t++) {
				if (imgerode[i + t][j] == 1)
					conut++;
			}
			if (conut > control / 2)
				return true;
			break;
		case 3:
			for (int t = 0; t < control; t++) {
				if (imgerode[i][j - t]  == 0)
					conut++;
			}
			if (conut > control / 2)
				return true;
			break;
		case 4:
			for (int t = 0; t < control; t++) {
				if (imgerode[i][j + t] == 1)
					conut++;
			}
			if (conut > control / 2)
				return true;
			break;
		default:
			break;
		}

		return false;
	}

	/**
	 * ��ȡ������Ϣ ����ɫ�ʵ���ȡ��������Ҫ��� ��ɫ���ƺ���ɫ���Ƶ���ȡ. ע�⣺��ɫ���Ӻ���ɫ���ӿ��ܻ��и��ţ�
	 * 
	 * @param imageRGB
	 * @return
	 */

}
