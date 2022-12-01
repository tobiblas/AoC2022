package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

	private String inFile;

	public InputReader(String inFile) {
		this.inFile = inFile;
	}

	public List<Integer> getIntList() {
		List<Integer> intList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
			String line = reader.readLine();
			while (line != null) {
				intList.add(Integer.parseInt(line));
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return intList;
	}

	public List<Long> getLongList() {
		List<Long> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
			String line = reader.readLine();
			while (line != null) {
				list.add(Long.parseLong(line));
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getStringList(boolean incluredSpaces) {
		List<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
			String line = reader.readLine();
			while (line != null) {
				if (line.equals("") && !incluredSpaces) {
					line = reader.readLine();
					continue;
				}
				list.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
