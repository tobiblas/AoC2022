package d6;

import java.util.List;

import util.Task;

public class D6 extends Task {

	public static void main(String[] args) {
		D6 day = new D6();
		List<String> list = day.getStrings();
		day.p1(list);
		day.p2(list);
	}

	private void p1(List<String> list) {
		String input = list.get(0);
		String lastFour = "";
		for (int i = 0; i < input.length(); ++i) {
			if (lastFour.length() < 4) {
				lastFour += input.charAt(i);
				if (lastFour.length() < 4)
					continue;
			} else {
				lastFour += input.charAt(i);
				lastFour = lastFour.substring(1);
			}
			if (marker(lastFour)) {
				System.out.println(i + 1);
				break;
			}
		}
	}

	private boolean marker(String input) {
		for (int i = 0; i < input.length(); ++i) {
			char a = input.charAt(i);
			for (int j = i + 1; j < input.length(); ++j) {
				char b = input.charAt(j);
				if (a == b) {
					return false;
				}
			}
		}
		return true;
	}

	private void p2(List<String> list) {
		String input = list.get(0);
		String lastFourteen = "";
		for (int i = 0; i < input.length(); ++i) {
			if (lastFourteen.length() < 14) {
				lastFourteen += input.charAt(i);
				if (lastFourteen.length() < 14)
					continue;
			} else {
				lastFourteen += input.charAt(i);
				lastFourteen = lastFourteen.substring(1);
			}
			if (marker(lastFourteen)) {
				System.out.println(i + 1);
				break;
			}
		}
	}

}
