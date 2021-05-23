package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MagicSquares {
	public static void main(String[] args) {
		int[][] array = new int[1000][1000];
		for (int i = 1; i < 6; i++) {
			int size = readFile(array, "./src/P1/txt/" + i + ".txt");
			System.out.println(i + ":" + isLegalMagicSquare(array, size));
		}
		if(generateMagicSquare(7)) {
			int size = readFile(array, "./src/P1/txt/6.txt");
			System.out.println("6:" + isLegalMagicSquare(array, size));
		}
		else
			System.out.println("6:false");
	}

	public static boolean isLegalMagicSquare(int[][] array, int size) {
		if (size == -1)
			return false;
		int sum1 = 0, sum2 = 0;
		for (int i = 0; i < size; i++)
			sum1 += array[0][i];
		for (int i = 1; i < size; i++) {
			sum2 = 0;
			for (int j = 0; j < size; j++)
				sum2 += array[i][j];
			if (sum2 != sum1)
				return false;
		}
		for (int i = 0; i < size; i++) {
			sum2 = 0;
			for (int j = 0; j < size; j++)
				sum2 += array[j][i];
			if (sum2 != sum1)
				return false;
		}
		sum2 = 0;
		for (int i = 0; i < size; i++)
			sum2 += array[i][i];
		if (sum2 != sum1)
			return false;
		sum2 = 0;
		for (int i = 0; i < size; i++)
			sum2 += array[i][size - i - 1];
		if (sum2 != sum1)
			return false;
		return true;
	}

	private static int readFile(int[][] array, String filepath) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String line = null;
		String[][] strs = new String[1000][];
		int i = 0, j = 0, column = 0, flag = 0;
		boolean[] vis = new boolean[999999];
		try {
			fis = new FileInputStream(filepath);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			if ((line = br.readLine()) != null) {
				strs[i] = line.split("\t");
				for (j = 0; j < strs[i].length; j++) {
					if (isLegalNumber(strs[i][j]))
						array[i][j] = Integer.parseInt(strs[i][j]);
					else
						return -1;
					if (vis[array[i][j]])
						return -1;
					else
						vis[array[i][j]] = true;
				}
				column = j;
				i += 1;
			}
			while ((line = br.readLine()) != null) {
				strs[i] = line.split("\t");
				for (j = 0; j < strs[i].length; j++) {
					if (isLegalNumber(strs[i][j]))
						array[i][j] = Integer.parseInt(strs[i][j]);
					else
						return -1;
					if (vis[array[i][j]])
						return -1;
					else
						vis[array[i][j]] = true;
				}
				if (column != j) {
					flag = 1;
					break;
				}
				i += 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (flag == 1)
			return -1;
		return i;
	}

	private static boolean isLegalNumber(String str) {
		if (str.matches("^[0-9]+$"))
			return true;
		return false;
	}

	public static boolean generateMagicSquare(int n) {
		//确保n为奇数
		if (n % 2 == 0 || n < 0) {
			System.out.println("ERROR!");
			return false;
		}
		int magic[][] = new int[n][n];
		//初始化的点是第一行中间数
		int row = 0, col = n / 2, i, j, square = n * n;
		//赋的值从1到矩阵的面积。
		//赋值顺序是从初始化的点开始，下一个点在上一个点的右上方。
		//若已经到最上一行或最右一列，则下一个点取最下一行或最左一列，
		//另一个坐标照常+1
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
				if (row == 0)
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		//将生成的矩阵存入文件“6.txt”
		try {
			File f = new File("./src/P1/txt/" + 6 + ".txt");
			if (!f.exists())
				f.createNewFile();
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "gbk");
			BufferedWriter writer = new BufferedWriter(write);
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++)
					writer.write(magic[i][j] + "\t");
				writer.write("\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
}
