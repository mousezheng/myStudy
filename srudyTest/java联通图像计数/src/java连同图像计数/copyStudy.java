package java连同图像计数;



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
	 * 对二值图像联通区域进行标记
	 * 
	 * @param pnBW
	 *            二值图像 0 1
	 * @param pnBWLabel
	 *            pnBWLabel标记后的图像 1 2 3 4...不同的区域不同的值 未被标记的区域也就是背景为0
	 * @param nImageWidth
	 *            图像宽
	 * @param nImageHeight
	 *            图像高
	 * @return 二值图像中区域数
	 */
	int BWLabel(byte pnBW[], int pnBWLabel[], short nImageWidth, short nImageHeight)
	{
		int k = 0, n = 0, L = 0, R = 0, LL = 0, RR = 0, N = 0, X = 0, X1 = 0, nFlag = 0;
		int nWidthPadded = nImageWidth + 2, nHeightPadded = nImageHeight + 2;

		byte pnBWPadded[] = new byte[nWidthPadded * nHeightPadded];//二值图像 四周0填充 上下左右 外扩一行或列
		int pnSegNumPerLine[] = new int[nHeightPadded];//记录每行有多少段
		/**
		 * 将pnBW值 赋给pnBWPadded
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
		 * 统计每行有多少段
		 * 判断依据 [i j]处值为0 [i j+1]不为0
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
		 *根据上述统计的每行有多少小段段 为每一行的线段端点存储分配空间
		 */
		int pnSegments[][] = new int[nHeightPadded][];
		for (int i = 0; i < nHeightPadded;++i) {
			int size = pnSegNumPerLine[i] << 2; // 乘以四的用意是什么？？JCJ解答
												// 参考下pnSegment矩阵中的数据结构
			if (size > 0) {
				pnSegments[i] = new int[size];
			} else {
				pnSegments[i] = null;
			}
		}

		// 扫描标记
		/* pnSegment矩阵中的数据结构 */
		/*---------------------------
		 |行号|左端点|右端点|标志位|
		 ---------------------------
		 |行号|左端点|右端点|标志位|
		 ---------------------------
		 |行号|左端点|右端点|标志位|
		 ---------------------------
		 |行号|左端点|右端点|标志位|
		 ---------------------------*/
		for (int i = 1; i < nHeightPadded - 1; ++i) 
		{
			n = 0;// JCJ 记录子段数
			for (int j = 0; j < nWidthPadded - 1; ++j) 
			{
				if (pnBWPadded[i * nWidthPadded + j] != 0)
				{
					for (k = j + 1; k < nWidthPadded; ++k) 
					{
						if (pnBWPadded[i * nWidthPadded + k] == 0)
							break;
					}
					pnSegments[i][n << 2] = i; // 记录行号
					pnSegments[i][(n << 2) + 1] = j; // 记录左端点
					pnSegments[i][(n << 2) + 2] = k - 1;// 记录右端点
					pnSegments[i][(n << 2) + 3] = 0; // 默认初始化标记位 为0
					++n;
					j = k;
				}
			}
		}

		Stack<StackItem> pStack = new Stack<StackItem>();

		N = 1; // 区域的编号
		for (int i = 1; i < nHeightPadded - 1; i++) {
			for (int j = 0; j < pnSegNumPerLine[i]; j++) {
				if (pnSegments[i][(j << 2) + 3] == 0) {
					pnSegments[i][(j << 2) + 3] = -1; // 标志位置-1表示入栈
					StackItem item = new StackItem(i, j << 2);
					pStack.push(item);
					while (true) {
						StackItem top = pStack.peek();
						X = pnSegments[top.row][top.col]; // 行号
						L = pnSegments[top.row][top.col + 1]; // 左端点
						R = pnSegments[top.row][top.col + 2]; // 右端点
						//8联通	
						LL = L - 1;
						RR = R +1;
//						//4联通	
//						LL = L;
//						RR = R;

						nFlag = 0;
						// 扫描上一行看是否存在未标记的邻接线段，存在，nFlag=1，将邻接段压入堆栈
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
						// 扫描下一行看是否存在邻接线段，存在，nFlag=1，将邻接段压入堆栈
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
							if (pStack.empty())// 栈为空，继续标记下一个连通区域
							{
								++N;
								break;
							} else {
								StackItem top1 = pStack.peek();
								pnSegments[top1.row][top1.col + 3] = N; // 栈为不空，标记当前连通区域
								pStack.pop(); // 将其从堆栈中弹出

								// 栈为空，表明当前连通区域标记完毕
								// 开始下一个连通区域的标记
								if (pStack.empty()) 
								{
									++N;
									break;
								} else {
									// 栈不为空，继续处理栈顶元素
									continue;
								}
							}
						} else {
							// 继续处理栈顶元素
							continue;
						}
					}
				}
			}
		}
		// 依据pnSegments中各个标记位，标记二值图像

		for (int i = 1; i <= nImageHeight; ++i)
			for (int j = 0; j < pnSegNumPerLine[i]; ++j)
				for (k = pnSegments[i][(j << 2) + 1]; k <= pnSegments[i][(j << 2) + 2]; ++k)
				{
					pnBWLabel[(i - 1) * nImageWidth + k - 1] = (int) pnSegments[i][(j << 2) + 3];
				}
		return N - 1;
	}
}

