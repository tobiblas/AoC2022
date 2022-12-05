package d5;

import java.util.ArrayList;
import java.util.List;

import util.Task;

public class D5 extends Task {

	public static void main(String[] args) {
		D5 day = new D5();
		List<String> list = day.getStrings();
		p1(list);
	}

	private static void p1(List<String> list) {

		List<List<String>> cratesList = new ArrayList<>();
		int instructionRow = 11;
		List<String> instructions = new ArrayList<>();
		instructions = list.subList(instructionRow - 1, list.size());

		List<String> crate = new ArrayList<>();
		crate.add("B");
		crate.add("Q");
		crate.add("C");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("R");
		crate.add("Q");
		crate.add("W");
		crate.add("Z");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("B");
		crate.add("M");
		crate.add("R");
		crate.add("L");
		crate.add("V");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("C");
		crate.add("Z");
		crate.add("H");
		crate.add("V");
		crate.add("T");
		crate.add("W");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("D");
		crate.add("Z");
		crate.add("H");
		crate.add("B");
		crate.add("N");
		crate.add("V");
		crate.add("G");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("H");
		crate.add("N");
		crate.add("P");
		crate.add("C");
		crate.add("J");
		crate.add("F");
		crate.add("V");
		crate.add("Q");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("D");
		crate.add("G");
		crate.add("T");
		crate.add("R");
		crate.add("W");
		crate.add("Z");
		crate.add("S");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("C");
		crate.add("G");
		crate.add("M");
		crate.add("N");
		crate.add("B");
		crate.add("W");
		crate.add("Z");
		crate.add("P");
		cratesList.add(crate);
		crate = new ArrayList<>();
		crate.add("N");
		crate.add("J");
		crate.add("B");
		crate.add("M");
		crate.add("W");
		crate.add("Q");
		crate.add("F");
		crate.add("P");
		cratesList.add(crate);

		for (String instr : instructions) {
			String[] i = instr.split(" ");
			int amount = Integer.parseInt(i[1]);
			int from = Integer.parseInt(i[3]);
			int to = Integer.parseInt(i[5]);
			List<String> fromList = cratesList.get(from - 1);
			List<String> toList = cratesList.get(to - 1);
			List<String> move = new ArrayList<>();
			move = fromList.subList(Math.max(fromList.size() - amount, 0), fromList.size());
			toList.addAll(move);
			for (int j = 0; j < amount; ++j) {
				try {
					fromList.remove(fromList.size() - 1);
				} catch (Exception e) {
				}
			}
		}
		for (List<String> crateList : cratesList) {
			System.out.print(crateList.get(crateList.size() - 1));
		}
		System.out.println();
	}

}
