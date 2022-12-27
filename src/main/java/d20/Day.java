package d20;

import java.util.ArrayList;
import java.util.List;

import util.Task;

public class Day extends Task {

	public static void main(String[] args) {
		Day d = new Day();
		List<String> list = d.getStrings();
		CoordinateDecryptor c = d.new CoordinateDecryptor();
		c.doIt(list);
	}

	public class CoordinateDecryptor {

		private static final long ENCRYPION_KEY = 811589153L;

		public void doIt(List<String> input) {
			// encrypted file
			List<Integer> file = new ArrayList<>();
			for (String s : input) {
				file.add(Integer.parseInt(s));
			}

			// mix the file
			//mixFile1(file);
			mixFile2(file);

//			811589153, 1623178306, -2434767459, 2434767459, -1623178306, 0, -3246356612, 6492713224, 
//			1623178306, -2434767459, 2434767459, -1623178306, 811589153, 0, -3246356612, 6492713224, 
//			-2434767459, 1623178306, 2434767459, -1623178306, 811589153, 0, -3246356612, 6492713224, 
		}

		private void mixFile2(List<Integer> file) {
			List<Num> numbers = new ArrayList<>();
			int j = 0;
			for (Integer num : file) {
				Num number = new Num();
				number.originalPosition = j;
//				if (num == 1) {
//					number.originalPosition = 0;
//				} else if (num == 2) {
//					number.originalPosition = 1;
//				} else if (num == -3) {
//					number.originalPosition = 2;
//				} else if (num == 3) {
//					number.originalPosition = 3;
//				} else if (num == -2) {
//					number.originalPosition = 4;
//				} else if (num == 0) {
//					number.originalPosition = 5;
//				} else if (num == 4) {
//					number.originalPosition = 6;
//				}

				number.value = num * ENCRYPION_KEY;
				numbers.add(number);
				j++;
			}
			print(numbers);
			for (int m = 0; m < 10; m++) {
				System.out.println(">>>>>>>>>Iteration " + (m + 1));
				for (int i = 0; i < file.size(); i++) {
					Num numberToMove = getNumberToMove(i, numbers);
					if (numberToMove.value == 0) {
						print(numbers);
						continue;
					}
					int currentIndex = numbers.indexOf(numberToMove);
					//problem 2
					int stepsFromEnd = numberToMove.value < 0 ? currentIndex : file.size() - 1 - currentIndex;
					int newPosition = 0;
					if (numberToMove.value > 0) {
						if (numberToMove.value < stepsFromEnd) {
							newPosition = (int) (currentIndex + numberToMove.value + 1);
							numbers.add(newPosition, numberToMove);
							numbers.remove(currentIndex);
						} else {
							newPosition = (int) ((numberToMove.value - stepsFromEnd) % (file.size() - 1));
							numbers.remove(currentIndex);
							numbers.add(newPosition, numberToMove);
						}
					} else {
						if (numberToMove.value > stepsFromEnd) {
							newPosition = (int) (currentIndex - numberToMove.value);
							numbers.add(newPosition, numberToMove);
							numbers.remove(currentIndex);
						} else {
							newPosition = (int) ((Math.abs(numberToMove.value) - stepsFromEnd) % (file.size() - 1));
							newPosition = file.size() - 1 - newPosition;
							numbers.remove(currentIndex);
							numbers.add(newPosition, numberToMove);
						}
					}
					print(numbers);
				}
			}

//			After 1 round of mixing:
//OK
//				0, -2434767459, 3246356612, -1623178306, 2434767459, 1623178306, 811589153
//OK
//				After 2 rounds of mixing:
//				0, 2434767459, 1623178306, 3246356612, -2434767459, -1623178306, 811589153
//NOK
//				After 3 rounds of mixing:
//				0, 811589153, 2434767459, 3246356612, 1623178306, -1623178306, -2434767459
//
//				After 4 rounds of mixing:
//				0, 1623178306, -2434767459, 811589153, 2434767459, 3246356612, -1623178306
//
//				After 5 rounds of mixing:
//				0, 811589153, -1623178306, 1623178306, -2434767459, 3246356612, 2434767459
//
//				After 6 rounds of mixing:
//				0, 811589153, -1623178306, 3246356612, -2434767459, 1623178306, 2434767459
//
//				After 7 rounds of mixing:
//				0, -2434767459, 2434767459, 1623178306, -1623178306, 811589153, 3246356612
//
//				After 8 rounds of mixing:
//				0, 1623178306, 3246356612, 811589153, -2434767459, 2434767459, -1623178306
//
//				After 9 rounds of mixing:
//				0, 811589153, 1623178306, -2434767459, 3246356612, 2434767459, -1623178306
//
//				After 10 rounds of mixing:
//				0, -2434767459, 1623178306, 3246356612, -1623178306, 2434767459, 811589153

			// get the sum of the three numbers that form the grove coordinates
			int zeroPosition = 0;
			for (int k = 0; k < numbers.size(); ++k) {
				Num num = numbers.get(k);
				if (num.value == 0) {
					zeroPosition = k;
					break;
				}
			}
			System.out.println("0 is on position " + zeroPosition);
			int pos1000 = (zeroPosition + 1000) % numbers.size();
			System.out.println("0 + 1000 holds " + numbers.get(pos1000).value);
			int pos2000 = (zeroPosition + 2000) % numbers.size();
			System.out.println("0 + 1000 holds " + numbers.get(pos2000).value);
			int pos3000 = (zeroPosition + 3000) % numbers.size();
			System.out.println("0 + 1000 holds " + numbers.get(pos3000).value);
			System.out.println(numbers.get(pos1000).value + numbers.get(pos2000).value + numbers.get(pos3000).value);
		}

		private void mixFile1(List<Integer> file) {
			List<Num> numbers = new ArrayList<>();
			int j = 0;
			for (Integer num : file) {
				Num number = new Num();
				number.originalPosition = j;
//				if (num == 1) {
//					number.originalPosition = 0;
//				} else if (num == 2) {
//					number.originalPosition = 1;
//				} else if (num == -3) {
//					number.originalPosition = 2;
//				} else if (num == 3) {
//					number.originalPosition = 3;
//				} else if (num == -2) {
//					number.originalPosition = 4;
//				} else if (num == 0) {
//					number.originalPosition = 5;
//				} else if (num == 4) {
//					number.originalPosition = 6;
//				}

				number.value = num * ENCRYPION_KEY;
				numbers.add(number);
				j++;
			}
			print(numbers);
			for (int i = 0; i < file.size(); i++) {
				Num numberToMove = getNumberToMove(i, numbers);
				if (numberToMove.value == 0) {
					print(numbers);
					continue;
				}
				int currentIndex = numbers.indexOf(numberToMove);
				//solution for provblem 1
				for (long k = 0; k < Math.abs(numberToMove.value); ++k) {
					currentIndex = moveOneStep(numbers, currentIndex, numberToMove);
					//print(numbers);
				}
				print(numbers);
			}
			// get the sum of the three numbers that form the grove coordinates
			int zeroPosition = 0;
			for (int k = 0; k < numbers.size(); ++k) {
				Num num = numbers.get(k);
				if (num.value == 0) {
					zeroPosition = k;
					break;
				}
			}
			System.out.println("0 is on position " + zeroPosition);
			int pos1000 = (zeroPosition + 1000) % numbers.size();
			System.out.println("0 + 1000 holds " + numbers.get(pos1000).value);
			int pos2000 = (zeroPosition + 2000) % numbers.size();
			System.out.println("0 + 1000 holds " + numbers.get(pos2000).value);
			int pos3000 = (zeroPosition + 3000) % numbers.size();
			System.out.println("0 + 1000 holds " + numbers.get(pos3000).value);
			System.out.println(numbers.get(pos1000).value + numbers.get(pos2000).value + numbers.get(pos3000).value);
		}

		private Num getNumberToMove(int i, List<Num> numbers) {
			for (Num number : numbers) {
				if (number.originalPosition == i) {
					return number;
				}
			}
			return null;
		}

		private int moveOneStep(List<Num> numbers, int currentIndex, Num numberToMove) {
			boolean moveForward = numberToMove.value > 0;
			int nextIndex = moveForward ? currentIndex + 1 : currentIndex - 1;
			if (nextIndex == 0) {
				nextIndex = numbers.size() - 1;
				numbers.add(numbers.size(), numberToMove);
				numbers.remove(1);
				return nextIndex;
			} else if (nextIndex == -1) {
				nextIndex = numbers.size() - 2;
				numbers.add(numbers.size() - 1, numberToMove);
				numbers.remove(0);
				return nextIndex;
			} else if (nextIndex == numbers.size() - 1) {
				nextIndex = 0;
				numbers.add(0, numberToMove);
				numbers.remove(numbers.size() - 2);
				return nextIndex;
			} else if (nextIndex == numbers.size()) {
				nextIndex = 1;
				numbers.add(1, numberToMove);
				numbers.remove(numbers.size() - 1);
				return nextIndex;
			}

			Num temp = numbers.get(nextIndex);
			numbers.set(nextIndex, numberToMove);
			numbers.set(currentIndex, temp);
			return nextIndex;
		}

		private void print(List<Num> numbers) {
//			for (Num n : numbers) {
//				System.out.print(n.value + ", ");
//			}
//			System.out.println();
		}
	}

	class Num {

		int originalPosition = 0;
		long value;
	}
}
