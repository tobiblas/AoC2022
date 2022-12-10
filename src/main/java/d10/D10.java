package d10;

import java.util.ArrayList;
import java.util.List;

import util.Task;

public class D10 extends Task {

	public static void main(String[] args) {
		D10 day = new D10();
		List<String> list = day.getStrings();
		day.p1(list);
		day.p2(list);
	}

	private void p1(List<String> list) {
		List<String> drawings = new ArrayList<>();
		int spritePosition = 1; //spritePosition
		int cycle = 1;
		int posBeingDrawn = cycle - 1;
		boolean addStarted = false;
		int lastAdd = 0;
		for (String str : list) {
			posBeingDrawn = (cycle - 1) % 40;
			if (str.equals("noop")) {
				draw(drawings, spritePosition, posBeingDrawn);
				cycle++;
				continue;
			}

			lastAdd = Integer.parseInt(str.split(" ")[1]);
			draw(drawings, spritePosition, posBeingDrawn);
			cycle++;
			posBeingDrawn = (cycle - 1) % 40;

			draw(drawings, spritePosition, posBeingDrawn);
			spritePosition += lastAdd;

			printMatrix(drawings, cycle, spritePosition);
			cycle++;
		}
		printMatrix(drawings, cycle, spritePosition);
	}

	private void draw(List<String> drawings, long spritePosition, int posBeingDrawn) {
		if (spritePosition == posBeingDrawn || spritePosition - 1 == posBeingDrawn || spritePosition + 1 == posBeingDrawn) {
			drawings.add("#");
			return;
		}
		drawings.add(".");
	}

	private void printMatrix(List<String> drawings, int cycle, int spritePosition) {
		for (int i = 0; i < cycle; ++i) {
			System.out.print(drawings.get(i));
			if ((i + 1) % 40 == 0) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.println("spritePosition: " + spritePosition);
	}

	private void p2(List<String> list) {

	}

}
