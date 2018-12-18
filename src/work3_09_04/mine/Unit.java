package work3_09_04.mine;

public interface Unit {

	/**
	 * 被挖掘时触发
	 */
	public static interface DugReceiver {
		void onReceiver();
	}
	
	public void setDugReceiver(DugReceiver receiver);

	/**
	 * 爆炸时触发
	 */
	public static interface BoomReceiver {
		public void onReceiver();
	}
	
	public void setBoomReceiver(BoomReceiver receiver);
	
	/**
	 * 设置数字时触发
	 */
	public static interface NumberSetter {
		public void onNumberSetting(int n);
	}
	
	public void setNumberSetter(NumberSetter setter);
	
	/**
	 * 显示答案时触发
	 */
	public static interface ShowingReceiver {
		public void onReceiver();
	}
	
	public void setShowingReceiver(ShowingReceiver receiver);
	
	/**
	 * 设置数字
	 * @param n 表示雷数目的数字
	 */
	public void setNumber(int n);
	
	/**
	 * 检查是不是雷
	 * @return 是雷返回true, 则返回false
	 */
	public boolean isMine();
	
	/**
	 * 取消掉该处的雷, 一般用于第一次点击游戏区使用
	 * @return 此处有雷且没被挖且取消成功则返回true
	 */
	public boolean cancelMine();

	/**
	 * 在此处设置雷, 一般在第一次就点击到雷时使用
	 * @return 没被挖没有雷则设置成功
	 */
	public boolean setMine();
	
	/**
	 * 检查是否有插旗
	 * @return 是否有插旗
	 */
	public boolean isFlagged();

	/**
	 * 检查有没有被挖了
	 * @return 是否被挖
	 */
	public boolean isDug();
	
	/**
	 * 是否做了标记
	 * @return 做了标记返回true
	 */
	public boolean isMarked();
	
	/**
	 * 挖该处
	 * @param isDirectClicked 是否为直接点击
	 * @return 成功返回true
	 */
	public boolean dig();
	
	/**
	 * 给该处插旗
	 * @return 若未挖且未插旗则返回true
	 */
	public boolean flag();
	
	/**
	 * 做标记
	 * @return 成功返回true
	 */
	public boolean mark();
	
	/**
	 * 拔旗
	 * @return 成功返回true
	 */
	public boolean unflag();
	
	/**
	 * 取消标记
	 * @return 成功返回true
	 */
	public boolean unmark();
	
	/**
	 * 显示答案
	 */
	public void showAnswer();
	
	/**
	 * 正在显示答案
	 */
	public boolean isShowing();
}
