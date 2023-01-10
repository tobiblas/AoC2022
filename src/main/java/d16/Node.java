package d16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

	private boolean open = false;

	private int flowRate;
	private String name;
	private List<Node> children;
	private Map<String, Integer> distances = new HashMap<>();

	public Node(int flowRate, String name) {
		this.flowRate = flowRate;
		this.name = name;
		this.children = new ArrayList<>();
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isEndPoint() {
		return children.size() == 1;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getFlowRate() {
		return flowRate;
	}

	public String getName() {
		return name;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void addDistanceTo(String cave, int distance) {
		distances.put(cave, distance);
	}

	public int getDistanceTo(String cave) {
		Integer distance = distances.get(cave);
		if (distance == null) {
			return -1;
		}
		return distance;
	}
}
