package d16;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private String name;

	public String getName() {
		return name;
	}

	private List<TreeNode> children;

	public List<TreeNode> getChildren() {
		return children;
	}

	public TreeNode(String name) {
		this.name = name;
		this.children = new ArrayList<>();
	}

	public void addChild(TreeNode of) {
		children.add(of);
	}

	public TreeNode addChild(String s) {
		for (TreeNode child : children) {
			if (child.name.equals(s)) {
				return child;
			}
		}
		TreeNode newNode = new TreeNode(s);
		children.add(newNode);
		return newNode;
	}

	public TreeNode getChild(String s) {
		for (TreeNode child : children) {
			if (child.name.equals(s)) {
				return child;
			}
		}
		return null;
	}
}
