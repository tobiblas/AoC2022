package d11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import util.Task;

public class D11 extends Task {

	public static int count = 0;

	class Item {

		public Item(BigInteger value) {
			super();
			this.id = count;
			count++;
			this.value = value;
			op = value.toString();
		}

		private String op = "";
		int id;
		BigInteger value;
	}

	class Monkey {

		public Monkey(List<Item> items, boolean plus, boolean secondOperatorIsOld, int secondOperator, int divisabeByTest, int testOkThrowMonkey, int testNotOkThrowMonkey) {
			super();
			this.items = items;
			this.plus = plus;
			this.secondOperatorIsOld = secondOperatorIsOld;
			this.secondOperator = secondOperator;
			this.divisabeByTest = divisabeByTest;
			this.testOkThrowMonkey = testOkThrowMonkey;
			this.testNotOkThrowMonkey = testNotOkThrowMonkey;
		}

		private List<Item> items = new ArrayList<>();
		private boolean plus;
		private boolean secondOperatorIsOld;
		private int secondOperator;
		private int divisabeByTest;
		private int testOkThrowMonkey;
		private int testNotOkThrowMonkey;
		private long inspected = 0;
	}

	public static void main(String[] args) {
		D11 day = new D11();
		List<String> list = day.getStrings();
		day.p1(list);
		day.p2(list);
	}

	private Item item0 = null;

	private void p1(List<String> list) {
		List<Monkey> monkeys = parseMonkeys(list);
		//BigInteger divisor = new BigInteger("" + (23 * 19 * 13 * 17));
		String pattern = "";
		for (int round = 0; round < 10000; ++round) {
			for (int i = 0; i < monkeys.size(); ++i) {
				Monkey current = monkeys.get(i);
				while (current.items.size() > 0) {
					Item item = current.items.remove(0);
					String op = "";
					current.inspected++;
					BigInteger op2 = current.secondOperatorIsOld ? item.value : new BigInteger("" + current.secondOperator);
					if (current.plus) {
						item.value = item.value.add(op2);
					} else {
						item.value = item.value.multiply(op2);
					}
					BigInteger divisor = new BigInteger("" + (2 * 3 * 5 * 7 * 11 * 13 * 17 * 19));
					item.value = item.value.divideAndRemainder(divisor)[1];
					BigInteger[] test = item.value.divideAndRemainder(new BigInteger("" + current.divisabeByTest));
					if (test[1].toString().equals("0")) {
						//if (item % current.divisabeByTest == 0) {
						monkeys.get(current.testOkThrowMonkey).items.add(item);
						pattern += current.testOkThrowMonkey + ",";
					} else {
						monkeys.get(current.testNotOkThrowMonkey).items.add(item);
						pattern += current.testNotOkThrowMonkey + ",";
					}
				}
			}
			if (round % 100 == 0)
				System.out.println("Round " + round + " ");
			//System.out.println("Item0: " + item0.op);
//			pattern += '\n';
//			System.out.println(pattern);
			//printStatus(monkeys);
		}

		Collections.sort(monkeys, new Comparator<Monkey>() {

			@Override
			public int compare(Monkey o1, Monkey o2) {
				return o2.inspected < o1.inspected ? -1 : 1;
			}

		});

		System.out.println(monkeys.get(0).inspected * monkeys.get(1).inspected);

	}

	private void printStatus(List<Monkey> monkeys) {
		System.out.println();
		for (int i = 0; i < monkeys.size(); ++i) {
			System.out.print("Monkey" + i + " (" + monkeys.get(i).inspected + ") ");
			if (monkeys.get(i).items.size() == 0) {
				System.out.println();
				continue;
			}
			for (int j = 0; j < monkeys.get(i).items.size(); ++j) {
				System.out.print(monkeys.get(i).items.get(j) + ",");
			}
			System.out.println();
		}
	}

	private List<Monkey> parseMonkeys(List<String> list) {
		List<Monkey> monkeys = new ArrayList<>();
		List<Item> items = new ArrayList<>();
		items.add(new Item(new BigInteger("98")));
		items.add(new Item(new BigInteger("97")));
		items.add(new Item(new BigInteger("98")));
		items.add(new Item(new BigInteger("55")));
		items.add(new Item(new BigInteger("56")));
		items.add(new Item(new BigInteger("72")));
		Monkey m = new Monkey(items, false, false, 13, 11, 4, 7);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("73")));
		items.add(new Item(new BigInteger("99")));
		items.add(new Item(new BigInteger("55")));
		items.add(new Item(new BigInteger("54")));
		items.add(new Item(new BigInteger("88")));
		items.add(new Item(new BigInteger("50")));
		items.add(new Item(new BigInteger("55")));
		m = new Monkey(items, true, false, 4, 17, 2, 6);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("67")));
		items.add(new Item(new BigInteger("98")));
		m = new Monkey(items, false, false, 11, 5, 6, 5);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("82")));
		items.add(new Item(new BigInteger("91")));
		items.add(new Item(new BigInteger("92")));
		items.add(new Item(new BigInteger("53")));
		items.add(new Item(new BigInteger("99")));
		m = new Monkey(items, true, false, 8, 13, 1, 2);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("52")));
		items.add(new Item(new BigInteger("62")));
		items.add(new Item(new BigInteger("94")));
		items.add(new Item(new BigInteger("96")));
		items.add(new Item(new BigInteger("52")));
		items.add(new Item(new BigInteger("87")));
		items.add(new Item(new BigInteger("53")));
		items.add(new Item(new BigInteger("60")));
		m = new Monkey(items, false, true, 0, 19, 3, 1);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("94")));
		items.add(new Item(new BigInteger("80")));
		items.add(new Item(new BigInteger("84")));
		items.add(new Item(new BigInteger("79")));
		m = new Monkey(items, true, false, 5, 2, 7, 0);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("89")));
		m = new Monkey(items, true, false, 1, 3, 0, 5);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("70")));
		items.add(new Item(new BigInteger("59")));
		items.add(new Item(new BigInteger("63")));
		m = new Monkey(items, true, false, 3, 7, 4, 3);
		monkeys.add(m);
		return monkeys;
	}

	private List<Monkey> parseMonkeysTest2(List<String> list) {
		List<Monkey> monkeys = new ArrayList<>();
		List<Item> items = new ArrayList<>();
		items.add(new Item(new BigInteger("79")));
		items.add(new Item(new BigInteger("98")));
		Monkey m = new Monkey(items, false, false, 19, 23, 2, 3);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("54")));
		items.add(new Item(new BigInteger("65")));
		items.add(new Item(new BigInteger("75")));
		items.add(new Item(new BigInteger("74")));
		m = new Monkey(items, true, false, 6, 19, 2, 0);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("79")));
		items.add(new Item(new BigInteger("60")));
		items.add(new Item(new BigInteger("97")));
		//m = new Monkey(items, false, false, 1, 13, 1, 3);
		m = new Monkey(items, false, true, 0, 13, 1, 3);
		monkeys.add(m);

		items = new ArrayList<>();
		items.add(new Item(new BigInteger("74")));
		m = new Monkey(items, true, false, 3, 17, 0, 1);
		monkeys.add(m);
		return monkeys;
	}

	private void p2(List<String> list) {

	}

}
