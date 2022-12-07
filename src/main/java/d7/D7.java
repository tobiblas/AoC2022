package d7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import util.Task;

public class D7 extends Task {

	class File {

		public File(long size, String name, boolean dir, File parent) {
			super();
			this.dir = dir;
			this.size = size;
			this.name = name;
			this.parent = parent;
		}

		File parent;
		boolean dir;
		long size = 0;
		String name = "";
		List<File> files = new ArrayList<>();
	}

	public static void main(String[] args) {
		D7 day = new D7();
		List<String> list = day.getStrings();
		day.p1AndP2(list);
	}

	private File getFileStructure(List<String> list) {
		File root = new File(0, "/", true, null);
		File currentDir = root;
		int count = 0;
		boolean checkFiles = false;
		for (String str : list) {
			count++;
			if (str.equals("$ cd ..")) {
				//if (currentDir.parent != null) {

				currentDir = currentDir.parent;
				//}
				continue;
			}
			if (str.equals("$ cd /")) {
				currentDir = root;
				continue;
			}
			if (str.startsWith("$ cd")) {
				String dirStr = str.split(" ")[2];
				for (File dir : currentDir.files) {
					if (dir.dir && dir.name.equals(dirStr)) {
						currentDir = dir;
						break;
					}
				}
				continue;
			}
			if (str.startsWith("dir")) {
				String dirStr = str.split(" ")[1];
				File dir = new File(0, dirStr, true, currentDir);
				currentDir.files.add(dir);
				continue;
			}
			if (str.startsWith("$ ls")) {
				checkFiles = true;
				continue;
			}
			if (str.startsWith("$")) {
				//command
			} else {
				String[] file = str.split(" ");
				File f = new File(Long.parseLong(file[0]), file[1], false, currentDir);
				currentDir.files.add(f);
				continue;
			}
		}
		return root;
	}

	private void p1AndP2(List<String> list) {
		File root = getFileStructure(list);
		long curr = print("", root, 0);
		System.out.println("");
		List<File> files = new ArrayList<>();
		find(files, root, 25367140L);
		long sum = 0;
		Collections.sort(files, new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				return lhs.size > rhs.size ? -1 : 1;
			}
		});
		//disk 70000000
		//total = 42536714 - from problem 1
		//need 30000000
		// need - (disk - total) = 25367140
		for (File file : files) {
			if (file.dir && file.size <= 25367140L) {
				System.out.println("dir " + file.name + " " + file.size);
			}
		}
		//Manually find closest to 25367140L
		System.out.println(sum);
	}

	private void find(List<File> files, File file, long threshold) {
		if (file.dir) {
			if (file.size <= threshold) {
				files.add(file);
				System.out.println("Dir found " + file.name + " size=" + file.size);
			}
			for (File f : file.files) {
				find(files, f, threshold);
			}
		}
	}

	private long print(String inline, File file, long currSize) {
		if (file.dir) {
			System.out.println(inline + file.name + (file.dir ? "(dir)" : ""));
			inline += "  ";
			for (File f : file.files) {
				currSize += print(inline, f, 0);
			}
			file.size = currSize;

		} else {
			System.out.println(inline + "- " + file.name + " (file," + file.name + ", size=" + file.size);
			currSize += file.size;
		}
		return currSize;
	}

	private void p2(List<String> list) {
	}

}
