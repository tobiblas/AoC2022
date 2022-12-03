package d3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.Task;

public class D3 extends Task {

	public static void main(String[] args) {
		D3 day = new D3();
		List<String> list = day.getStrings();
		p1(list);
		p2(list);
	}

	private static void p1(List<String> list) {
		long currentSum = 0;
		for (int i = 0; i < list.size(); ++i) {
			String str = list.get(i);
			String str1 = str.substring(0, str.length() / 2);
			String str2 = str.substring(str.length() / 2, str.length());
			Set<String> comp1 = new HashSet<>();
			for (int j = 0; j < str1.length(); ++j) {
				comp1.add("" + str1.charAt(j));
			}
			Set<String> comp2 = new HashSet<>();
			for (int j = 0; j < str2.length(); ++j) {
				comp2.add("" + str2.charAt(j));
			}
			for (String a : comp2) {
				if (!comp1.add(a)) {
					currentSum += getValue(a);
					System.out.println(a);
					break;
				}
			}
		}
		System.out.println(currentSum);
	}

	private static void p2(List<String> list) {
		long currentSum = 0;
		List<String> elfs = new ArrayList<>();
		for (String elf : list) {
			elfs.add(elf);
			if (elfs.size() == 3) {

				Set<String> comp1 = new HashSet<>();
				for (int j = 0; j < elfs.get(0).length(); ++j) {
					comp1.add("" + elfs.get(0).charAt(j));
				}
				Set<String> comp2 = new HashSet<>();
				for (int j = 0; j < elfs.get(1).length(); ++j) {
					comp2.add("" + elfs.get(1).charAt(j));
				}
				Set<String> comp3 = new HashSet<>();
				for (int j = 0; j < elfs.get(2).length(); ++j) {
					comp3.add("" + elfs.get(2).charAt(j));
				}

				for (String a : comp2) {
					if (comp1.contains(a) && comp3.contains(a)) {
						currentSum += getValue(a);
						break;
					}
				}
				elfs.clear();
			}
		}
		System.out.println(currentSum);
	}

	private static int getValue(String a) {
		char b = a.charAt(0);
		int c = b;
		if (c < 91) {
			return c - 38;
		}
		return b - 96;
	}
}
