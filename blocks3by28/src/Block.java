

import java.util.ArrayList;

public class Block {
	private char shape[][];
	private int possiblePositions[][]; 
	public Block(char s[][]) {
		assert(s.length > 0);
		assert(s[0].length > 0);
		this.shape = s;
	}
	public char get(int x, int y) {
		return shape[x][y];
	}
	public int getWidth() {
		return shape.length;
	}
	public int getLength() {
		return shape[0].length;
	}
	public int[][] getPossiblePositions() {
		return possiblePositions;
	}
	public void setPossiblePositions(int[][] possiblePositions) {
		this.possiblePositions = possiblePositions;
	}
	public Block rotateClockwise() {
		char s[][] = new char[shape[0].length][shape.length];
		for (int i = 0; i < shape.length; i++)
			for (int j = 0; j < shape[0].length; j++)
				s[j][shape.length - 1 - i] = shape[i][j];
		return new Block(s);
	}
	public Block flipHorizontally() {
		char s[][] = new char[shape.length][shape[0].length];
		for (int i = 0; i < shape.length; i++)
			for (int j = 0; j < shape[0].length; j++)
				s[i][s[0].length - 1 - j] = shape[i][j];
		return new Block(s);
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Block) {
			Block b = (Block)o;
			if (this.getWidth() == b.getWidth()) {
				if (this.getLength() == b.getLength()) {
					for (int i = 0; i < this.getWidth(); i++)
						for (int j = 0; j < this.getLength(); j++)
							if (shape[i][j] != b.shape[i][j]) return false;
					return true;
				}
			}
		}
		return false;
	}
	public boolean isValid(int width, int length) {
		return this.getWidth() <= width && this.getLength() <= length;
	}
	public Block[] getVariants(int width, int length) {
		ArrayList<Block> vars = new ArrayList<Block>();
		Block b = this;
		for (int i = 0; i < 4; i++) {
			if (b.isValid(width, length)) {
				boolean duplicate = false;
				for (int j = 0; j < vars.size(); j++) {
					if (vars.get(j).equals(b)) {
						duplicate = true;
						break;
					}
				}
				if (!duplicate) vars.add(b);
			}
			b = b.rotateClockwise();
		}

		b = this.flipHorizontally();
		for (int i = 0; i < 4; i++) {
			if (b.isValid(width, length)) {
				boolean duplicate = false;
				for (int j = 0; j < vars.size(); j++) {
					if (vars.get(j).equals(b)) {
						duplicate = true;
						break;
					}
				}
				if (!duplicate) vars.add(b);
			}
			b = b.rotateClockwise();
		}

		return vars.toArray(new Block[0]);
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
