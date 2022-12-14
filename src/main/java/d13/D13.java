package d13;

import java.util.ArrayList;
import java.util.List;

import util.Task;

public class D13 extends Task {

	public static void main(String[] args) {
		D13 day = new D13();
		List<String> list = day.getStrings();
		day.p1(list);
		day.p2(list);
	}

	private void p1(List<String> list) {
		int count = 0;
		String first = null;
		String second = null;
		int pairNumber = 1;
		List<Integer> rightOrder = new ArrayList<>();
		for (String str : list) {
			if (str.equals("")) {
				continue;
			}
			if (count % 2 == 0) {
				first = str;
			} else {
				second = str;
				System.out.println("Comparing: " + first + " with " + second);
				Result r = okOrder(first, second);
				if (r == Result.CONTINUE || r == Result.LEFT_RAN_OUT) {
					rightOrder.add(pairNumber);
					System.out.println(" OK!");
				} else {
					System.out.println(" NOT OK!");
				}
				pairNumber++;
			}
			count++;
		}
		int sum = 0;
		for (Integer i : rightOrder) {
			sum += i;
		}
		System.out.println(sum);
	}

	enum Result {
		CONTINUE,
		LEFT_RAN_OUT,
		RIGHT_RAN_OUT,
		NOT_EQUAL
	}

	private Result okOrder(String first, String second) {
		//System.out.println(first);
		//System.out.println(second);
		//System.out.println();
		List<String> itemsFirst = getItems(first.substring(1, first.length() - 1));
		List<String> itemsSecond = getItems(second.substring(1, second.length() - 1));
		boolean leftRanOut = false;
		boolean rightRanOut = false;
		for (int i = 0; i < itemsFirst.size(); ++i) {
			String f = itemsFirst.get(i);
			String s = "";
			try {
				s = itemsSecond.get(i);
//				if (i == 0 && s.equals("")) {
//					return false;
//				}
			} catch (Exception e) {
				//return false;
				return Result.RIGHT_RAN_OUT;
			}
			System.out.println("Comparing: " + f + " with " + s);
			if (f.equals("") && s.equals("")) {
//				return true;
				return Result.CONTINUE;
			}
			if (s.equals("")) {
				return Result.RIGHT_RAN_OUT;
			}
			if (f.equals("")) {
				return Result.LEFT_RAN_OUT;
			}
			if (isInteger(f) && isInteger(s)) {
				if (getInt(f) < getInt(s)) {
					//ok order
//					return true;
					return Result.LEFT_RAN_OUT;
				}
				if (getInt(f) == getInt(s)) {
					continue;
				} else {
					return Result.NOT_EQUAL;
//					return false;
				}
			}
			if (isInteger(f) || isInteger(s)) {
				//mixed types. convert to list.
				if (isInteger(f)) {
					f = "[" + f + "]";
				} else {
					s = "[" + s + "]";
				}
				return okOrder(f, s);
			} else {
				Result r = okOrder(f, s);
				if (r == Result.CONTINUE) {
					continue;
				} else if (r == Result.NOT_EQUAL) {
					return Result.NOT_EQUAL;
				} else if (r == Result.LEFT_RAN_OUT) {
					return Result.LEFT_RAN_OUT;
				} else if (r == Result.RIGHT_RAN_OUT) {
					return Result.RIGHT_RAN_OUT;
				}
			}
		}
		return Result.CONTINUE;
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private int getInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	private List<String> getItems(String str) {
		List<String> strs = new ArrayList<>();
		String[] strings = str.split(",");
		if (strings.length == 1) {
			strs.add(strings[0]);
			return strs;
		}
		if (strings.length == 0) {
			return strs;
		}
		int startBrackets = 0;
		String current = "";
		for (int i = 0; i < str.length(); ++i) {
			if (/*startBrackets == 0 && */leftBracket(str.charAt(i))) {
				if (startBrackets > 0) {
					current += str.charAt(i);
				}
				startBrackets++;
				continue;
			}
			if (rightBracket(str.charAt(i))) {
				startBrackets--;
				if (startBrackets == 0) {
					strs.add("[" + current + "]");
					current = "";
					i++;
					continue;
				} else {
					current += str.charAt(i);
					continue;
				}
			}
			if (startBrackets > 0) {
				current += str.charAt(i);
				continue;
			}

			try {
				int a = Integer.parseInt("" + str.charAt(i));
				boolean twoDigits = false;
				try {
					int b = Integer.parseInt("" + str.charAt(i + 1));
					strs.add("" + a + b);
					i++;
					twoDigits = true;
				} catch (Exception e) {
					int b = 0;
				}
				if (!twoDigits) {
					strs.add("" + a);
				}
			} catch (Exception e) {
				int a = 0;
			}
		}
		return strs;
	}

	private boolean leftBracket(char charAt) {
		return charAt == 91;
	}

	private boolean rightBracket(char charAt) {
		return charAt == 93;
	}

	private void p2(List<String> list) {

	}

}
