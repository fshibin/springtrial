import java.util.Date;

public class Game {
	public static final int WIDTH = 3;
	public static final int LENGTH = 28;
	public static final int BLOCK_NUM = 17;
	private static Board board = Board.getBoard(WIDTH, LENGTH);
	private static Block blocks[] = new Block[BLOCK_NUM];
	private static Block blockVariants[][]  = new Block[blocks.length][];
	
	public static void generateBlocks() {
		int i = 0;
		blocks[i++] = new Block(new char[][] {
			{' ', 'C', ' '},
			{'C', 'C', 'C'},
			{' ', 'C', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'G', 'G', ' ', ' '},
			{' ', 'G', 'G', ' '},
			{' ', ' ', 'G', 'G'},
		});
		blocks[i++] = new Block(new char[][] {
			{'F', 'F', 'F', 'F'},
			{' ', 'F', 'F', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'9', '9', '9'},
			{'9', ' ', ' '},
			{'9', ' ', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{' ', 'A', 'A'},
			{'A', 'A', ' '},
			{'A', ' ', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'D', 'D', 'D'},
			{'D', 'D', ' '},
			{'D', ' ', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'E', 'E', ' '},
			{'E', 'E', 'E'},
			{' ', 'E', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'2', '2', '2'},
			{' ', '2', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'3', '3', ' '},
			{' ', '3', '3'},
		});
		blocks[i++] = new Block(new char[][] {
			{'5', '5', '5'},
			{'5', ' ', '5'},
		});
		blocks[i++] = new Block(new char[][] {
			{'6', '6', '6', '6'},
			{'6', ' ', ' ', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'7', '7', '7', '7'},
			{' ', '7', ' ', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'8', '8', '8', ' '},
			{' ', ' ', '8', '8'},
		});
		blocks[i++] = new Block(new char[][] {
			{'B', 'B', ' '},
			{' ', 'B', 'B'},
			{' ', 'B', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'0', '0'},
			{'0', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'1', '1', '1'},
			{'1', ' ', ' '},
		});
		blocks[i++] = new Block(new char[][] {
			{'4', '4', '4'},
			{'4', '4', ' '},
		});

	}
	
	public static void getBlockVariants() {
		for (int i = 0; i < blocks.length; i++) {
			System.out.println("Block " + i);
			blockVariants[i] = blocks[i].getVariants(WIDTH, LENGTH);
			System.out.println("Block has " + blockVariants[i].length + " varaints");
			int sum = 0;
			for (int j = 0; j < blockVariants[i].length; j++) {
				Block b = blockVariants[i][j];
				b.setPossiblePositions(board.getPossiblePositions(b));
				System.out.println("# of positions: " + b.getPossiblePositions().length);
				sum += b.getPossiblePositions().length;
			}
			System.out.println("Total # of positions: " + sum);
		}
		//System.exit(0);
	}
	
	public static void play() {
		generateBlocks();
		getBlockVariants();
		/*
		int bi = 8;
		for (int i = 0; i < blockVariants[bi].length; i++) 
			System.out.println(blockVariants[bi][i]);
		System.exit(0);
		*/
		boolean s = tryOneBlock(0);
		if (s) System.out.println("Found some answers!");
		else System.out.println("Unable to solve this problem!");
	}
	public static boolean tryOneBlock(int bi) {
		boolean s = false;
		for (int i = 0; i < blockVariants[bi].length; i++) {
			s = tryOneBlockVariant(bi, i);
			if (s) return s;
		}
		return s;
	}
	public static boolean tryOneBlockVariant(int bi, int vi) {
		if (bi == 6) {
			System.out.println(new Date());
			System.out.println("Trying block " + bi + "...");
		}
		Block b = blockVariants[bi][vi];
		int positions[][] = b.getPossiblePositions();
		for (int i = 0; i < positions.length; i++) {
			int x = positions[i][0];
			int y = positions[i][1];
			if (board.testEmptyAndPut(b, x, y)) {
				if (bi < blocks.length - 1) { // not last block
					if (tryOneBlock(bi + 1)) return true;
					// else try next position
				} else { // already last block, succeed!
					System.out.println(board);
					return true;
				}
				board.testFilledAndRemove(b, x, y);
			}
			// else try next position
		}
		return false;
	}
	
	public static void main(String args[]) {
		play();
	}
}
