package d15;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.Task;

public class D15 extends Task {

	private class Beacon {

		int x = 0;
		int y = 0;
	}

	private class Sensor {

		Beacon b;
		int x = 0;
		int y = 0;
	}

	public static void main(String[] args) {
		D15 day = new D15();
		List<String> list = day.getStrings();
		day.p1(list);
		day.p2(list);
	}

	private void p1(List<String> list) {
		List<Sensor> sensors = new ArrayList<>();
		List<Beacon> beacons = new ArrayList<>();
		for (String str : list) {
			String[] split = str.split(": closest beacon is at ");
			Sensor s = new Sensor();
			String coords = split[0].replace("Sensor at ", "");
			String[] xAndY = coords.split(",");
			s.x = Integer.parseInt(xAndY[0].split("=")[1]);
			s.y = Integer.parseInt(xAndY[1].split("=")[1]);
			sensors.add(s);
			coords = split[1];
			xAndY = coords.split(",");
			Beacon b = new Beacon();
			b.x = Integer.parseInt(xAndY[0].split("=")[1]);
			b.y = Integer.parseInt(xAndY[1].split("=")[1]);
			s.b = b;
			beacons.add(b);
		}
		Point p = null;
		long l = System.currentTimeMillis();
		int maxX = 4000000;
		for (int i = 0; i < maxX; ++i) {
			boolean rowFilled = rowFilled(sensors, 3391794, 0, maxX);
			if (i % 1000 == 0) {
				System.out.print(i + " ");
				System.out.println(System.currentTimeMillis() - l);
			}
			if (!rowFilled) {
				System.out.println("can have beacon. row " + i);
				break;
			}
		}
		System.out.println(System.currentTimeMillis() - l);
	}

	//gå igenom varje rad och varje sensor. Varje sensor får ange sitt intervall den kan se. Sen kan vi avgöra med alla intervallerna om det är någon punkt som inte kan ses.

	class Interval {

		int min;
		int max;
	}

	private boolean rowFilled(List<Sensor> sensors, int rowToCheck, int xMin, int xMax) {
		List<Interval> intervals = new ArrayList<>();
		for (Sensor sensor : sensors) {
			//vilka punkter på denna rad kan sensorn se?
			int distanceToBeacon = getBeaconDist(sensor);
			int distanceToRowToCheck = getDistToRow(sensor, rowToCheck);
			if (distanceToRowToCheck > distanceToBeacon) {
				continue;
			}
			int distLeftAfterReachingRow = distanceToBeacon - distanceToRowToCheck;
			Interval v = new Interval();
			v.min = sensor.x - distLeftAfterReachingRow;
			v.max = sensor.x + distLeftAfterReachingRow;
			if (intervals.size() == 0) {
				intervals.add(v);
			} else {
				mergeIntervals(intervals, v, true);
			}
		}

		List<Interval> nonMergable = new ArrayList<>();
		while (intervals.size() > 0) {
			Interval i = intervals.remove(0);
			boolean mergable = mergeIntervals(intervals, i, false);
			if (!mergable) {
				nonMergable.add(i);
			}
		}
		System.out.println(rowToCheck + "-" + nonMergable.size());
		return checkIfIntervalCovered(xMin, xMax, nonMergable);
	}

	private boolean checkIfIntervalCovered(int xMin, int xMax, List<Interval> nonMergable) {
		nonMergable.sort(new Comparator<Interval>() {

			@Override
			public int compare(Interval o1, Interval o2) {
				return o1.min - o2.min;
			}

		});
		int lastMax = 0;
		for (Interval i : nonMergable) {
			if (i.max < xMin) {
				continue;
			}
			if (lastMax < i.min - 1) {
				int gap = lastMax + 1;
				if (xMin < gap && xMax > gap) {
					return false;
				}
			}
			lastMax = i.max;
		}
		return true;
	}

	private boolean mergeIntervals(List<Interval> intervals, Interval v, boolean add) {
		for (Interval interval : intervals) {
			if (v.min >= interval.min && v.min <= interval.max && v.max >= interval.max) {
				interval.max = v.max;
				return true;
			}
			if (v.max >= interval.min && v.max <= interval.max && v.min <= interval.min) {
				interval.min = v.min;
				return true;
			}
			if (v.min <= interval.min && v.max >= interval.max) {
				interval.min = v.min;
				interval.max = v.max;
				return true;
			}
		}
		if (add) {
			intervals.add(v);
		}
		return false;
	}

	private void mergeIntervals(List<Interval> intervals) {

	}

	private Point checkRow(List<Sensor> sensors, int rowToCheck, int xMin, int xMax) {
		//Set<Point> positionThatCannotHaveABeacon = new HashSet<>();
		Set<Integer> pos = new HashSet<>();
		for (Sensor sensor : sensors) {
			int taxiDistanceToBeacon = getBeaconDist(sensor);
			int distanceToRowToCheck = getDistToRow(sensor, rowToCheck);
			if (distanceToRowToCheck <= taxiDistanceToBeacon) {
				int currentDist = distanceToRowToCheck;
				int currentX = sensor.x;
				//if (!isBeacon(beacons, sensor.x, rowToCheck)) {
				//positionThatCannotHaveABeacon.add(new Point(sensor.x, rowToCheck));
				if (sensor.x >= xMin && sensor.x < xMax + 1)
					pos.add(sensor.x);
				//}
				while (currentDist <= taxiDistanceToBeacon) {
					//check left
					currentX--;
					currentDist = Math.abs(sensor.x - currentX) + Math.abs(sensor.y - rowToCheck);
					if (currentDist <= taxiDistanceToBeacon /*&& !isBeacon(beacons, currentX, rowToCheck)*/) {
						//positionThatCannotHaveABeacon.add(new Point(currentX, rowToCheck));
						if (currentX >= xMin && currentX < xMax + 1)
							pos.add(currentX);
					}
				}
				currentDist = distanceToRowToCheck;
				currentX = sensor.x;
				while (currentDist <= taxiDistanceToBeacon) {
					//check right
					currentX++;
					currentDist = Math.abs(sensor.x - currentX) + Math.abs(sensor.y - rowToCheck);
					if (currentDist <= taxiDistanceToBeacon /*&& !isBeacon(beacons, currentX, rowToCheck)*/) {
						//positionThatCannotHaveABeacon.add(new Point(currentX, rowToCheck));
						if (currentX >= xMin && currentX < xMax + 1)
							pos.add(currentX);
					}
				}
			}
		}
		if (pos.size() != xMax + 1) {
			System.out.println("found it. Its row " + rowToCheck);
		}
//		for (int x = xMin; x < xMax; ++x) {
//			boolean positionCantHaveBeacon = false;
//			for (Point p : positionThatCannotHaveABeacon) {
//				if (p.x == x) {
//					positionCantHaveBeacon = true;
//					break;
//				}
//			}
//			if (!positionCantHaveBeacon) {
//				return new Point(x, rowToCheck);
//			}
//		}
		return null;
	}

	private boolean isBeacon(List<Beacon> beacons, int x, int y) {
		for (Beacon b : beacons) {
			if (b.x == x && b.y == y) {
				return true;
			}
		}
		return false;
	}

	private int getDistToRow(Sensor sensor, int rowToCheck) {
		return Math.abs(sensor.y - rowToCheck);
	}

	private int getBeaconDist(Sensor sensor) {
		return Math.abs(sensor.x - sensor.b.x) + Math.abs(sensor.y - sensor.b.y);
	}

	private void p2(List<String> list) {

	}

}
