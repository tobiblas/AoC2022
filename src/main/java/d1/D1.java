package d1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import util.Task;

public class D1 extends Task {

	public static void main(String[] args) {
		D1 d1 = new D1();
		List<String> list = d1.getStrings();

		List<Long> all = new ArrayList<>();
		long current = 0;
		long currentSum = 0;
		//int increases = 0;
		for (int i = 0; i < list.size(); ++i) {
			try {
				String a = list.get(i);
				current = Long.parseLong(a);
			} catch (NumberFormatException e) {
				current = 0;
			}
			if (current == 0) {
				all.add(currentSum);
				currentSum = 0;
			} else {
				currentSum += Long.parseLong(list.get(i));
			}

		}
		all.sort(new Comparator<Long>() {

			@Override
			public int compare(Long o1, Long o2) {
				return (int) (o2 - o1);
			}
		});
		System.out.println(all.get(0) + all.get(1) + all.get(2));
	}
}
