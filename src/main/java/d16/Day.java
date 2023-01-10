package d16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

		int bestOfAll = 0;

		for (String caveRoot : cavesList) {
			Set<List<String>> queue = new HashSet<>();
			Set<List<String>> nextQueue = new HashSet<>();

			List<String> l = new ArrayList<>();
			l.add(caveRoot);

			queue.add(l);
			int bestScore = 0;
			int i = 1;
			while (true) {
				while (!queue.isEmpty()) {
					List<String> nodeList = queue.iterator().next();
					queue.remove(nodeList);
					for (String cave : cavesList) {
						if (!nodeList.contains(cave)) {
							List<String> s = new ArrayList<>();
							s.addAll(nodeList);
							s.add(cave);
							if (visited(s)) {
								continue;
							}
							nextQueue.add(s);
						}
					}
					int score = test(nodeList, nodes);
					if (score > bestScore) {
						System.out.println("New best score " + score + " list: " + nodeList);
						bestScore = score;
						if (score > bestOfAll) {
							bestOfAll = score;
						}
					}
				}
				System.out.println("Depth = " + i);
				System.out.println("Size = " + nextQueue.size());
				i++;
				queue = nextQueue;
				nextQueue = new HashSet<>();
				if (queue.size() == 0) {
					break;
				}
			}
		}
		System.out.println("BEST = " + bestOfAll);
	}

	private int test(List<String> permutation, Map<String, Node> nodes) {
		List<String> visits = new ArrayList<>();
		//calculate score
		int currentMinute = 1;
		String currentCave = "AA";
		int currentFlow = 0;
		int currentScore = 0;
		boolean done = false;
		for (int i = 0; i < permutation.size(); ++i) {
			String cave = permutation.get(i);
			visits.add(cave);
			int distance = nodes.get(currentCave).getDistanceTo(cave);
			for (int j = 0; j < distance; ++j) {
				currentScore += currentFlow;
				currentMinute += 1;
				if (currentMinute >= 30) {
					done = true;
					break;
				}
			}
			if (done) {
				break;
			}
			int flowReleased = nodes.get(cave).getFlowRate();
			currentFlow += flowReleased;
			currentScore += currentFlow;
			currentMinute++;
			currentCave = cave;
			if (currentMinute >= 30) {
				done = true;
				break;
			}
		}
		if (currentMinute < 30) {
			currentScore += (currentFlow * (30 - currentMinute));
		}
		if (currentMinute >= 30) {
			addResult(visits);
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
