

import java.util.ArrayList;

public class Board {
	private int width;
	private int length;
	private char board[][];
	private Board(int width, int length) {
		this.width = width;
		this.length = length;
		board = new char[width][length];
		for (int i = 0; i < getWidth(); i++)
			for (int j = 0; j < getLength(); j++)
				unset(i, j);
	}
	public char get(int x, int y) {
		return board[x][y];
	}
	public void set(int x, int y, char c) {
		board[x][y] = c;
	}
	public void unset(int x, int y) {
		board[x][y] = ' ';
	}
	public int getWidth() {
		return width;
	}
	public int getLength() {
		return length;
	}
	public static Board getBoard(int width, int length) {
		return new Board(width, length);
	}
	
	public boolean testEmpty(Block b, int x, int y) {
		if (x + b.getWidth() > this.getWidth()) return false;
		if (y + b.getLength() > this.getLength()) return false;
		for (int i = 0; i < b.getWidth(); i++)
			for (int j = 0; j < b.getLength(); j++)
				if (b.get(i, j) != ' ' && this.get(x + i, y + j) != ' ') return false;
		return true;
	}
	public boolean testFilled(Block b, int x, int y) {
		if (x + b.getWidth() > this.getWidth()) return false;
		if (y + b.getLength() > this.getLength()) return false;
		for (int i = 0; i < b.getWidth(); i++)
			for (int j = 0; j < b.getLength(); j++)
				if (b.get(i, j) != ' ' && this.get(x + i, y + j) != b.get(i, j)) return false;
		return true;
	}
	public boolean testEmptyAndPut(Block b, int x, int y) {
		if (!testEmpty(b, x, y)) return false;
		for (int i = 0; i < b.getWidth(); i++)
			for (int j = 0; j < b.getLength(); j++)
				if (b.get(i, j) != ' ') set(x + i, y + j, b.get(i, j));
		return true;
	}
	public boolean testFilledAndRemove(Block b, int x, int y) {
		if (!testFilled(b, x, y)) return false;
		for (int i = 0; i < b.getWidth(); i++)
			for (int j = 0; j < b.getLength(); j++)
				if (b.get(i, j) != ' ') unset(x + i, y + j);
		return true;
	}
	
	public int[][] getPossiblePositions(Block b) {
		ArrayList<Integer> xPos = new ArrayList<Integer>();
		ArrayList<Integer> yPos = new ArrayList<Integer>();
		for (int i = 0; i <= getWidth() - b.getWidth(); i++)
			for (int j = 0; j <= getLength() - b.getLength(); j++)
				if (testEmpty(b, i, j)) {
					xPos.add(i);
					yPos.add(j);
				}
		int pos[][] = new int[xPos.size()][2];
		for (int i = 0; i < xPos.size(); i++) {
			pos[i][0] = xPos.get(i);
			pos[i][1] = yPos.get(i);
		}
		return pos;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getLength(); j++) {
				sb.append(get(i, j));
			}
			sb.append('\n');
		}
			
		return sb.toString();
	}
}
