package d24;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private Point value;
	private List<TreeNode> children;

	private TreeNode(Point value) {
		this.value = value;
		this.children = new ArrayList<>();
	}

	public static TreeNode of(Point value) {
		return new TreeNode(value);
	}

	public Point getValue() {
		return value;
	}

	public void addChild(TreeNode of) {
		TreeNode newChild = new TreeNode(value);
		children.add(newChild);
	}
}
