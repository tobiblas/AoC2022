package d16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import d16.Dijkstras.Edge;
import util.Task;

public class Day extends Task {

	Map<String, Node> nodes = new HashMap<>();

	public static void main(String[] args) {
		Day d = new Day();
		List<String> list = d.getStrings();
		d.p1(list);
	}

	long t1;
	TreeNode results = null;

	private void p1(List<String> list) {

		//Create graph from input
		for (String str : list) {
			String[] first = str.split(";")[0].split(" ");
			String name = first[1].trim();
			String flowPart = first[4];
			int flow = Integer.parseInt(flowPart.split("=")[1]);
			nodes.put(name, new Node(flow, name));
		}
		for (String str : list) {
			String[] first = str.split(";")[0].split(" ");
			String name = first[1].trim();
			String second = str.split(";")[1];
			String[] parts = second.split(" ");
			Node node = nodes.get(name);
			for (int i = 5; i < parts.length; ++i) {
				String valve = parts[i];
				valve = valve.replace(",", "");
				Node child = nodes.get(valve);
				node.addChild(child);
			}
		}

		calculateDistances(nodes);
		t1 = System.currentTimeMillis();
		bfsCaves();
		System.out.println("Time " + (System.currentTimeMillis() - t1));
	}

	class Persons {

		List<String> path;
		List<String> path2;
	}

	private void bfsCaves() {
		Set<String> caves = nodes.keySet();
		List<String> cavesList = new ArrayList<String>();
		for (String cave : caves) {
			if (nodes.get(cave).getFlowRate() > 0) {
				cavesList.add(cave);
			}
		}
		cavesList.remove("AA");

//		Persons p3 = new Persons();
//		p3.path = new ArrayList<>();
//		p3.path2 = new ArrayList<>();
//		p3.path.add("FS");
//		p3.path.add("HG");
//		p3.path.add("PZ");
//		p3.path.add("JE");
//		p3.path.add("YB");
//		p3.path.add("KI");
//		p3.path2.add("MD");
//		p3.path2.add("DS");
//		p3.path2.add("YW");
//		p3.path2.add("SS");
//		p3.path2.add("IK");
//		p3.path2.add("CR");

		//2206 P1: [FS, HG, PZ, JE, YB, KI] P2: [MD, DS, YW, SS, IK, CR]
		//int score1 = test(p3, nodes);

		int bestOfAll = 0;

		List<Persons> queue = new ArrayList<>();
		List<Persons> nextQueue = new ArrayList<>();

		for (String caveRoot1 : cavesList) {
			for (String caveRoot2 : cavesList) {
				if (caveRoot1.equals(caveRoot2)) {
					continue;
				}
				Persons persons = new Persons();
				persons.path = new ArrayList<>();
				persons.path2 = new ArrayList<>();
				persons.path.add(caveRoot1);
				persons.path2.add(caveRoot2);
				queue.add(persons);
			}
		}

		int bestScore = 0;
		int i = 1;
		while (true) {
			int counter = 0;
			while (!queue.isEmpty()) {
				counter++;
				Persons persons = queue.remove(queue.size() - 1);
				//queue.remove(persons);

				int score = test(persons, nodes);
				if (counter % 100000 == 0) {
					System.out.println(counter + " score = " + score + " P1: " + persons.path + " P2: " + persons.path2 + " queue=" + nextQueue.size());
				}
				if (score > bestScore) {
					System.out.println("New best score " + score + " P1: " + persons.path + " P2: " + persons.path2);
					bestScore = score;
				}
				if (i == 2 && score < 750) {
					continue;
				}
				if (i == 3 && score < 1300) {
					continue;
				}
				if (i == 4 && score < 1550) {
					continue;
				}
				if (i == 5 && score < 1900) {
					continue;
				}
				if (i == 7) {
					continue;
				}
				if (i == 5) {
					for (String caveRoot1 : cavesList) {
						if (!persons.path.contains(caveRoot1) && !persons.path2.contains(caveRoot1)) {
							Persons p = new Persons();
							p.path = new ArrayList<>();
							p.path2 = new ArrayList<>();
							p.path.addAll(persons.path);
							p.path2.addAll(persons.path2);
							p.path.add(caveRoot1);
							//System.out.println("Added P1: " + p.path + " P2: " + p.path2);
							nextQueue.add(p);
						}
					}
					for (String caveRoot1 : cavesList) {
						if (!persons.path.contains(caveRoot1) && !persons.path2.contains(caveRoot1)) {
							Persons p = new Persons();
							p.path = new ArrayList<>();
							p.path2 = new ArrayList<>();
							p.path.addAll(persons.path);
							p.path2.addAll(persons.path2);
							p.path2.add(caveRoot1);
							//System.out.println("Added P1: " + p.path + " P2: " + p.path2);
							nextQueue.add(p);
						}
					}
				}

				for (String caveRoot1 : cavesList) {
					for (String caveRoot2 : cavesList) {
						if (caveRoot1.equals(caveRoot2)) {
							continue;
						}
						if (!persons.path.contains(caveRoot1) && !persons.path2.contains(caveRoot1) &&
								!persons.path2.contains(caveRoot2) && !persons.path.contains(caveRoot2)) {
							Persons p = new Persons();
							p.path = new ArrayList<>();
							p.path2 = new ArrayList<>();
							p.path.addAll(persons.path);
							p.path2.addAll(persons.path2);
							p.path.add(caveRoot1);
							p.path2.add(caveRoot2);
							//System.out.println("Added P1: " + p.path + " P2: " + p.path2);
							nextQueue.add(p);
						}
					}
				}
			}
			System.out.println("Depth = " + i);
			System.out.println("Size = " + nextQueue.size());
			i++;
			queue = nextQueue;
			nextQueue = new ArrayList<>();
			if (queue.size() == 0) {
				break;
			}
		}
		System.out.println("BEST = " + bestScore);
	}

	private int test(Persons persons, Map<String, Node> nodes) {
		List<String> path1 = persons.path;
		List<String> path2 = persons.path2;
		//List<String> visits = new ArrayList<>();
		//calculate score
		int currentMinute = 1;
		Node currentCave1 = nodes.get("AA");
		Node currentCave2 = nodes.get("AA");

		int currentFlow = 0;
		int currentScore = 0;
		Node nextCave = nodes.get(path1.get(0));
		int distance1 = currentCave1.getDistanceTo(nextCave.getName());
		currentCave1 = nextCave;

		nextCave = nodes.get(path2.get(0));
		int distance2 = currentCave2.getDistanceTo(nextCave.getName());
		currentCave2 = nextCave;

		int caveIndex1 = 0;
		int caveIndex2 = 0;
		for (int i = 0; currentMinute <= 26; ++i) {
			//visits.add(cave);
//			System.out.println("minute " + currentMinute + " flow " + currentFlow);
			currentScore += currentFlow;

			if (distance1 == 0 && caveIndex1 <= path1.size() - 1) {
				currentFlow += currentCave1.getFlowRate();
//				System.out.println("minute " + currentMinute + " You released " + currentCave1.getFlowRate());
				caveIndex1++;
				if (caveIndex1 < path1.size()) {
					nextCave = nodes.get(path1.get(caveIndex1));
					distance1 = currentCave1.getDistanceTo(nextCave.getName());
					currentCave1 = nextCave;
				}
			} else {
				distance1--;
			}
			if (distance2 == 0 && caveIndex2 <= path2.size() - 1) {
				currentFlow += currentCave2.getFlowRate();
//				System.out.println("minute " + currentMinute + " Ele released " + currentCave2.getFlowRate());
				caveIndex2++;
				if (caveIndex2 < path2.size()) {
					nextCave = nodes.get(path2.get(caveIndex2));
					distance2 = currentCave2.getDistanceTo(nextCave.getName());
					currentCave2 = nextCave;
				}
			} else {
				distance2--;
			}
			currentMinute += 1;
		}
		if (currentMinute < 26) {
			currentScore += (currentFlow * (26 - currentMinute));
		}
		if (currentMinute >= 26) {
			//addResult(visits);
			//results.add(0, visited);
		}
		return currentScore;
	}

	private boolean visited(List<String> currentPermutationString) {
		if (results == null) {
			return false;
		}
		TreeNode currentNode = results;
		boolean first = true;
		for (String s : currentPermutationString) {
			if (first) {
				first = false;
				continue;
			}
			if (currentNode == null) {
				return false;
			}
			if (currentNode.getChildren().size() == 0) {
				return true;
			}
			currentNode = currentNode.getChild(s);

		}
		return true;
	}

	private void addResult(List<String> result) {
		if (results == null) {
			String root = result.remove(0);
			results = new TreeNode(root);
			TreeNode currentNode = results;
			for (String s : result) {
				currentNode = currentNode.addChild(s);
			}
			return;
		}
		TreeNode currentNode = results;
		boolean first = true;
		for (String s : result) {
			if (first) {
				first = false;
				continue;
			}
			currentNode = currentNode.addChild(s);
		}
	}

	private void calculateDistances(Map<String, Node> nodes) {

		Map<String, List<Edge>> graph = new HashMap<>();
		for (String node : nodes.keySet()) {
			List<Node> children = nodes.get(node).getChildren();
			List<Edge> edges = new ArrayList<>();
			for (Node child : children) {
				edges.add(new Edge(child.getName(), 1));
			}
			graph.put(node, edges);
		}

		for (String node : nodes.keySet()) {
			Node n = nodes.get(node);
			if (n.getName().equals("AA") || n.getFlowRate() > 0) {
				Map<String, Integer> distances = Dijkstras.dijkstra(graph, n.getName());
				for (String n2Str : distances.keySet()) {
					Node n2 = nodes.get(n2Str);
					if ((n2.getFlowRate() > 0 || n2.getName().equals("AA")) && !n2.getName().equals(n.getName())) {
						n.addDistanceTo(n2.getName(), distances.get(n2.getName()));
					}
				}
				System.out.println(distances);
			}
		}
	}
}
