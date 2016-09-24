package java��ͬͼ�����;



import java.util.*;

class StackItem 
{
	public int row;
	public int col;
	public StackItem(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

}
public class copyStudy
{

	/**
	 * �Զ�ֵͼ����ͨ������б��
	 * 
	 * @param pnBW
	 *            ��ֵͼ�� 0 1
	 * @param pnBWLabel
	 *            pnBWLabel��Ǻ��ͼ�� 1 2 3 4...��ͬ������ͬ��ֵ δ����ǵ�����Ҳ���Ǳ���Ϊ0
	 * @param nImageWidth
	 *            ͼ���
	 * @param nImageHeight
	 *            ͼ���
	 * @return ��ֵͼ����������
	 */
	int BWLabel(byte pnBW[], int pnBWLabel[], short nImageWidth, short nImageHeight)
	{
		int k = 0, n = 0, L = 0, R = 0, LL = 0, RR = 0, N = 0, X = 0, X1 = 0, nFlag = 0;
		int nWidthPadded = nImageWidth + 2, nHeightPadded = nImageHeight + 2;

		byte pnBWPadded[] = new byte[nWidthPadded * nHeightPadded];//��ֵͼ�� ����0��� �������� ����һ�л���
		int pnSegNumPerLine[] = new int[nHeightPadded];//��¼ÿ���ж��ٶ�
		/**
		 * ��pnBWֵ ����pnBWPadded
		 */
		for (int i = 0; i < nImageHeight; ++i)
		{
			int src = i * nImageWidth;
			int dst = (i + 1) * nWidthPadded + 1;
			for (int j = 0; j < nImageWidth; ++j) 
			{
				pnBWPadded[dst++] = pnBW[src++];
			}
		}
		/**
		 * ͳ��ÿ���ж��ٶ�
		 * �ж����� [i j]��ֵΪ0 [i j+1]��Ϊ0
		 */
		for (int i = 1; i <= nImageHeight; ++i) {
			for (int j = 0; j < nWidthPadded - 1; ++j) {
				if ((pnBWPadded[i * nWidthPadded + j + 1] != 0)&& (pnBWPadded[i * nWidthPadded + j] == 0)) 
				{
					++pnSegNumPerLine[i];
				}
			}
		}
		/**
		 *��������ͳ�Ƶ�ÿ���ж���С�ζ� Ϊÿһ�е��߶ζ˵�洢����ռ�
		 */
		int pnSegments[][] = new int[nHeightPadded][];
		for (int i = 0; i < nHeightPadded;++i) {
			int size = pnSegNumPerLine[i] << 2; // �����ĵ�������ʲô����JCJ���
												// �ο���pnSegment�����е����ݽṹ
			if (size > 0) {
				pnSegments[i] = new int[size];
			} else {
				pnSegments[i] = null;
			}
		}

		// ɨ����
		/* pnSegment�����е����ݽṹ */
		/*---------------------------
		 |�к�|��˵�|�Ҷ˵�|��־λ|
		 ---------------------------
		 |�к�|��˵�|�Ҷ˵�|��־λ|
		 ---------------------------
		 |�к�|��˵�|�Ҷ˵�|��־λ|
		 ---------------------------
		 |�к�|��˵�|�Ҷ˵�|��־λ|
		 ---------------------------*/
		for (int i = 1; i < nHeightPadded - 1; ++i) 
		{
			n = 0;// JCJ ��¼�Ӷ���
			for (int j = 0; j < nWidthPadded - 1; ++j) 
			{
				if (pnBWPadded[i * nWidthPadded + j] != 0)
				{
					for (k = j + 1; k < nWidthPadded; ++k) 
					{
						if (pnBWPadded[i * nWidthPadded + k] == 0)
							break;
					}
					pnSegments[i][n << 2] = i; // ��¼�к�
					pnSegments[i][(n << 2) + 1] = j; // ��¼��˵�
					pnSegments[i][(n << 2) + 2] = k - 1;// ��¼�Ҷ˵�
					pnSegments[i][(n << 2) + 3] = 0; // Ĭ�ϳ�ʼ�����λ Ϊ0
					++n;
					j = k;
				}
			}
		}

		Stack<StackItem> pStack = new Stack<StackItem>();

		N = 1; // ����ı��
		for (int i = 1; i < nHeightPadded - 1; i++) {
			for (int j = 0; j < pnSegNumPerLine[i]; j++) {
				if (pnSegments[i][(j << 2) + 3] == 0) {
					pnSegments[i][(j << 2) + 3] = -1; // ��־λ��-1��ʾ��ջ
					StackItem item = new StackItem(i, j << 2);
					pStack.push(item);
					while (true) {
						StackItem top = pStack.peek();
						X = pnSegments[top.row][top.col]; // �к�
						L = pnSegments[top.row][top.col + 1]; // ��˵�
						R = pnSegments[top.row][top.col + 2]; // �Ҷ˵�
						//8��ͨ	
						LL = L - 1;
						RR = R +1;
//						//4��ͨ	
//						LL = L;
//						RR = R;

						nFlag = 0;
						// ɨ����һ�п��Ƿ����δ��ǵ��ڽ��߶Σ����ڣ�nFlag=1�����ڽӶ�ѹ���ջ
						X1 = X - 1;
						for (int m = 0; m < pnSegNumPerLine[X1]; ++m) {
							if ((pnSegments[X1][(m << 2) + 1] <= RR)
									&& (pnSegments[X1][(m << 2) + 2] >= LL)
									&& (pnSegments[X1][(m << 2) + 3] == 0)) {
								pnSegments[X1][(m << 2) + 3] = -1;
								StackItem it = new StackItem(X - 1, m << 2);
								pStack.push(it);
								nFlag = 1;
							}
						}
						// ɨ����һ�п��Ƿ�����ڽ��߶Σ����ڣ�nFlag=1�����ڽӶ�ѹ���ջ
						X1 = X + 1;
						for (int n1 = 0; n1 < pnSegNumPerLine[X1]; ++n1) {
							if ((pnSegments[X1][(n1 << 2) + 1] <= RR)
									&& (pnSegments[X1][(n1 << 2) + 2] >= LL)
									&& (pnSegments[X1][(n1 << 2) + 3] == 0)) {
								pnSegments[X1][(n1 << 2) + 3] = -1;
								StackItem it = new StackItem(X1, n1 << 2);
								pStack.push(it);
								nFlag = 1;
							}
						}
						if (nFlag == 0) {
							if (pStack.empty())// ջΪ�գ����������һ����ͨ����
							{
								++N;
								break;
							} else {
								StackItem top1 = pStack.peek();
								pnSegments[top1.row][top1.col + 3] = N; // ջΪ���գ���ǵ�ǰ��ͨ����
								pStack.pop(); // ����Ӷ�ջ�е���

								// ջΪ�գ�������ǰ��ͨ���������
								// ��ʼ��һ����ͨ����ı��
								if (pStack.empty()) 
								{
									++N;
									break;
								} else {
									// ջ��Ϊ�գ���������ջ��Ԫ��
									continue;
								}
							}
						} else {
							// ��������ջ��Ԫ��
							continue;
						}
					}
				}
			}
		}
		// ����pnSegments�и������λ����Ƕ�ֵͼ��

		for (int i = 1; i <= nImageHeight; ++i)
			for (int j = 0; j < pnSegNumPerLine[i]; ++j)
				for (k = pnSegments[i][(j << 2) + 1]; k <= pnSegments[i][(j << 2) + 2]; ++k)
				{
					pnBWLabel[(i - 1) * nImageWidth + k - 1] = (int) pnSegments[i][(j << 2) + 3];
				}
		return N - 1;
	}
}

