package d2;

import java.util.List;

import util.Task;

public class D2 extends Task {

	private static final String PASE = "B";
	private static final String SAX1 = "C";
	private static final String WIN = "Z";
	private static final String DRAW = "Y";
	private static final String LOOSE = "X";
	private static final String STEN = "A";

	public static void main(String[] args) {
		D2 d1 = new D2();
		List<String> list = d1.getStrings();
		long currentSum = 0;
		for (int i = 0; i < list.size(); ++i) {
			String row = list.get(i);
			int score = getOutCome(row);
			currentSum += score;
		}
		System.out.println(currentSum);
	}

	private static int getOutCome(String row) {
		String a = row.split(" ")[0];
		String b = row.split(" ")[1];
		if (a.equals(STEN) && b.equals(LOOSE)) {
			return 3 + 0;
		}
		if (a.equals(STEN) && b.equals(DRAW)) {
			return 1 + 3;
		}
		if (a.equals(STEN) && b.equals(WIN)) {
			return 2 + 6;
		}
		if (a.equals(PASE) && b.equals(LOOSE)) {
			return 1 + 0;
		}
		if (a.equals(PASE) && b.equals(DRAW)) {
			return 2 + 3;
		}
		if (a.equals(PASE) && b.equals(WIN)) {
			return 3 + 6;
		}
		if (a.equals(SAX1) && b.equals(LOOSE)) {
			return 2 + 0;
		}
		if (a.equals(SAX1) && b.equals(DRAW)) {
			return 3 + 3;
		}
		if (a.equals(SAX1) && b.equals(WIN)) {
			return 1 + 6;
		}
		return 0;
	}
}
