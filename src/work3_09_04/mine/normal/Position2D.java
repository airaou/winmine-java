package work3_09_04.mine.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import work3_09_04.mine.Position;

public class Position2D implements Position {
	
	final int[] position2D;
	
	private Position2D(int... ds) {
		position2D = ds;
	}

	static public Position2D getInstance(int... ds) {
		if(ds.length != 2) return null;
		Position2D p = new Position2D(ds);
		return p;
	}

	public int[] toArray() {
		return position2D;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(position2D);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Position2D other = (Position2D)o;
		return Arrays.equals(other.position2D, position2D);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(position2D);
	}
	
	public ArrayList<Position2D> getAround(int wid, int hei) {
		int[][] around = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}, };
		ArrayList<Position2D> res = new ArrayList<>();
		for(int[] p : around) {
			int x = p[0] + position2D[0];
			int y = p[1] + position2D[1];
			if(x >= 0 && y >= 0 && x < wid && y < hei) {
				res.add(Position2D.getInstance(x, y));
			}
		}
		return res;
	}
	
	public static void test_around() {
		String mid_str;
		Position2D p;
		ArrayList<Position2D> around;
		
		p = new Position2D(1, 0);
		around = p.getAround(10, 10);
		mid_str = around.toString();
		assert(mid_str.equals("[[0, 0], [0, 1], [1, 1], [2, 0], [2, 1]]"));
		System.out.println(mid_str);
		
		p = new Position2D(1, 2);
		around = p.getAround(10, 10);
		mid_str = around.toString();
		assert(mid_str.equals("[[0, 1], [0, 2], [0, 3], [1, 1], [1, 3], [2, 1], [2, 2], [2, 3]]"));
		System.out.println(mid_str);

		p = new Position2D(2, 3);
		around = p.getAround(3, 4);
		mid_str = around.toString();
		assert(mid_str.equals("[[1, 2], [1, 3], [2, 2]]"));
		System.out.println(mid_str);

		p = new Position2D(4, 0);
		around = p.getAround(5, 1);
		mid_str = around.toString();
		assert(mid_str.equals("[[3, 0]]"));
		System.out.println(mid_str);
	}
	public static void test_single() {
		Position2D p = new Position2D(1, 2);
		System.out.println("hashCode(" + p + ") == " + p.hashCode());
	}
	public static void test_two() {
		Position2D p = new Position2D(1, 2);
		Position2D q = new Position2D(1, 2);
		Position2D r = new Position2D(2, 3);
		assert(q.equals(p) == true);
		assert(q.equals(r) == false);
		assert(q.equals(null) == false);
		assert(p.equals(q) == true);
		assert(r.equals(q) == false);
	}
	public static void test_dictionary() {
		int[][] test_datas = {{0, 3, 86},
				{5, 2, 94},
				{2, 1, 98},
				{2, 1, 49},
				{3, 4, 36},
				{3, 3, 57},
				{4, 4, 98},
				{3, 4, 64},
				{4, 1, 44},
				{3, 4, 40},
		};
		Map<Position2D, Integer> m = new HashMap<>();
		for(int[] data : test_datas) {
			m.put(Position2D.getInstance(data[0], data[1]), data[2]);
		}
		for(Entry<Position2D, Integer> i : m.entrySet()) {
			System.out.println(i.getKey() + " = " + i.getValue() + ", ");
		}
	}	
	public static void main(String[] args) {
		test_single();
		try {
			test_two();
		} catch(AssertionError e) {
			System.out.println("≤‚ ‘≥ˆ¥Ì");
			e.printStackTrace();
		}
		test_dictionary();
		test_around();
		try {
			assert(false);
		} catch(AssertionError e) {
			System.out.println("≤‚ ‘ÕÍ±œ");
			return;
		}
		System.out.println("assertŒ¥ø™∆Ù");
	}

	public static Position getRandomPosition(int rows, int colums) {
		Random ran = new Random();
		return Position2D.getInstance(ran.nextInt(rows), ran.nextInt(colums));
	}
}
