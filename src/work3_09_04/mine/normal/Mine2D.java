package work3_09_04.mine.normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import work3_09_04.mine.Mine;
import work3_09_04.mine.Position;
import work3_09_04.mine.Unit;

public class Mine2D implements Mine {
	
	private final int rows;
	private final int colums;
	private boolean over = false;
	private boolean newGame = true;
	private final Map<Position, Unit2D> units;
	
	public static class Configurer implements Mine.Configurer {
		private int rows;
		private int colums;
		private int amount;
		
		public Configurer() {
			
		}

		@Override
		public Mine.Configurer setSize(int rows, int colums) {
			this.rows = rows;
			this.colums = colums;
			return this;
		}
		
		@Override
		public Mine.Configurer setAmount(int amount) {
			this.amount = amount;
			return this;
		}

		@Override
		public Mine2D getGame() {
			if(rows > 0 && colums > 0 && amount >= 0 && rows * colums >= amount) {
				Mine2D m = new Mine2D(rows, colums, amount);
				return m;
			}
			return null;
		}

		@Override
		public int getRows() {
			return rows;
		}

		@Override
		public int getColums() {
			return colums;
		}

		@Override
		public int getAmount() {
			return amount;
		}
		
	}

	
	private Mine2D(int rows, int colums, int amount) {
		this.rows = rows;
		this.colums = colums;
		units = new HashMap<>();
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < colums; c++) {
				units.put(Position2D.getInstance(r, c), null);
			}
		}
		setMine(amount);
		fillEarth();
	}
	
	/**
	 * 设置雷, 假定units长宽相等, 假定空位足够
	 * @param amount 添置的雷的数量
	 */
	private void setMine(int amount) {
		Random r = new Random();
		for(int i = 0; i < amount; i++) {
			int row;
			int col;
			do {
				 row = r.nextInt(rows);
				 col = r.nextInt(colums);
			} while(units.get(Position2D.getInstance(row, col)) != null);
			Unit2D u = new Unit2D(true);
			u.setBoomReceiver(() -> {
				over();
				loseReceiver.onLose(this);
			});
			units.put(Position2D.getInstance(row, col), u);
		}
	}
	
	/**
	 * 将units中null都用无雷地填满
	 */
	private void fillEarth() {
		for(Entry<Position, Unit2D> r : units.entrySet()) {
			if(r.getValue() == null) {
				units.put(r.getKey(), new Unit2D(false));
			}
		}
	}
	
	protected boolean over() {
		System.out.println("over");
		if(over == true) {
			return false;
		}
		over = true;
		return true;
	}

	@Override
	public boolean isOver() {
		return over;
	}

	/**
	 * @param pos Position2D 对象
	 */
	@Override
	public Unit get(Position pos) {
		int[] p = ((Position2D)pos).toArray();
		if(p[0] < 0 || p[1] < 0 || p[0] >= rows || p[1] >= colums) {
			return null;
		} else {
			return units.get(Position2D.getInstance(p));
		}
	}

	@Override
	public Position[] getAround(Position pos) {
		ArrayList<Position2D> l = ((Position2D)pos).getAround(rows, colums);
		Position[] positions = new Position[l.size()];
		l.toArray(positions);
		return positions;
	}
	
	@Override
	public boolean dig(Position pos) {
		boolean is_succeed;
		Unit u = get(pos);
		if(newGame == true && u.isMine()) {
			keepNoMine(pos);
			System.out.println("keepoutmine");
		}
		newGame = false;
		int num = getMineAround(pos);
		//System.out.println(String.format("Mine2D::dig dig(%s, %s), %d, %s", u.isDug(), u.isMine(), num, pos));
		if(!u.isDug() && !u.isMine() && num == 0) {
			is_succeed = u.dig();
			//System.out.println("Mine2D::dig to dig around");
			digAround(pos);
		} else {
			is_succeed = u.dig();
			if(is_succeed && u.isMine()) {
				over();
				showAnswer();
			}
			u.setNumber(num);
		}
		return is_succeed;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Entry<Position, Unit2D> u : units.entrySet()) {
			builder.append(u.getKey() + ": " + u.getValue() + "\n");
		}
		return builder.toString();
	}

	@Override
	public int getColums() {
		return colums;
	}

	@Override
	public int getRows() {
		return rows;
	}
	
	private static void test() {
		Mine2D.Configurer conf = new Mine2D.Configurer();
		conf.setSize(3, 3).setAmount(3);
		Mine2D g = conf.getGame();
		assert(g != null);
		assert(g.isOver() == false);
		System.out.println(g);
		Unit2D u;
		u = (Unit2D)g.get(Position2D.getInstance(1, 2));
		if(u.isMine()) {
			u.dig();
			assert(g.isOver() == true);
		} else {
			u.dig();
			assert(g.isOver() == false);
		}
		System.out.println(g);
	}
	public static void main(String[] args) {
		test();
		try {
			assert(false);
		} catch(AssertionError e) {
			System.out.println("测试完毕");
			return;
		}
		System.out.println("assert未开启");
	}

	private LoseReceiver loseReceiver = (s) -> {};
	
	@Override
	public void setLoseReceiver(LoseReceiver receiver) {
		if(receiver != null) {
			loseReceiver = receiver;
		}
	}
	
	@Override
	public boolean isWon() {
		for(Entry<Position, Unit2D> e : units.entrySet()) {
			Unit2D u = e.getValue();
			if(!u.isDug() && !u.isMine()) {
				return false;
			}
		}
		for(Entry<Position, Unit2D> e : units.entrySet()) {
			Unit2D u = e.getValue();
			if(u.isMine()) {
				u.flag();
			}
		}
		over();
		return true;
	}
	
	@Override
	public void showAnswer() {
		for(Entry<Position, Unit2D> e : units.entrySet()) {
			e.getValue().showAnswer();
		}
	}

	@Override
	public int getRestMine() {
		int sum = 0;
		for(Entry<Position, Unit2D> e : units.entrySet()) {
			Unit2D u = e.getValue();
			if(u.isMine()) {
				sum += 1;
			}
			if(u.isFlagged()) {
				sum -= 1;
			}
		}
		return sum;
	}

	@Override
	public void keepNoMine(Position pos) {
		Unit2D u = (Unit2D)get(pos);
		if(u.cancelMine()) {
			Position2D ranpos;
			do {
				ranpos = (Position2D)Position2D.getRandomPosition(rows, colums);
			} while(ranpos.equals(pos) == false && get(ranpos).setMine() == true);
		}
	}

	@Override
	public int getDugUnitAmount() {
		int sum = 0;
		for(Entry<Position, Unit2D> e : units.entrySet()) {
			if(e.getValue().isDug()) {
				sum += 1;
			}
		}
		return sum;
	}
}
