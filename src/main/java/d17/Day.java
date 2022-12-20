package d17;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Task;

public class Day extends Task {

	public static void main(String[] args) throws IOException {
		Day d = new Day();
		List<String> list = d.getStrings();
		d.p1(list);
		d.p2(list);
	}

	private void p2(List<String> list) {

	}

	class Block {

		List<Point> points = new ArrayList<>();

		int offsetFromLeftAtStart = 2;
		int width = 0;
		int currentOffset = 0;
		long currentY = 0;
		int height = 0;

		public void print() {
			int[][] ps = new int[5][5];
			for (Point p : points) {
				ps[p.x][p.y] = 1;
			}
			for (int y = 0; y < 5; ++y) {
				for (int x = 0; x < 5; ++x) {
					System.out.print(ps[x][y] == 0 ? '.' : '#');
				}
				System.out.println();
			}
			System.out.println();
		}

		public void position(long currentHighestPoint) {
			currentOffset = offsetFromLeftAtStart;
			//bottom edge 3 above floor or highest point.
			currentY = currentHighestPoint + 3 + (height);
		}

		public void blow(String currentJet, List<Block> blocksAtRest) {
			int xFromStart = currentOffset;
			if (currentJet.equals("<")) {
				if (currentOffset == 0) {
					//System.out.println("Not affected by jet");
					return;
				}
				currentOffset--;
			} else if (currentJet.equals(">")) {
				if (currentOffset + width == 7) {
					//System.out.println("Not affected by jet");
					return;
				}
				currentOffset++;
			}
			if (intersects(blocksAtRest)) {
				//System.out.println("Not affected by jet");
				currentOffset = xFromStart;
			} else {
				//System.out.println("pushed 1 step " + currentJet);
			}
		}

		private boolean intersects(List<Block> blocksAtRest) {
			for (Block b : blocksAtRest) {
				for (Point p : points) {
					for (Point p2 : b.points) {
						if (p.x + currentOffset == p2.x + b.currentOffset && p.y + currentY - height == p2.y + b.currentY - b.height) {
							return true;
						}
					}
				}
			}
			return false;
		}

		public void fall(List<Block> blocksAtRest) {
			long yFromStart = currentY;
			currentY--;
			if (intersects(blocksAtRest) || touchesGround()) {
				//System.out.println("Not falling");
				currentY = yFromStart;
			} else {
				//System.out.println("Fell to " + currentY);
			}
		}

		private boolean touchesGround() {
			return currentY == 0;
		}

		public Block copy() {
			Block copy = new Block();
			copy.width = width;
			copy.currentOffset = currentOffset;
			copy.currentY = currentY;
			copy.points = this.points;
			copy.height = height;
			return copy;
		}
	}

	private void p1(List<String> list) throws IOException {
		//block1.
		List<Block> blocks = new ArrayList<>();
		Block block = new Block();
		block.points.add(new Point(0, 0));
		block.points.add(new Point(1, 0));
		block.points.add(new Point(2, 0));
		block.points.add(new Point(3, 0));
		block.width = 4;
		block.height = 1;
		blocks.add(block);

		block = new Block();
		block.points.add(new Point(1, 0));
		block.points.add(new Point(1, 1));
		block.points.add(new Point(1, 2));
		block.points.add(new Point(0, 1));
		block.points.add(new Point(2, 1));
		block.width = 3;
		block.height = 3;
		blocks.add(block);

		block = new Block();
		block.points.add(new Point(2, 2));
		block.points.add(new Point(2, 1));
		block.points.add(new Point(2, 0));
		block.points.add(new Point(0, 0));
		block.points.add(new Point(1, 0));
		block.width = 3;
		block.height = 3;
		blocks.add(block);

		block = new Block();
		block.points.add(new Point(0, 3));
		block.points.add(new Point(0, 2));
		block.points.add(new Point(0, 1));
		block.points.add(new Point(0, 0));
		block.width = 1;
		block.height = 4;
		blocks.add(block);

		block = new Block();
		block.points.add(new Point(0, 1));
		block.points.add(new Point(0, 0));
		block.points.add(new Point(1, 1));
		block.points.add(new Point(1, 0));
		block.width = 2;
		block.height = 2;
		blocks.add(block);

//		for (Block b : blocks) {
//			b.print();
//		}

		List<Block> blocksAtRest = new ArrayList<>();

		int iteration = 0;
		String jetStreams = list.get(0);
		int currentStreamIndex = 0;
		while (iteration < 430 + 1740 + 750) {
			//efter block 50 vet vi att det byggs 53 per 35 block.
			//höjd efter 50 = 78.

			//först 430 block sen uprepar sig 1740 i taget
			//upprepningssekvensen är 1740. Den börjar på block 431.
			//430 block bygger 637. sen bygger varje 1740 2666 i höjd
			//vi har 574 712 643 hela block upp till 1 biljon. Sista blocket måste simuleras.

			if ((iteration - 430) % 1740 == 0) {
				System.out.println(currentHighestPoint);
			}
			currentStreamIndex = dropRock(jetStreams, currentStreamIndex, blocks, iteration, blocksAtRest);
			///print(blocksAtRest, currentHighestPoint);
			iteration++;
		}
		// 1 514 285 714 210 + 78
		// 1 514 285 714 288

		//637 + 574 712 643 * 2666 + 1173(det som byggs upp till 1B av 1740 rest) 750 block till behövs. 
		//så kolla höjd efter 630 + 1740 och jämför med 630 + 1740 + 750
		// 1532183907746 too low
		// 1532183908048

		//när börjar mönstret att upprepa sig?  upprepar sig med intervallet 1740
		//ide: simulera till 2022. Sen simulera 1740 till 
		//NÄR BÖRJARD DET UPPRePA SIG? HUR LÅNG ÄR UPPREPNINGEN? SEN KAN KAN KÖRA UPPREPNINGEN IGEN OCH IGEN.
		//15 block först. Sen en sekvens på 34 som upprepar sig forever
		//hur högt är 15 först blocken? hur högt bygger de 34 som upprepar sig?
		String s = buff.toString();
		//Files.writeString(Path.of("/Users/tobias/AoC2022/src/main/java/d17/output"), buff);
		String s1 = s.substring(0, 5);
		int i = s.indexOf(s1, 6);
		//börjar upprepa sig efter index 39, 10091
		System.out.println(currentHighestPoint);
	}

	long currentHighestPoint = 0;
	StringBuffer buff = new StringBuffer();

	private void print(List<Block> blocksAtRest, long currentHighestPoint) {
		long[][] shaft = new long[7][(int) (currentHighestPoint + 1)];
		for (Block b : blocksAtRest) {
			for (Point p : b.points) {
				//System.out.println("Adding " + (p.x + b.currentOffset) + "," + (p.y + b.currentY));
				shaft[p.x + b.currentOffset][(int) (p.y + b.currentY - b.height)] = 1;
			}
		}
		for (int y = (int) currentHighestPoint; y >= 0; --y) {
			for (int x = 0; x < 7; ++x) {
				System.out.print(shaft[x][y] == 0 ? '.' : '#');
			}
			System.out.println();
		}
		System.out.println();
	}

	private int dropRock(String jetStreams, int currentStreamIndex, List<Block> blocks, int iteration, List<Block> blocksAtRest) {

		Block blockFalling = blocks.get(iteration % 5);
		blockFalling.position(currentHighestPoint);
//		if (iteration % 1000 == 0) {
//			System.out.println("Rock" + iteration + " begins falling");
//		}
		int blows = 0;
		//blocksAtRest.add(blockFalling);
		//currentHighestPoint += 10;
		//print(blocksAtRest, currentHighestPoint);
		//currentHighestPoint -= 10;
		//blocksAtRest.remove(iteration);
		while (true) {
			String currentJet = "" + jetStreams.charAt(currentStreamIndex % jetStreams.length());
			blockFalling.blow(currentJet, blocksAtRest);
			blows++;
			long y = blockFalling.currentY;
			blockFalling.fall(blocksAtRest);
			if (y == blockFalling.currentY) {
//				currentStreamIndex++;
//				currentJet = "" + jetStreams.charAt(currentStreamIndex % jetStreams.length());
//				blockFalling.blow(currentJet, blocksAtRest);
				blocksAtRest.add(blockFalling.copy());
				if (iteration % 5 == 0 && iteration != 0) {
					buff.append(" ");
				}
				buff.append(blockFalling.currentOffset);
				if (blocksAtRest.size() > 300) {
					blocksAtRest.remove(0);
				}
				currentStreamIndex++;
				for (Block bb : blocksAtRest) {
					currentHighestPoint = bb.currentY > currentHighestPoint ? bb.currentY : currentHighestPoint;
				}
				//System.out.println(blows);
				return currentStreamIndex;
			}
			//	blocksAtRest.add(blockFalling);
			//		currentHighestPoint += 10;
			//	print(blocksAtRest, currentHighestPoint);
			//		currentHighestPoint -= 10;
//			blocksAtRest.remove(iteration);
			currentStreamIndex++;
		}
	}

}
