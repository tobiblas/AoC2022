package d4;

import java.util.ArrayList;
import java.util.List;

import util.Task;

public class D4 extends Task {

	public static void main(String[] args) {
		D4 day = new D4();
		List<String> list = day.getStrings();
		p1(list);
		p2(list);
	}

	private static void p1(List<String> list) {
		int count = 0;
		for (String str : list) {
			String first = str.split(",")[0];
			String second = str.split(",")[1];
			if (oneContainsTheOther(first, second)) {
				count++;
			}
		}
		System.out.println(count);
	}

	private static boolean oneContainsTheOther(String first, String second) {
		int x1 = Integer.parseInt(first.split("-")[0]);
		int y1 = Integer.parseInt(first.split("-")[1]);
		int x2 = Integer.parseInt(second.split("-")[0]);
		int y2 = Integer.parseInt(second.split("-")[1]);
		return x1 <= x2 && y1 >= y2 || x2 <= x1 && y2 >= y1;
	}

	private static void p2(List<String> list) {
		int count = 0;
		for (String str : list) {
			String first = str.split(",")[0];
			String second = str.split(",")[1];
			if (overlap2(first, second)) {
				count++;
			}
		}
		System.out.println(count);
	}

	private static boolean overlap2(String first, String second) {
		int x1 = Integer.parseInt(first.split("-")[0]);
		int y1 = Integer.parseInt(first.split("-")[1]);
		int x2 = Integer.parseInt(second.split("-")[0]);
		int y2 = Integer.parseInt(second.split("-")[1]);
		return !(y1 < x2 || y2 < x1);
	}

	private static boolean overlap(String first, String second) {
		int x1 = Integer.parseInt(first.split("-")[0]);
		int y1 = Integer.parseInt(first.split("-")[1]);
		int x2 = Integer.parseInt(second.split("-")[0]);
		int y2 = Integer.parseInt(second.split("-")[1]);
		List<Integer> firstInts = new ArrayList<>();
		List<Integer> secondInts = new ArrayList<>();
		for (int i = x1; i <= y1; ++i) {
			firstInts.add(i);
		}
		for (int i = x2; i <= y2; ++i) {
			secondInts.add(i);
		}
		for (Integer i : firstInts) {
			if (secondInts.contains(i)) {
				return true;
			}
		}
		return false;

	}

}
