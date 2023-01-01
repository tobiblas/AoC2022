package d21;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import util.Task;

public class Day extends Task {

	public static void main(String[] args) {
		Day d = new Day();
		List<String> list = d.getStrings();
		//d.p1(list);
		d.p2(list);
	}

	class Monkey {

		String id;
		String operation;
	}

	private void p2(List<String> list) {
		List<Monkey> monkeys = new ArrayList<>();
		for (String s : list) {
			Monkey m = new Monkey();
			m.id = s.split(":")[0];
			m.operation = s.split(":")[1].trim();
			monkeys.add(m);
		}
		Monkey m = getMonkey(monkeys, "root");
		Monkey humn = getMonkey(monkeys, "humn");
		String[] split = m.operation.split(" ");

		//gå igenom trädet en gång och fyll i det som går.
		//skriv ut ett nytt träd.
		Monkey m2 = getMonkey(monkeys, split[2]);
		BigDecimal value = processMonkey(m2, monkeys);
		System.out.println(value);

		Monkey m1 = getMonkey(monkeys, split[0]);
		//for (long i = 1; i < Long.MAX_VALUE; i += 10000000L) {
		humn.operation = "3848301405790";
		//try {
		BigDecimal value2 = processMonkey(m1, monkeys);
		System.out.println(humn.operation + " gives");
		System.out.println(value2);

		//String expr = processMonkey2(m1, monkeys);

	}

	private String processMonkey2(Monkey monkey, List<Monkey> monkeys) {
		String op = monkey.operation;
		long value = 0;
		try {
			value = Integer.parseInt(op);
		} catch (NumberFormatException e) {
			if (op.equals("X")) {
				return op;
			}
			String[] parts = op.split(" ");
			String first = parts[0];
			String operator = parts[1];
			String second = parts[2];
			String val1 = processMonkey2(getMonkey(monkeys, first), monkeys);
			String val2 = processMonkey2(getMonkey(monkeys, second), monkeys);
			if (operator.equals("+")) {
				return "(" + val1 + "+" + val2 + ")";
			} else if (operator.equals("-")) {
				return "(" + val1 + "-" + val2 + ")";
			} else if (operator.equals("*")) {
				return "(" + val1 + "*" + val2 + ")";
			} else if (operator.equals("/")) {
				return "(" + val1 + "/" + val2 + ")";
			}
		}
		return op;
	}

	private void p1(List<String> list) {
		List<Monkey> monkeys = new ArrayList<>();
		for (String s : list) {
			Monkey m = new Monkey();
			m.id = s.split(":")[0];
			m.operation = s.split(":")[1].trim();
			monkeys.add(m);
		}
		BigDecimal value = processMonkey(getMonkey(monkeys, "root"), monkeys);
		System.out.println(value);
	}

	private BigDecimal processMonkey(Monkey monkey, List<Monkey> monkeys) {
		String op = monkey.operation;
		double value = 0;
		try {
			value = Long.parseLong(op);
		} catch (NumberFormatException e) {
			String[] parts = op.split(" ");
			String first = parts[0];
			String operator = parts[1];
			String second = parts[2];
			BigDecimal val1 = processMonkey(getMonkey(monkeys, first), monkeys);
			BigDecimal val2 = processMonkey(getMonkey(monkeys, second), monkeys);
			if (operator.equals("+")) {
				return val1.add(val2);
			} else if (operator.equals("-")) {
				return val1.subtract(val2);
			} else if (operator.equals("*")) {
				return val1.multiply(val2);
			} else if (operator.equals("/")) {
				try {
					return val1.divide(val2, RoundingMode.HALF_UP);
				} catch (ArithmeticException e2) {
					int a = 0;
				}
			}
		}
		return new BigDecimal(value);
	}

	private Monkey getMonkey(List<Monkey> monkeys, String first) {
		for (Monkey m : monkeys) {
			if (m.id.equals(first)) {
				return m;
			}
		}
		return null;
	}
}
