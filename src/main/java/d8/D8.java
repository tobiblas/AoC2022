package d8;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.Task;

public class D8 extends Task {

	public static void main(String[] args) {
		D8 day = new D8();
		List<String> list = day.getStrings();
		day.p1(list);
		day.p2(list);
	}

	private void p1(List<String> list) {
		int[][] trees = new int[list.size()][list.get(0).length()];
		for (int x = 0; x < list.size(); ++x) {
			for (int y = 0; y < list.get(0).length(); ++y) {
				trees[x][y] = Integer.parseInt("" + list.get(x).charAt(y));
			}
		}
		Set<Integer> coord = new HashSet<>();
		//LEFT
		for (int x = 0; x < list.size(); ++x) {
			int currHeight = -1;
			for (int y = 0; y < list.get(0).length(); ++y) {
				int tree = trees[x][y];
				if (tree > currHeight) {
					coord.add(x * 10000 + y);
					currHeight = tree;
				}
			}
		}
		//RIGHT
		for (int x = list.size() - 1; x >= 0; --x) {
			int currHeight = -1;
			for (int y = list.size() - 1; y >= 0; --y) {
				int tree = trees[x][y];
				if (tree > currHeight) {
					coord.add(x * 10000 + y);
					currHeight = tree;
				}
			}
		}
		//TOP
		for (int y = list.size() - 1; y >= 0; --y) {
			int currHeight = -1;
			for (int x = list.size() - 1; x >= 0; --x) {
				int tree = trees[x][y];
				if (tree > currHeight) {
					coord.add(x * 10000 + y);
					currHeight = tree;
				}
			}
		}
		//BOTTOM
		for (int y = 0; y < list.get(0).length(); ++y) {
			int currHeight = -1;
			for (int x = 0; x < list.size(); ++x) {
				int tree = trees[x][y];
				if (tree > currHeight) {
					coord.add(x * 10000 + y);
					currHeight = tree;
				}
			}
		}
		System.out.println(coord.size());
	}

	private void p2(List<String> list) {
		long largestSum = 0;
		for (int x = 0; x < list.size(); ++x) {
			for (int y = 0; y < list.get(0).length(); ++y) {
				long s = getViewingDist(list, x, y);
				if (s > largestSum) {
					largestSum = s;
				}
			}
		}
		System.out.println(largestSum);
	}

	private long getViewingDist(List<String> list, int xIn, int yIn) {
		int[][] trees = new int[list.size()][list.get(0).length()];
		for (int x = 0; x < list.size(); ++x) {
			for (int y = 0; y < list.get(0).length(); ++y) {
				trees[x][y] = Integer.parseInt("" + list.get(x).charAt(y));
			}
		}
		int start = trees[xIn][yIn];
		Set<Integer> coord = new HashSet<>();
		//LEFT
		int viewLeft = 0;
		int x = xIn;
		int y = yIn;
		int currHeight = -1;

		for (y = yIn; y < list.get(0).length(); ++y) {
			if (x == xIn && y == yIn) {
				continue;
			}
			int tree = trees[x][y];
			if (tree >= start) {
				coord.add(x * 10000 + y);
				break;
			} else {
				coord.add(x * 10000 + y);
			}
		}
		viewLeft = coord.size();

		coord = new HashSet<>();
		//RIGHT
		int viewRight = 0;
		y = yIn;
		x = xIn;
		currHeight = -1;
		for (y = yIn; y >= 0; --y) {
			if (x == xIn && y == yIn) {
				continue;
			}
			int tree = trees[x][y];
			if (tree >= start) {
				coord.add(x * 10000 + y);
				break;
			} else {
				coord.add(x * 10000 + y);
			}
		}

		viewRight = coord.size();

		coord = new HashSet<>();
		//TOP
		int viewTop = 0;
		y = yIn;
		x = xIn;
		for (x = xIn; x < list.get(0).length(); ++x) {
			if (x == xIn && y == yIn) {
				continue;
			}
			int tree = trees[x][y];
			if (tree >= start) {
				coord.add(x * 10000 + y);
				break;
			} else {
				coord.add(x * 10000 + y);
			}
		}
		viewTop = coord.size();

		coord = new HashSet<>();
		int viewBottom = 0;
		y = yIn;
		x = xIn;
		for (x = xIn; x >= 0; --x) {
			if (x == xIn && y == yIn) {
				continue;
			}
			int tree = trees[x][y];
			if (tree >= start) {
				coord.add(x * 10000 + y);
				break;
			} else {
				coord.add(x * 10000 + y);
			}
		}
		viewBottom = coord.size();
		long st = viewBottom * viewLeft * viewRight * viewTop;
		return st;

	}

}
