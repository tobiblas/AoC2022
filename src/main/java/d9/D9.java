package d9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import util.Task;

public class D9 extends Task {

	public static void main(String[] args) {
		D9 day = new D9();
		List<String> list = day.getStrings();
		//day.p1(list);
		day.p2(list);
	}

	private class Pos {

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(x, y);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pos other = (Pos) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return x == other.x && y == other.y;
		}

		public Pos(Pos pos) {
			this.x = pos.x;
			this.y = pos.y;
		}

		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int x;
		int y;

		private D9 getEnclosingInstance() {
			return D9.this;
		}

	}

	private void p1(List<String> list) {
		Set<Pos> visited = new HashSet<>();
		Pos headPos = new Pos(0, 0);
		Pos tailPos = new Pos(0, 0);
		visited.add(new Pos(tailPos));
		for (String move : list) {
			String dir = move.split(" ")[0];
			int steps = Integer.parseInt(move.split(" ")[1]);

			for (int i = 0; i < steps; ++i) {
				move(dir, headPos, tailPos);
				visited.add(new Pos(tailPos));
			}
		}
		System.out.println(visited.size());

		printVisited(visited);
	}

	private void printVisited(Set<Pos> visited) {
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		for (Pos pos : visited) {
			if (pos.x > maxX) {
				maxX = pos.x;
			}
			if (pos.x < minX) {
				minX = pos.x;
			}
			if (pos.y > maxY) {
				maxY = pos.y;
			}
			if (pos.y < minY) {
				minY = pos.y;
			}
		}
		int diffXFrom0 = Math.abs(minX);
		if (minX < 0) {

			minX = 0;
			maxX += Math.abs(diffXFrom0);
		}
		int diffYFrom0 = Math.abs(minY);
		if (minY < 0) {

			minY = 0;
			maxY += Math.abs(diffYFrom0);
		}

		int[][] visitedMatrix = new int[maxX + 1][maxY + 1];
		for (Pos pos : visited) {
			visitedMatrix[pos.x + diffXFrom0][pos.y + diffYFrom0] = 1;
		}
		for (int y = maxY; y >= 0; --y) {
			for (int x = 0; x < maxX + 1; ++x) {
				if (visitedMatrix[x][y] == 1)
					System.out.print("#");
				else
					System.out.print(".");
			}
			System.out.println();
		}
	}

	private boolean headAdjacentToTail(Pos headPos, Pos tailPos) {
		double dis = getDistance(headPos, tailPos);
		return dis < 1.5f;
	}

	private double getDistance(Pos headPos, Pos tailPos) {
		int x1, x2, y1, y2;
		double dis;
		x1 = headPos.x;
		y1 = headPos.y;
		x2 = tailPos.x;
		y2 = tailPos.y;
		dis = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		return dis;
	}

	private void p2(List<String> list) {
		Set<Pos> visited = new HashSet<>();
		List<Pos> knots = new ArrayList<>();
		for (int i = 0; i < 10; ++i) {
			knots.add(new Pos(0, 0));
		}
//		Pos headPos = knots.get(0);
//		Pos tailPos = knots.get(9);
		visited.add(new Pos(new Pos(0, 0)));
		for (String move : list) {
			String dir = move.split(" ")[0];
			if (move.equals("L 8")) {
				int a = 0;
			}
			int steps = Integer.parseInt(move.split(" ")[1]);

			for (int i = 0; i < steps; ++i) {
				//Move 
				for (int j = 0; j < 9; ++j) {
					Pos current = knots.get(j);
					if (j == 0) {
						//move head
						if (dir.equals("U")) {
							current.y++;
						} else if (dir.equals("D")) {
							current.y--;
						} else if (dir.equals("L")) {
							current.x--;
						} else if (dir.equals("R")) {
							current.x++;
						}
					}
					Pos next = knots.get(j + 1);
					move(dir, current, next);
					//printKnots(knots);
				}
				visited.add(new Pos(knots.get(9)));
				//printKnots(knots);
			}
		}
		System.out.println(visited.size());
		printKnots(knots);
		printVisited(visited);
	}

	private void printKnots(List<Pos> knots) {
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		for (Pos pos : knots) {
			if (pos.x > maxX) {
				maxX = pos.x;
			}
			if (pos.x < minX) {
				minX = pos.x;
			}
			if (pos.y > maxY) {
				maxY = pos.y;
			}
			if (pos.y < minY) {
				minY = pos.y;
			}
		}
		int diffXFrom0 = Math.abs(minX);
		if (minX < 0) {

			minX = 0;
			maxX += Math.abs(diffXFrom0);
		}
		int diffYFrom0 = Math.abs(minY);
		if (minY < 0) {

			minY = 0;
			maxY += Math.abs(diffYFrom0);
		}

		int[][] visitedMatrix = new int[maxX + 1][maxY + 1];
		for (int i = 0; i < knots.size(); ++i) {
			Pos pos = knots.get(i);
			if (visitedMatrix[pos.x + diffXFrom0][pos.y + diffYFrom0] == 0) {
				visitedMatrix[pos.x + diffXFrom0][pos.y + diffYFrom0] = i + 1;
			}
		}
		for (int y = maxY; y >= 0; --y) {
			for (int x = 0; x < maxX + 1; ++x) {
				if (visitedMatrix[x][y] == 1) {
					System.out.print("H");
				} else if (visitedMatrix[x][y] == 0) {
					System.out.print(".");
				} else {
					System.out.print(visitedMatrix[x][y] - 1);
				}
			}
			System.out.println();
		}
		System.out.println("------------------");
	}

	private void move(String dir, Pos current, Pos next) {
		boolean moveNext = !headAdjacentToTail(current, next);
		if (moveNext) {
			boolean diagonalMoveNeeded = false;
			if (next.x != current.x && next.y != current.y) {
				diagonalMoveNeeded = true;
			}
			if (diagonalMoveNeeded) {
				if (current.x > next.x) {
					next.x++;
				}
				if (current.x < next.x) {
					next.x--;
				}
				if (current.y > next.y) {
					next.y++;
				}
				if (current.y < next.y) {
					next.y--;
				}
			} else {
				if (current.x == next.x && current.y < next.y) {
					next.y--;
				} else if (current.x == next.x && current.y > next.y) {
					next.y++;
				} else if (current.y == next.y && current.x < next.x) {
					next.x--;
				} else if (current.y == next.y && current.x > next.x) {
					next.x++;
				}
			}
		}
	}

}
