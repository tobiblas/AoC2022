package d23;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Task;

public class Day extends Task {

	enum Dir {
		NORTH,
		SOUTH,
		WEST,
		EAST;
	}

	public static void main(String[] args) {
		Day d = new Day();
		List<String> list = d.getStrings();
		long t1 = System.currentTimeMillis();
		d.p1(list);
		System.out.println("P1 finished in " + (System.currentTimeMillis() - t1));
		t1 = System.currentTimeMillis();
		d.p2(list);
		System.out.println("P2 finished in " + (System.currentTimeMillis() - t1));
	}

	class Elf {

		int x;
		int y;

		int xP;
		int yP;

		boolean move = false;
	}

	private void p1(List<String> list) {
		List<Elf> elves = setUpElves(list);
		List<Point> elfPositions = new ArrayList<>();
		addElfPositions(elfPositions, elves);
		int iteration = 0;
		for (int i = 0; i < 10; ++i) {
			Dir[] order = getOrder(iteration);
			Map<Point, Integer> proposedMoves = proposeMove(elves, iteration, order, elfPositions);
			doMoveIfPossible(elves, proposedMoves);
			addElfPositions(elfPositions, elves);
			iteration++;
		}
		printMap(elves);
	}

	private void p2(List<String> list) {
		List<Elf> elves = setUpElves(list);
		List<Point> elfPositions = new ArrayList<>();
		addElfPositions(elfPositions, elves);
		int iteration = 0;
		while (true) {
			Dir[] order = getOrder(iteration);
			Map<Point, Integer> proposedMoves = proposeMove(elves, iteration, order, elfPositions);
			if (!doMoveIfPossible(elves, proposedMoves)) {
				System.out.println("P2. " + (iteration + 1));
				break;
			}
			addElfPositions(elfPositions, elves);
			iteration++;
		}
	}

	private void printMap(List<Elf> elves) {
		long minX = Integer.MAX_VALUE;
		long minY = Integer.MAX_VALUE;
		long maxX = Integer.MIN_VALUE;
		long maxY = Integer.MIN_VALUE;
		for (Elf elf : elves) {
			if (elf.x < minX) {
				minX = elf.x;
			}
			if (elf.x > maxX) {
				maxX = elf.x;
			}
			if (elf.y < minY) {
				minY = elf.y;
			}
			if (elf.y > maxY) {
				maxY = elf.y;
			}
		}
		long area = ((maxX - minX) + 1) * ((maxY - minY) + 1);
		System.out.println("P1. Empty space " + (area - elves.size()));
	}

	private void printElves(List<Elf> elves) {
		long minX = Integer.MAX_VALUE;
		long minY = Integer.MAX_VALUE;
		long maxX = Integer.MIN_VALUE;
		long maxY = Integer.MIN_VALUE;
		for (Elf elf : elves) {
			if (elf.x < minX) {
				minX = elf.x;
			}
			if (elf.x > maxX) {
				maxX = elf.x;
			}
			if (elf.y < minY) {
				minY = elf.y;
			}
			if (elf.y > maxY) {
				maxY = elf.y;
			}
		}
		System.out.println();
		List<Point> points = new ArrayList<>();
		addElfPositions(points, elves);
		for (long y = minY; y <= maxY; ++y) {
			for (long x = minX; x <= maxX; ++x) {
				if (points.contains(new Point((int) x, (int) y))) {
					if (points.contains(new Point((int) x - 1, (int) y)) ||
							points.contains(new Point((int) x - 1, (int) y + 1)) ||
							points.contains(new Point((int) x - 1, (int) y - 1)) ||
							points.contains(new Point((int) x, (int) y + 1)) ||
							points.contains(new Point((int) x, (int) y - 1)) ||
							points.contains(new Point((int) x + 1, (int) y - 1)) ||
							points.contains(new Point((int) x + 1, (int) y)) ||
							points.contains(new Point((int) x + 1, (int) y + 1))) {
						System.out.print("@");
					}
					System.out.print("o");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
			if (y > 10) {
				break;
			}
		}
	}

	private void addElfPositions(List<Point> elfPositions, List<Elf> elves) {
		elfPositions.clear();
		for (Elf elf : elves) {
			elf.move = false;
			elfPositions.add(new Point(elf.x, elf.y));
		}
	}

	private boolean doMoveIfPossible(List<Elf> elves, Map<Point, Integer> proposedMoves) {
		if (proposedMoves.size() == 0) {
			System.out.println("NO PROPOSED MOVES");
			return false;
		}
		int moves = 0;
		for (Elf elf : elves) {
			if (!elf.move) {
				continue;
			}
			Integer i = proposedMoves.get(new Point(elf.xP, elf.yP));
			if (i != null && i == 1) {
				elf.x = elf.xP;
				elf.y = elf.yP;
				moves++;
			}
		}
		if (moves == 0) {
			System.out.println("NO MOVES POSSIBLE");
			return false;
		}
		//System.out.println("moved " + moves);
		return true;
	}

	private Map<Point, Integer> proposeMove(List<Elf> elves, int iteration, Dir[] order, List<Point> elfPositions) {
		Map<Point, Integer> proposed = new HashMap<>();
		for (Elf elf : elves) {
			if (!elfPositions.contains(new Point(elf.x, elf.y - 1)) &&
					!elfPositions.contains(new Point(elf.x, elf.y + 1)) &&
					!elfPositions.contains(new Point(elf.x + 1, elf.y)) &&
					!elfPositions.contains(new Point(elf.x - 1, elf.y)) &&
					!elfPositions.contains(new Point(elf.x - 1, elf.y - 1)) &&
					!elfPositions.contains(new Point(elf.x + 1, elf.y + 1)) &&
					!elfPositions.contains(new Point(elf.x - 1, elf.y + 1)) &&
					!elfPositions.contains(new Point(elf.x + 1, elf.y - 1))) {
				continue;
			}

			int i = 0;
			boolean added = false;
			while (!added) {
				if (order[i] == Dir.NORTH) {
					if (!elfPositions.contains(new Point(elf.x, elf.y - 1))
							&& !elfPositions.contains(new Point(elf.x - 1, elf.y - 1))
							&& !elfPositions.contains(new Point(elf.x + 1, elf.y - 1))) {
						elf.xP = elf.x;
						elf.yP = elf.y - 1;
						added = true;
						//System.out.println(elf.y + "," + elf.x + " Propose: " + elf.yP + "," + elf.xP);
					}
				} else if (order[i] == Dir.SOUTH) {
					if (!elfPositions.contains(new Point(elf.x, elf.y + 1))
							&& !elfPositions.contains(new Point(elf.x - 1, elf.y + 1))
							&& !elfPositions.contains(new Point(elf.x + 1, elf.y + 1))) {
						elf.xP = elf.x;
						elf.yP = elf.y + 1;
						added = true;
						//System.out.println(elf.y + "," + elf.x + " Propose: " + elf.yP + "," + elf.xP);
					}
				} else if (order[i] == Dir.WEST) {
					if (!elfPositions.contains(new Point(elf.x - 1, elf.y))
							&& !elfPositions.contains(new Point(elf.x - 1, elf.y - 1))
							&& !elfPositions.contains(new Point(elf.x - 1, elf.y + 1))) {
						elf.xP = elf.x - 1;
						elf.yP = elf.y;
						added = true;
						//System.out.println(elf.y + "," + elf.x + " Propose: " + elf.yP + "," + elf.xP);
					}
				} else if (order[i] == Dir.EAST) {
					if (!elfPositions.contains(new Point(elf.x + 1, elf.y))
							&& !elfPositions.contains(new Point(elf.x + 1, elf.y - 1))
							&& !elfPositions.contains(new Point(elf.x + 1, elf.y + 1))) {
						elf.xP = elf.x + 1;
						elf.yP = elf.y;
						added = true;
						//System.out.println(elf.y + "," + elf.x + " Propose: " + elf.yP + "," + elf.xP);
					}
				}
				if (added) {
					Point p = new Point(elf.xP, elf.yP);
					Integer previousValue = proposed.putIfAbsent(p, 1);
					if (previousValue != null) {
						proposed.put(p, previousValue + 1);
					}
					elf.move = true;
					break;
				} else {
					i++;
					if (i == 4) {
						break;
					}
				}
			}
		}
		return proposed;
	}

	private Dir[] getOrder(int iteration) {
		int i = iteration % 4;
		Dir[] order = new Dir[4];
		Dir[] dirs = Dir.values();
		order[0] = dirs[i];
		i++;
		if (i == 4) {
			i = 0;
		}
		order[1] = dirs[i];
		i++;
		if (i == 4) {
			i = 0;
		}
		order[2] = dirs[i];
		i++;
		if (i == 4) {
			i = 0;
		}
		order[3] = dirs[i];
		i++;
		if (i == 4) {
			i = 0;
		}
		return order;
	}

	private List<Elf> setUpElves(List<String> list) {
		List<Elf> elves = new ArrayList<>();
		for (int y = 0; y < list.size(); ++y) {
			String row = list.get(y);
			for (int x = 0; x < list.get(0).length(); ++x) {
				if ((row.charAt(x) + "").equals("#")) {
					Elf elf = new Elf();
					elf.x = x;
					elf.y = y;
					elves.add(elf);
				}
			}
		}
		return elves;
	}
}
