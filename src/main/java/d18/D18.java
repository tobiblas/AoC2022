package d18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import util.Task;

public class D18 extends Task {

	class Cube {

		int x;
		int y;
		int z;

		public Cube(String str) {
			String[] coords = str.split(",");
			this.x = Integer.parseInt(coords[0]);
			this.y = Integer.parseInt(coords[1]);
			this.z = Integer.parseInt(coords[2]);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Objects.hash(x, y, z);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			Cube other = (Cube) obj;
			return x == other.x && y == other.y && z == other.z;
		}

		@Override
		public String toString() {
			return "Cube [x=" + x + ", y=" + y + ", z=" + z + "]";
		}
	}

	public static void main(String[] args) {
		D18 day = new D18();
		List<String> list = day.getStrings();
		String all = day.getAll();
		//day.p1(list, all);
		day.p2(list);
	}

	public List<Cube> trappedAir = new ArrayList<>();

	private void p1(List<String> list, String all) {

		//surface area of rock 3374
		// surface area of trapped air?

		List<Cube> cubes = new ArrayList<>();
		for (String str : list) {
			cubes.add(new Cube(str));
		}
		int edge = 20;
		List<Cube> airs = new ArrayList<>();
		for (int x = 0; x < edge; ++x) {
			for (int y = 0; y < edge; ++y) {
				for (int z = 0; z < edge; ++z) {
					String pos = x + "," + y + "," + z;
					boolean found = false;
					for (String str : list) {
						if (str.equals(pos)) {
							found = true;
							break;
						}
					}
					if (!found) {
						airs.add(new Cube(x + "," + y + "," + z));
					}
				}
			}
		}
		//generate air droplets that are 0.0.0 - 20.20.20
		//find iardroplets that are trapped inside structre..
		//for each air drop. Check if trapped by finding path among airdroplets until reaching edge.

		for (int i = 0; i < airs.size(); ++i) {
			Cube air = airs.get(i);
			if (air.x == 0 || air.y == 0 || air.z == 0 ||
					air.x == edge || air.y == edge || air.z == edge) {
				continue;
			}
			//System.out.println("Checking: " + air.toString() + " " + i + "/" + airs.size());
			if (i % 500 == 0) {
				System.out.println(i + "/" + airs.size());
			}
			Status status = findWayOut(edge, airs, new ArrayList<>(), i, air);
			if (status == Status.TRAPPED) {
				System.out.println("Trapped: " + air.toString());
				trappedAir.add(air);
			}
			if (status == Status.NO_INTERSECTION) {
				System.out.println("No intersection: " + air.toString());
				trappedAir.add(air);
			}
		}

		System.out.println("TRAPPED:");
		for (Cube cube : trappedAir) {
			System.out.println(cube.toString());
		}

//		int sides = 6 * cubes.size();
//		for (int count = 0; count < cubes.size(); ++count) {
//			Cube cube = cubes.get(count);
//			for (int i = 0; i < cubes.size(); ++i) {
//				Cube cubeToCompareWith = cubes.get(i);
//				if (i == count) {
//					continue;
//				}
//				int connectingSides = getIntersection(cube, cubeToCompareWith);
//				sides -= connectingSides;
//			}
//		}
		//System.out.println(sides);
	}

	enum Status {
		NO_INTERSECTION,
		TRAPPED,
		WAY_OUT_FOUND
	}

	private Status findWayOut(int edge, List<Cube> airs, List<Cube> connectedAirs, int i, Cube air) {
		int newAdded = 0;
		for (int j = 0; j != airs.size(); ++j) {
			if (j == i) {
				continue;
			}
			//List<Cube> connectedAirs2 = new ArrayList<>();
			Cube air2 = airs.get(j);
			if (getIntersection(air, air2) > 0) {
				if (air2.x == 0 || air2.y == 0 || air2.z == 0 ||
						air2.x == edge || air2.y == edge || air2.z == edge) {
					//way out found
					//System.out.println("Way out found for " + air.toString());
					return Status.WAY_OUT_FOUND;
				}
				if (trappedAir.contains(air2)) {
					return Status.TRAPPED;
				}
				if (!connectedAirs.contains(air2)) {
					connectedAirs.add(air2);
					newAdded++;
				}
			}
		}
		if (newAdded > 0 && connectedAirs.size() > 0) {
			for (int ii = 0; ii < connectedAirs.size(); ++ii) {
				Cube connectedAir = connectedAirs.get(ii);
				Status wayOut = findWayOut(edge, airs, connectedAirs, i, connectedAir);
				if (wayOut == Status.WAY_OUT_FOUND) {
					return Status.WAY_OUT_FOUND;
				}
				if (wayOut == Status.TRAPPED) {
					return Status.TRAPPED;
				}
			}
		}
		return Status.NO_INTERSECTION;
	}

	private int getIntersection(Cube c1, Cube c2) {
		int intersections = 0;
		if (c1.x == c2.x && c1.y == c2.y && Math.abs(c1.z - c2.z) == 1) {
			intersections++;
		}
		if (c1.x == c2.x && c1.z == c2.z && Math.abs(c1.y - c2.y) == 1) {
			intersections++;
		}
		if (c1.z == c2.z && c1.y == c2.y && Math.abs(c1.x - c2.x) == 1) {
			intersections++;
		}
		return intersections;
	}

	private void p2(List<String> list) {
		//surface area of rock 3374
		// surface area of trapped air?
		//intersect air with cubes

		List<Cube> cubes = new ArrayList<>();
		for (String str : list) {
			cubes.add(new Cube(str));
		}

		List<Cube> trappedAir = new ArrayList<>();
		for (String str : getStringList()) {
			trappedAir.add(new Cube(str));
		}

		int intersections = 0;
		for (Cube air : trappedAir) {
			for (Cube cube : cubes) {
				int inter = getIntersection(air, cube);
				intersections += inter;
			}
		}
		System.out.println(intersections);
		System.out.println(3374 - intersections);
	}

	public List<String> getStringList() {
		String inFile = "/Users/tobias/AoC2022/src/main/java/template/trapped";
		List<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
			String line = reader.readLine();
			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
