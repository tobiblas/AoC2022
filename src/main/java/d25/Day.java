package d25;

import java.util.List;

import util.Task;

public class Day extends Task {

	public static void main(String[] args) {
		Day d = new Day();
		List<String> list = d.getStrings();
		d.p1(list);
	}

	private void p1(List<String> list) {
		long sum = 0;
		for (String s : list) {
			long v = process(s);
			System.out.println(s + "is " + v);
			sum += v;
		}
		String theOne = "2-==10--=-0101==1201";
		long v = process(theOne);
		System.out.println(theOne + "is " + v);
		System.out.println(sum);
		//System.out.println(((long) Math.pow(5, 18)));
		System.out.println(
				((long) Math.pow(5, 19)) * 2
						- ((long) Math.pow(5, 18) * 1)
						- ((long) Math.pow(5, 17) * 2)
						- ((long) Math.pow(5, 16) * 2)
						+ ((long) Math.pow(5, 15) * 1)
						- ((long) Math.pow(5, 14) * 0)
						- ((long) Math.pow(5, 13) * 1)
						- ((long) Math.pow(5, 12) * 1)
						- ((long) Math.pow(5, 11) * 2)
						- ((long) Math.pow(5, 10) * 1)
						- ((long) Math.pow(5, 9) * 0)
						+ ((long) Math.pow(5, 8) * 1)
						- ((long) Math.pow(5, 7) * 0)
						+ ((long) Math.pow(5, 6) * 1)
						- ((long) Math.pow(5, 5) * 2)
						- ((long) Math.pow(5, 4) * 2)
						+ ((long) Math.pow(5, 3) * 1)
						+ ((long) Math.pow(5, 2) * 2)
						- ((long) Math.pow(5, 1) * 0)
						+ 1);

		//2-==10--=-0101==1201
		//System.out.println(convert(sum));
	}

//	private String convert(long sum) {
//		String result = "";
//		String decimal = "" + sum;
//		
//		int i = 1;
//		while(true) {
//			int value = 0;
//			if (sum % 5*i == 0) {
//				
//			}
//			
//			long mul = (long) Math.pow(5, s.length() - 1 - i);
//			long v = value * mul;
//			sum += v;
//		}
//		}return result;
//
//	}

	private long process(String s) {
		long sum = 0;
		for (int i = s.length() - 1; i > -1; i--) {
			int value = 0;
			String ch = "" + s.charAt(i);
			try {
				value = Integer.parseInt(ch);
			} catch (Exception e) {
			}

			if (ch.equals("=")) {
				value = -2;
			} else if (ch.equals("-")) {
				value = -1;
			}
			long mul = (long) Math.pow(5, s.length() - 1 - i);
			long v = value * mul;
			sum += v;
		}
		return sum;
	}
}
