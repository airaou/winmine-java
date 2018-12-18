package work3_09_04.mine;

public interface Mine {

	public interface Configurer {
		
		/**
		 * 设置游戏的区域大小
		 * @param rows 行数
		 * @param colums 列数
		 * @return 配置器对象自身
		 */
		public Configurer setSize(int rows, int colums);
		
		public int getRows();
		public int getColums();
		
		/**
		 * 设置雷的数量
		 * @param amount
		 * @return 配置器对象自身
		 */
		public Configurer setAmount(int amount);
		
		public int getAmount();
		
		/**
		 * 根据配置器的配置生成一局游戏, 如果配置不合常理如雷数比区域内方块多时会抛出异常
		 * @return 一局新游戏
		 */
		public Mine getGame();
		
	}

	/**
	 * 游戏结束事件
	 */
	public static interface LoseReceiver {
		void onLose(Mine source);
	}
	
	public void setLoseReceiver(LoseReceiver receiver);
	
	/**
	 * 检查是否胜利, 胜利则游戏结束
	 * @return 没有未挖掘的非雷则返回true
	 */
	public boolean isWon();
	
	/**
	 * 获取剩下的雷的数量
	 * @return 剩下的雷的数量
	 */
	public int getRestMine();
	
	/**
	 * 获取已被挖的方格数
	 * @return 被挖了的方格数
	 */
	public int getDugUnitAmount();
	
	/**
	 * 检查游戏是否结束
	 * @return 若已结束则为true, 否则为false
	 */
	public boolean isOver();
	
	/**
	 * 获取某处的格子单元
	 * @param pos 该处坐标
	 * @return 格子单元
	 */
	public Unit get(Position pos);

	
	/**
	 * 获取该坐标处周围的坐标
	 * @param pos 该处坐标
	 * @return 周围的坐标的数组, 顺序可能是乱序
	 */
	public Position[] getAround(Position pos);
	
	/**
	 * 获取列数
	 * @return 列数
	 */
	public int getColums();
	
	/**
	 * 获取行数
	 * @return 行数
	 */
	public int getRows();
	
	/**
	 * 挖此处
	 * @param pos 坐标
	 * @param isDirectClicked 是否为鼠标直接点击
	 */
	public boolean dig(Position pos);
	
	/**
	 * 获取该处周围雷的数量
	 * @param pos 该处
	 * @return 周围雷的数量
	 */
	default public int getMineAround(Position pos) {
		int c = 0;
		for(Position p : getAround(pos)) {
			if(get(p).isMine()) {
				c++;
			}
		}
		return c;
	}
	
	/**
	 * 获取该处周围旗子的数量
	 * @param pos 该处坐标
	 * @return 周围旗子数量
	 */
	default public int getFlagAround(Position pos) {
		int c = 0;
		for(Position p : getAround(pos)) {
			if(get(p).isFlagged()) {
				c++;
			}
		}
		return c;
	}

	/**
	 * 挖掘该处的周围的块
	 * @param pos 该点处的坐标
	 * @return 此处已被挖, 且周围旗子数量等于周围雷数或雷数为0才返回true, 否则返回false
	 */
	default public boolean digAround(Position pos) {
		if(get(pos).isDug()) {
			int c = getMineAround(pos);
			if(getFlagAround(pos) == c || c == 0) {
				for(Position p : getAround(pos)) {
					dig(p);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 显示答案
	 */
	public void showAnswer();
	
	/**
	 * 把某处雷移开, 一般用于第一次点击扫雷面
	 * @param pos 某处坐标
	 */
	public void keepNoMine(Position pos);
}
