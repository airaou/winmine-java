package work3_09_04.mine;

public interface Mine {

	public interface Configurer {
		
		/**
		 * ������Ϸ�������С
		 * @param rows ����
		 * @param colums ����
		 * @return ��������������
		 */
		public Configurer setSize(int rows, int colums);
		
		public int getRows();
		public int getColums();
		
		/**
		 * �����׵�����
		 * @param amount
		 * @return ��������������
		 */
		public Configurer setAmount(int amount);
		
		public int getAmount();
		
		/**
		 * ��������������������һ����Ϸ, ������ò��ϳ����������������ڷ����ʱ���׳��쳣
		 * @return һ������Ϸ
		 */
		public Mine getGame();
		
	}

	/**
	 * ��Ϸ�����¼�
	 */
	public static interface LoseReceiver {
		void onLose(Mine source);
	}
	
	public void setLoseReceiver(LoseReceiver receiver);
	
	/**
	 * ����Ƿ�ʤ��, ʤ������Ϸ����
	 * @return û��δ�ھ�ķ����򷵻�true
	 */
	public boolean isWon();
	
	/**
	 * ��ȡʣ�µ��׵�����
	 * @return ʣ�µ��׵�����
	 */
	public int getRestMine();
	
	/**
	 * ��ȡ�ѱ��ڵķ�����
	 * @return �����˵ķ�����
	 */
	public int getDugUnitAmount();
	
	/**
	 * �����Ϸ�Ƿ����
	 * @return ���ѽ�����Ϊtrue, ����Ϊfalse
	 */
	public boolean isOver();
	
	/**
	 * ��ȡĳ���ĸ��ӵ�Ԫ
	 * @param pos �ô�����
	 * @return ���ӵ�Ԫ
	 */
	public Unit get(Position pos);

	
	/**
	 * ��ȡ�����괦��Χ������
	 * @param pos �ô�����
	 * @return ��Χ�����������, ˳�����������
	 */
	public Position[] getAround(Position pos);
	
	/**
	 * ��ȡ����
	 * @return ����
	 */
	public int getColums();
	
	/**
	 * ��ȡ����
	 * @return ����
	 */
	public int getRows();
	
	/**
	 * �ڴ˴�
	 * @param pos ����
	 * @param isDirectClicked �Ƿ�Ϊ���ֱ�ӵ��
	 */
	public boolean dig(Position pos);
	
	/**
	 * ��ȡ�ô���Χ�׵�����
	 * @param pos �ô�
	 * @return ��Χ�׵�����
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
	 * ��ȡ�ô���Χ���ӵ�����
	 * @param pos �ô�����
	 * @return ��Χ��������
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
	 * �ھ�ô�����Χ�Ŀ�
	 * @param pos �õ㴦������
	 * @return �˴��ѱ���, ����Χ��������������Χ����������Ϊ0�ŷ���true, ���򷵻�false
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
	 * ��ʾ��
	 */
	public void showAnswer();
	
	/**
	 * ��ĳ�����ƿ�, һ�����ڵ�һ�ε��ɨ����
	 * @param pos ĳ������
	 */
	public void keepNoMine(Position pos);
}
