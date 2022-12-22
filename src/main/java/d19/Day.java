package d19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Task;

public class Day extends Task {

	public static void main(String[] args) {
		Day d = new Day();
		List<String> list = d.getStrings();
		d.p1(list);
		d.p2(list);
	}

	class Blueprint {

		int oreRobotCost;
		int clayRobotCost;
		int obsidianRobotOreCost;
		int obsidianRobotClayCost;
		int geodeRobotOreCost;
		int geodeRobotObsidianCost;
	}

	class Simulation {

		@Override
		public String toString() {
			return "Simulation [oreRobots=" + oreRobots + ", clayRobots=" + clayRobots + ", obsidianRobots=" + obsidianRobots + ", geodeRobots=" + geodeRobots + ", ore=" + ore
					+ ", clay=" + clay + ", obsidian=" + obsidian + ", geode=" + geode + "]";
		}

		int oreRobots = 1;
		int clayRobots;
		int obsidianRobots;
		int geodeRobots;
		int ore;
		int clay;
		int obsidian;
		int geode;
		String work = "";

		public Simulation copy() {
			Simulation copy = new Simulation();
			copy.clay = clay;
			copy.clayRobots = clayRobots;
			copy.geode = geode;
			copy.geodeRobots = geodeRobots;
			copy.obsidian = obsidian;
			copy.obsidianRobots = obsidianRobots;
			copy.ore = ore;
			copy.oreRobots = oreRobots;
			copy.work = work;
			return copy;
		}

		public void collect() {
			ore += oreRobots;
			clay += clayRobots;
			obsidian += obsidianRobots;
			geode += geodeRobots;
		}
	}

	private void p2(List<String> list) {
		int id = 1;
		long sum = 0;
		for (String blueprint : list) {
			blueprint = blueprint.substring(blueprint.indexOf(":") + 1);
			Blueprint b = new Blueprint();
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(blueprint);
			m.find();
			b.oreRobotCost = Integer.parseInt(m.group());
			m.find();
			b.clayRobotCost = Integer.parseInt(m.group());
			m.find();
			b.obsidianRobotOreCost = Integer.parseInt(m.group());
			m.find();
			b.obsidianRobotClayCost = Integer.parseInt(m.group());
			m.find();
			b.geodeRobotOreCost = Integer.parseInt(m.group());
			m.find();
			b.geodeRobotObsidianCost = Integer.parseInt(m.group());

			Simulation s = new Simulation();
			long t1 = System.currentTimeMillis();
			simulateBlueprint(b, s, 0);
			System.out.println("Time: " + (System.currentTimeMillis() - t1));
			System.out.println("This many ways lead to max: " + counter);
			int qualityLevel = id * max;
			System.out.println("Blueprint " + id + " max " + max);
			max = 0;
			sum += qualityLevel;
			id++;
		}
		System.out.println(sum);
		printResult();
	}

	private void printResult() {
		for (int i = 0; i < result.length; ++i) {
			Object o = result[i];
			if (o == null) {
				System.out.println("Empty");
				continue;
			}
			Map<String, Integer> map = (HashMap<String, Integer>) o;
			for (String s : map.keySet()) {
				System.out.print(s + "=" + map.get(s) + " ");
			}
			System.out.println();
		}
	}

	private static int max = 0;

	private static float oreToClayRatio = 20 / 8f;
	private static long counter = 0;

	private Object[] result = new Object[33];

	private void simulateBlueprint(Blueprint b, Simulation s, int iteration) {
		//int iterationsLeft = 24 - iteration;
		if (iteration == 32) {
			if (s.geode >= max) {
				max = s.geode;
//				if (s.geodeRobots == 1) {
//				System.out.println(s.toString());
//				}
			}
			if (s.geode >= 33) {
				counter++;
				String[] works = s.work.trim().split(" ");
				for (int i = 0; i < works.length; ++i) {
					String w = works[i];
					Object o = result[i];
					Map<String, Integer> map;
					if (o == null) {
						map = new HashMap<>();
						result[i] = map;
					} else {
						map = (HashMap<String, Integer>) o;
					}
					Integer counter = map.get(w);
					if (counter == null) {
						map.put(w, 1);
					} else {
						map.put(w, counter + 1);
					}
				}
				//System.out.println(s.work);
			}
			return;
		}

//		boolean buildOre = true;
//		boolean buildClay = false;
//		if (s.clayRobots != 0) {
//			float ratio = s.oreRobots / s.clayRobots;
//			if (ratio < oreToClayRatio) {
//				buildOre = true;
//			} else {
//				buildOre = false;
//			}
//		} else if (s.clayRobots == 0) {
//			buildClay = true;
//		}

		//har förtur
		boolean canBuildGeo = b.geodeRobotObsidianCost <= s.obsidian && b.geodeRobotOreCost <= s.ore;
		boolean canBuildObsidian = b.obsidianRobotClayCost <= s.clay && b.obsidianRobotOreCost <= s.ore;
		boolean canBuildClay = b.clayRobotCost <= s.ore;
		boolean canBuildOre = b.oreRobotCost <= s.ore;

		boolean shouldBuildOre = true; //!canBuildGeo && !canBuildObsidian;
		boolean shouldBuildClay = true; //!canBuildGeo && !canBuildObsidian;
		boolean shouldBuildObsidian = true; //!canBuildGeo;
		boolean shouldWait = true;
		//canBuildGeo = true;

//		Blueprint 1: 
//			Each ore robot costs 4 ore. 
//			Each clay robot costs 4 ore. 
//			Each obsidian robot costs 4 ore and 8 clay. 
//			Each geode robot costs 2 ore and 15 obsidian.

//		kolla när man måste ha byggt en geo. 
		if (iteration == 4) { //byggs minut 23
			canBuildClay = false;
			shouldWait = false;
		}
		if (iteration == 7) {
			shouldWait = false;
		}
		if (iteration == 9) {
			shouldWait = false;
		}
		if (iteration > 18) {
			shouldWait = false;
		}
		if (iteration > 20) {
			canBuildClay = false;
			canBuildOre = false;
		}
//		if (iteration == 14 || iteration == 15 || iteration == 16) {
//			shouldWait = true;
//		}
//		if (!canBuildGeo && iteration == 26) {
//			canBuildClay = false;
//			canBuildOre = false;
//			return;
//		}

		//tvinga ore på 4, clay på 7, obs på 13 och geo på 22 --> 17 geo i slutet

//			canBuildClay = false;
//		}
//		if (iteration == 4) {
//			canBuildClay = false;
//			shouldWait = false;
//		}
//		if (iteration == 6) {
//			shouldWait = false;
//		}
//		if (iteration == 7) {
//			canBuildOre = false;
//			shouldWait = false;
//		}
//		if (iteration == 8) {
//			canBuildOre = false;
//			shouldWait = false;
//		}
//		if (iteration == 9) {
//			canBuildOre = false;
//			shouldWait = false;
//		}
//		if (iteration == 10) {
//			canBuildOre = false;
//			shouldWait = false;
//		}
//		if (iteration == 11) {
//			canBuildOre = false;
//			shouldWait = false;
//		}
//		if (iteration == 12) {
//			canBuildOre = false;
//			shouldWait = false;
//		}
//		if (iteration == 13) {
//			canBuildClay = false;
//			canBuildOre = false;
//			shouldWait = false;
//		}
//		if (iteration == 15 && !canBuildObsidian) {
//			return;
//		}
//		if (iteration == 15) {
//			canBuildClay = false;
//			canBuildOre = false;
//			shouldWait = false;
//		}

		//Räkna ut beroende på state nu hur kan jag snabbast bygga en ny geo

		if (canBuildOre && shouldBuildOre) {
			Simulation s2 = s.copy();
			s2.ore -= b.oreRobotCost;
			s2.collect();
			s2.oreRobots++;
			s2.work += " ore";
			simulateBlueprint(b, s2, iteration + 1);
		}
		if (canBuildClay && shouldBuildClay) {
			Simulation s2 = s.copy();
			s2.ore -= b.clayRobotCost;
			s2.collect();
			s2.clayRobots++;
			s2.work += " cla";
			simulateBlueprint(b, s2, iteration + 1);
		}
		if (canBuildObsidian && shouldBuildObsidian) {
			Simulation s2 = s.copy();
			s2.clay -= b.obsidianRobotClayCost;
			s2.ore -= b.obsidianRobotOreCost;
			s2.collect();
			s2.obsidianRobots++;
			s2.work += " obs";
			simulateBlueprint(b, s2, iteration + 1);
		}
		if (canBuildGeo) {
			Simulation s2 = s.copy();
			s2.obsidian -= b.geodeRobotObsidianCost;
			s2.ore -= b.geodeRobotOreCost;
			s2.collect();
			s2.geodeRobots++;
			s2.work += " geo";
			simulateBlueprint(b, s2, iteration + 1);
		}
		if (shouldWait) {
			Simulation s2 = s.copy();
			s2.collect();
			s2.work += " w";
			simulateBlueprint(b, s2, iteration + 1);
		}
	}

//
//	Blueprint 1:
//		  Each ore robot costs 4 ore.
//		  Each clay robot costs 2 ore.
//		  Each obsidian robot costs 3 ore and 14 clay.
//		  Each geode robot costs 2 ore and 7 obsidian.
	private void p1(List<String> list) {

	}
}
