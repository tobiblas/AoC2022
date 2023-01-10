package d16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstras {

	private static final int INFINITY = Integer.MAX_VALUE;

	public static Map<String, Integer> dijkstra(Map<String, List<Edge>> graph, String start) {
		// distances from the start node to all other nodes
		Map<String, Integer> distances = new HashMap<>();

		// priority queue for keeping track of the next node to visit
		PriorityQueue<Node> queue = new PriorityQueue<>();

		// initialize distances and add the start node to the queue
		for (String node : graph.keySet()) {
			distances.put(node, INFINITY);
			queue.add(new Node(node, INFINITY));
		}
		distances.put(start, 0);
		queue.add(new Node(start, 0));

		// loop until the queue is empty
		while (!queue.isEmpty()) {
			// get the next node with the smallest distance
			Node next = queue.poll();

			// update the distances of the neighbors
			for (Edge edge : graph.get(next.name)) {
				int newDistance = distances.get(next.name) + edge.distance;
				if (newDistance < distances.get(edge.to)) {
					distances.put(edge.to, newDistance);
					queue.add(new Node(edge.to, newDistance));
				}
			}
		}

		return distances;
	}

	public static class Node implements Comparable<Node> {

		String name;
		int distance;

		Node(String name, int distance) {
			this.name = name;
			this.distance = distance;
		}

		@Override
		public int compareTo(Node other) {
			return Integer.compare(distance, other.distance);
		}
	}

	public static class Edge {

		String to;
		int distance;

		Edge(String to, int distance) {
			this.to = to;
			this.distance = distance;
		}
	}

	public static void main(String[] args) {
		Map<String, List<Edge>> graph = new HashMap<>();
		graph.put("A", List.of(new Edge("B", 10), new Edge("C", 3)));
		graph.put("B", List.of(new Edge("C", 1), new Edge("D", 2)));
		graph.put("C", List.of(new Edge("B", 4), new Edge("D", 8), new Edge("E", 2)));
		graph.put("D", List.of(new Edge("E", 7)));
		graph.put("E", List.of());

		Map<String, Integer> distances = dijkstra(graph, "A");
		System.out.println(distances); // {A=0, B=8, C=3, D=4, E=5}
	}
}
