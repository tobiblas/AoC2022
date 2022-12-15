package d14;

import java.util.List;

import util.Task;

public class D14 extends Task {

	public static void main(String[] args) {
		D14 day = new D14();
		List<String> list = day.getStrings();
		day.p1(list);
		day.p2(list);
	}

	private enum Item {
		SAND_FALLING,
		SAND_RESTING,
		ROCK,
		AIR;
	}

	public static long counter = 0;
	private static int floor = 11;

	private void p1(List<String> list) {

		//MAtrix 3 lower than lowest rock.
		//1 extra on each side. 500 is top.

		int lowestPoint = getLowestPoint(list);
		floor = lowestPoint;
		int leftesPoint = getLeftstPoint(list);
		int rightestPoint = getRightestPoint(list);
		int a = 0;
		int width = rightestPoint - leftesPoint;
		Item[][] map = new Item[10000][10000];
		populateMap(map, list);
		try {
			simulateSand(map, lowestPoint, leftesPoint, rightestPoint);
			printMap(map, lowestPoint, leftesPoint, rightestPoint);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(counter);
		}
	}

	private void simulateSand(Item[][] map, int lowestPoint, int leftesPoint, int rightestPoint) {

		while (true) {
			spawn(map, lowestPoint, leftesPoint, rightestPoint);
		}
	}

	private void spawn(Item[][] map, int lowestPoint, int leftesPoint, int rightestPoint) {
		if (map[500][0] == Item.SAND_RESTING) {
			map[500000][0] = Item.SAND_FALLING;
		}
		map[500][0] = Item.SAND_FALLING;
		if (counter % 100000 == 0) {
			printMap(map, lowestPoint, leftesPoint, rightestPoint);
		}
		int x = 500;
		int y = 0;
		while (true) {
			if (y + 1 == floor) {
				map[x][y] = Item.SAND_RESTING;
				counter++;
				break;
			}
			if (map[x][y + 1] == null) {
				map[x][y] = null;
				map[x][y + 1] = Item.SAND_FALLING;
				y++;
			} else if (map[x - 1][y + 1] == null) {
				map[x][y] = null;
				map[x - 1][y + 1] = Item.SAND_FALLING;
				x--;
				y++;
			} else if (map[x + 1][y + 1] == null) {
				map[x][y] = null;
				map[x + 1][y + 1] = Item.SAND_FALLING;
				x++;
				y++;
			} else {
				map[x][y] = Item.SAND_RESTING;
				counter++;
				break;
			}
		}

	}

	private void printMap(Item[][] map, int lowestPoint, int leftesPoint, int rightestPoint) {
		for (int y = 0; y < map.length; ++y) {
			if (y > lowestPoint) {
				continue;
			}
			for (int x = 0; x < map[0].length; ++x) {
				if (x < leftesPoint - 100 || x > rightestPoint + 100) {
					continue;
				}
				Item item = map[x][y];
				if (item == Item.AIR || item == null) {
					System.out.print(".");
				} else if (item == Item.ROCK) {
					System.out.print("#");
				} else if (item == Item.SAND_FALLING) {
					System.out.print("O");
				} else if (item == Item.SAND_RESTING) {
					System.out.print("o");
				}
			}
			System.out.println();
		}
	}

	private void populateMap(Item[][] map, List<String> list) {
		for (int x = 0; x < map.length; ++x) {
			for (int y = 0; y < map[0].length; ++y) {
				map[0][0] = Item.AIR;
			}
		}
		for (String str : list) {
			drawLine(map, str);
		}

	}

	private void drawLine(Item[][] map, String str) {
		String[] strs = str.split(" -> ");
		int currX = -1;
		int currY = -1;
		for (String s : strs) {
			if (currX == -1) {
				currX = Integer.parseInt(s.split(",")[0].trim());
				currY = Integer.parseInt(s.split(",")[1].trim());
				continue;
			}
			int x = Integer.parseInt(s.split(",")[0].trim());
			int y = Integer.parseInt(s.split(",")[1].trim());
			if (x != currX) {
				for (int i = 0; i < Math.abs(x - currX) + 1; ++i) {
					if (x > currX) {
						System.out.println("drawing: (" + (currX + i) + "," + currY + ")");
						map[currX + i][currY] = Item.ROCK;
					} else {
						System.out.println("drawing: (" + (currX - i) + "," + currY + ")");
						map[currX - i][currY] = Item.ROCK;
					}
				}
			}
			if (y != currY) {
				for (int i = 0; i < Math.abs(y - currY) + 1; ++i) {
					if (y > currY) {
						System.out.println("drawing: (" + (currX) + "," + (currY + i) + ")");
						map[currX][currY + i] = Item.ROCK;
					} else {
						System.out.println("drawing: (" + (currX) + "," + (currY - i) + ")");
						map[currX][currY - i] = Item.ROCK;
					}
				}
			}
			currX = x;
			currY = y;
		}
	}

	private int getRightestPoint(List<String> list) {
		int rightest = 500;
		for (String s : list) {
			String[] strs = s.split(" -> ");
			for (String str : strs) {
				int y = Integer.parseInt(str.split(",")[0].trim());
				if (y > rightest) {
					rightest = y;
				}
			}
		}
		return rightest + 1;
	}

	private int getLeftstPoint(List<String> list) {
		int leftest = 500;
		for (String s : list) {
			String[] strs = s.split(" -> ");
			for (String str : strs) {
				int y = Integer.parseInt(str.split(",")[0].trim());
				if (y < leftest) {
					leftest = y;
				}
			}
		}
		return leftest - 1;
	}

	private int getLowestPoint(List<String> list) {
		int lowest = 0;
		for (String s : list) {
			String[] strs = s.split(" -> ");
			for (String str : strs) {
				int x = Integer.parseInt(str.split(",")[1].trim());
				if (x > lowest) {
					lowest = x;
				}
			}
		}
		return lowest + 2;
	}

	private void p2(List<String> list) {

	}

}
