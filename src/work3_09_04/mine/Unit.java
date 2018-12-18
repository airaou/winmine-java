package work3_09_04.mine;

public interface Unit {

	/**
	 * ���ھ�ʱ����
	 */
	public static interface DugReceiver {
		void onReceiver();
	}
	
	public void setDugReceiver(DugReceiver receiver);

	/**
	 * ��ըʱ����
	 */
	public static interface BoomReceiver {
		public void onReceiver();
	}
	
	public void setBoomReceiver(BoomReceiver receiver);
	
	/**
	 * ��������ʱ����
	 */
	public static interface NumberSetter {
		public void onNumberSetting(int n);
	}
	
	public void setNumberSetter(NumberSetter setter);
	
	/**
	 * ��ʾ��ʱ����
	 */
	public static interface ShowingReceiver {
		public void onReceiver();
	}
	
	public void setShowingReceiver(ShowingReceiver receiver);
	
	/**
	 * ��������
	 * @param n ��ʾ����Ŀ������
	 */
	public void setNumber(int n);
	
	/**
	 * ����ǲ�����
	 * @return ���׷���true, �򷵻�false
	 */
	public boolean isMine();
	
	/**
	 * ȡ�����ô�����, һ�����ڵ�һ�ε����Ϸ��ʹ��
	 * @return �˴�������û������ȡ���ɹ��򷵻�true
	 */
	public boolean cancelMine();

	/**
	 * �ڴ˴�������, һ���ڵ�һ�ξ͵������ʱʹ��
	 * @return û����û���������óɹ�
	 */
	public boolean setMine();
	
	/**
	 * ����Ƿ��в���
	 * @return �Ƿ��в���
	 */
	public boolean isFlagged();

	/**
	 * �����û�б�����
	 * @return �Ƿ���
	 */
	public boolean isDug();
	
	/**
	 * �Ƿ����˱��
	 * @return ���˱�Ƿ���true
	 */
	public boolean isMarked();
	
	/**
	 * �ڸô�
	 * @param isDirectClicked �Ƿ�Ϊֱ�ӵ��
	 * @return �ɹ�����true
	 */
	public boolean dig();
	
	/**
	 * ���ô�����
	 * @return ��δ����δ�����򷵻�true
	 */
	public boolean flag();
	
	/**
	 * �����
	 * @return �ɹ�����true
	 */
	public boolean mark();
	
	/**
	 * ����
	 * @return �ɹ�����true
	 */
	public boolean unflag();
	
	/**
	 * ȡ�����
	 * @return �ɹ�����true
	 */
	public boolean unmark();
	
	/**
	 * ��ʾ��
	 */
	public void showAnswer();
	
	/**
	 * ������ʾ��
	 */
	public boolean isShowing();
}
