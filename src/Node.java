import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
	private int g;
	private int f;
	private int h;
	private Integer[][] matrix = new Integer[3][3];

	public int getF() {
		return f;
	}

	public int getH() {
		return h;
	}

	public int getG() {
		return g;
	}

	public Node(int g, Integer[][] matrix, Integer[][] goalMatrix) {
		this.g = g;
		this.matrix = matrix;

		this.f = f(goalMatrix);
		this.h = h(goalMatrix);
	}

	public SimpleEntry<Integer, Integer> find() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matrix[i][j] == null) {
					return new SimpleEntry<Integer, Integer>(i, j);
				}
			}
		}
		return null;
	}

	public List<Node> expand(Integer[][] goalMatrix) {
		List<Node> children = new ArrayList<Node>();

		SimpleEntry<Integer, Integer> cur = find();

		Integer[][] moves = { { cur.getKey(), cur.getValue() - 1 }, { cur.getKey(), cur.getValue() + 1 },
				{ cur.getKey() - 1, cur.getValue() }, { cur.getKey() + 1, cur.getValue() } };

		for (int i = 0; i < moves.length; i++) {
			Integer[][] childMatrix = shuffle(cur.getKey(), cur.getValue(), moves[i][0], moves[i][1]);

			if (childMatrix != null) {
				Node child = new Node(g + 1, childMatrix, goalMatrix);

				children.add(child);
			}
		}

		return children;
	}

	public int f(Integer[][] goalMatrix) {
		return h(goalMatrix) + g;
	}

	public int h(Integer[][] goalMatrix) {
		int temp = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matrix[i][j] != goalMatrix[i][j] && matrix[i][j] != null) {
					temp += 1;
				}
			}

		}
		return temp;
	}

	private Integer[][] shuffle(int x1, int y1, int x2, int y2) {
		if (x2 >= 0 && x2 < 3 && y2 >= 0 && y2 < 3) {
			Integer[][] tempMatrix = Arrays.stream(matrix).map((Integer[] row) -> row.clone())
					.toArray((int length) -> new Integer[length][]);
			Integer temp = tempMatrix[x2][y2];
			tempMatrix[x2][y2] = tempMatrix[x1][y1];
			tempMatrix[x1][y1] = temp;
			return tempMatrix;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(matrix);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		Node other = (Node) obj;
		return Arrays.deepEquals(matrix, other.matrix);
	}

	@Override
	public String toString() {
		String print = "|f = " + f + ", g = " + g + ", h = " + h + "|\n";
		int head = print.length();

		for (int i = 0; i < 3; i++) {
			String line = "|       ";
			for (int j = 0; j < 3; j++) {
				if (matrix[i][j] == null) {
					line += "  ";
				} else {
					line += matrix[i][j] + " ";
				}
			}
			print += line.substring(0, line.length() - 1);
			for (int j = 0; j < head - line.length() - 1; j++) {
				print += " ";
			}
			print += "|\n";
		}
		print = print.substring(0, print.length() - 1);

		return print;
	}

}
