package util;

import java.util.List;

public class Task {

	private String inputFile = "/Users/tobias/AoC2022/src/main/java/";
	private InputReader inputReader;

	public Task() {
		String clazz = this.getClass().getName();
		String packageName = clazz.substring(0, clazz.indexOf("."));
		String inFile = inputFile + packageName + "/input";
		inputReader = new InputReader(inFile);
	}

	public List<String> getStrings() {
		return inputReader.getStringList(true);
	}

	public List<String> getStringsNoEmptyRows() {
		return inputReader.getStringList(false);
	}

	public List<Integer> getInts() {
		return inputReader.getIntList();
	}

	public List<Long> getLongs() {
		return inputReader.getLongList();
	}
}
