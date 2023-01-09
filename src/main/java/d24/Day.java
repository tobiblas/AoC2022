package d24;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.Task;

public class Day extends Task {

	public static void main(String[] args) {
		Day d = new Day();
		List<String> list = d.getStrings();
		d.p1(list);
		d.p2(list);
	}

	private void p2(List<String> list) {

	}

	private void p1(List<String> list) {
		//y= 0 is top
		List<Blizzard> blizzards = new ArrayList<>();
		Point start = new Point(1, 0);
		Point goal = new Point(list.get(0).length() - 2, list.size() - 1);
		int wallRight = list.get(0).length() - 1;
		int wallBottom = list.size() - 1;
		populateBlizzards(list, blizzards);

		//Queue<TreeNode> queue = new ArrayDeque<>();
		//Queue<TreeNode> nextQueue = new ArrayDeque<>();
		//queue.add(TreeNode.of(start));
		Set<Point> queue = new HashSet<>();
		Set<Point> nextQueue = new HashSet<>();
		queue.add(start);
		boolean goalReached = false;
		boolean startReached = false;
		int i = 1;
		while (true) {
			moveBlizzards(blizzards, wallRight, wallBottom);
			Point node = null;
			while (!queue.isEmpty()) {
				node = queue.iterator().next();
				queue.remove(node);
				Point p = node;
				//calculate possible point from this point then add them as children and to Queue
				Point wait = (Point) p.clone();
				Point left = new Point(p.x - 1, p.y);
				Point right = new Point(p.x + 1, p.y);
				Point down = new Point(p.x, p.y + 1);
				Point up = new Point(p.x, p.y - 1);
				if (pointReached(goal, wait, left, right, down, up)) {
					if (goalReached && startReached) {
						System.out.println("GOAL REACHED AGAIN");
						System.exit(0);
					} else if (!goalReached && !startReached) {
						System.out.println("GOAL REACHED");
						goalReached = true;
						nextQueue.clear();
						nextQueue.add(goal);
						break;
					}
				}
				if (goalReached && !startReached && pointReached(start, wait, left, right, down, up)) {
					startReached = true;
					System.out.println("START REACHED");
					nextQueue.clear();
					nextQueue.add(start);
					break;
				}
				if (isClear(blizzards, goal, start, wallRight, wallBottom, wait)) {
					nextQueue.add(wait);
					//node.addChild(TreeNode.of(wait));
				}
				if (isClear(blizzards, goal, start, wallRight, wallBottom, left)) {
					nextQueue.add(left);
					//node.addChild(TreeNode.of(left));
				}
				if (isClear(blizzards, goal, start, wallRight, wallBottom, right)) {
					nextQueue.add(right);
					//node.addChild(TreeNode.of(right));
				}
				if (isClear(blizzards, goal, start, wallRight, wallBottom, up)) {
					nextQueue.add(up);
					//node.addChild(TreeNode.of(up));
				}
				if (isClear(blizzards, goal, start, wallRight, wallBottom, down)) {
					nextQueue.add(down);
					//node.addChild(TreeNode.of(down));
				}
			}
			int max = Integer.MIN_VALUE;
			Point closest = null;
			for (Point n : nextQueue) {
				int v = n.x + n.y;
				if (v > max) {
					closest = n;
					max = v;
				}
			}
			System.out.println("Closest node is at: " + closest.x + "," + closest.y);
			System.out.println("Depth = " + i);
			System.out.println("Size = " + nextQueue.size());
			i++;
			queue = nextQueue;
			nextQueue = new HashSet<>();
		}
	}

	private boolean pointReached(Point goal, Point wait, Point left, Point right, Point down, Point up) {
		return isSame(goal, wait) || isSame(goal, left) || isSame(goal, right) || isSame(goal, down) || isSame(goal, up);
	}

	private void printMap(List<Blizzard> blizzards, Point start, Point goal, int wallRight, int wallBottom) {
		for (int y = 0; y <= wallBottom; ++y) {
			for (int x = 0; x <= wallRight; ++x) {
				if (start.x == x && start.y == y || goal.x == x && goal.y == y) {
					System.out.print(" ");
					continue;
				}
				if (x == 0 || y == 0 || x == wallRight || y == wallBottom) {
					System.out.print("#");
					continue;
				}
				List<Blizzard> bs = getBlizzards(new Point(x, y), blizzards);
				if (bs.size() == 0) {
					System.out.print(".");
				} else if (bs.size() > 1) {
					System.out.print(bs.size());
				} else {
					Blizzard b = bs.get(0);
					if (b.dir == NORTH) {
						System.out.print("^");
					} else if (b.dir == SOUTH) {
						System.out.print("v");
					} else if (b.dir == WEST) {
						System.out.print("<");
					} else if (b.dir == EAST) {
						System.out.print(">");
					}
				}
			}
			System.out.println();
		}
	}

	private boolean isClear(List<Blizzard> blizzards, Point goal, Point start, int wallRight, int wallBottom, Point p) {
		return isSame(p, start) || isSame(p, goal) || !containsBlizzard(p, blizzards) && !isWall(p, wallRight, wallBottom);
	}

	private boolean isWall(Point p, int wallRight, int wallBottom) {
		return p.x <= 0 || p.y <= 0 || p.y > wallBottom - 1 || p.x > wallRight - 1;
	}

	private boolean containsBlizzard(Point p, List<Blizzard> blizzards) {
		for (Blizzard b : blizzards) {
			if (isSame(b.pos, p)) {
				return true;
			}
		}
		return false;
	}

	private List<Blizzard> getBlizzards(Point p, List<Blizzard> blizzards) {
		List<Blizzard> bs = new ArrayList<>();
		for (Blizzard b : blizzards) {
			if (isSame(b.pos, p)) {
				bs.add(b);
			}
		}
		return bs;
	}

	private boolean isSame(Point start, Point p) {
		return p.x == start.x && p.y == start.y;
	}

	private void moveBlizzards(List<Blizzard> blizzards, int wallRight, int wallBottom) {
		for (Blizzard b : blizzards) {
			if (b.dir == NORTH) {
				if (b.pos.y == 1) {
					b.pos.y = wallBottom - 1;
				} else {
					b.pos.y--;
				}
			} else if (b.dir == SOUTH) {
				if (b.pos.y == wallBottom - 1) {
					b.pos.y = 1;
				} else {
					b.pos.y++;
				}
			} else if (b.dir == WEST) {
				if (b.pos.x == 1) {
					b.pos.x = wallRight - 1;
				} else {
					b.pos.x--;
				}
			} else if (b.dir == EAST) {
				if (b.pos.x == wallRight - 1) {
					b.pos.x = 1;
				} else {
					b.pos.x++;
				}
			}
		}
	}

	private void populateBlizzards(List<String> list, List<Blizzard> blizzards) {
		int y = 0;
		for (String str : list) {
			for (int i = 0; i < str.length(); ++i) {
				int dir = -1;
				if ((str.charAt(i) + "").equals("^")) {
					dir = NORTH;
				} else if ((str.charAt(i) + "").equals("<")) {
					dir = WEST;
				} else if ((str.charAt(i) + "").equals(">")) {
					dir = EAST;
				} else if ((str.charAt(i) + "").equals("v")) {
					dir = SOUTH;
				}
				if (dir != -1) {
					Blizzard b = new Blizzard();
					b.dir = dir;
					b.pos = new Point(i, y);
					blizzards.add(b);
				}
			}
			y++;
		}
	}

	int NORTH = 0;
	int EAST = 1;
	int SOUTH = 2;
	int WEST = 3;

	private class Blizzard {

		Point pos;
		int dir;
	}
}
