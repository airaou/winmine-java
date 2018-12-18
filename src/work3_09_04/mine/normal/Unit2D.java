package work3_09_04.mine.normal;

import work3_09_04.mine.Unit;

public class Unit2D implements Unit {
	
	private boolean mine;
	private boolean dug = false;
	private boolean flagged = false;
	private boolean marked = false;
	private boolean showing = false;
	private BoomReceiver boomReceiver = () -> {};
	private DugReceiver dugReceiver = () -> {};
	private NumberSetter numberSetter = (n) -> {};
	private ShowingReceiver showingReceiver = () -> {};
	@Override
	public void setShowingReceiver(ShowingReceiver receiver) {
		if(receiver != null) {
			showingReceiver = receiver;
		}
	}
	@Override
	public void setDugReceiver(DugReceiver receiver) {
		if(dugReceiver != null) {
			dugReceiver = receiver;
		}
	}
	@Override
	public void setBoomReceiver(BoomReceiver receiver) {
		if(receiver != null)
			boomReceiver = receiver;
	}
	@Override
	public void setNumberSetter(NumberSetter setter) {
		if(setter != null) {
			numberSetter = setter;
		}
	}
	public void setNumber(int n) {
		numberSetter.onNumberSetting(n);
	}
	
	public Unit2D(boolean isMine) {
		mine = isMine;
	}
	
	@Override
	public String toString() {
		return String.format("[mine=%s,dug=%s,flagged=%s,marked=%s]", mine, dug, flagged, marked);
	}

	@Override
	public boolean isMine() {
		return mine;
	}

	@Override
	public boolean isFlagged() {
		return flagged;
	}

	@Override
	public boolean isDug() {
		return dug;
	}

	@Override
	public boolean dig() {
		if(dug == false && flagged == false) {
			dug = true;
			marked = false;
			dugReceiver.onReceiver();
			if(isMine() == true) {
				boomReceiver.onReceiver();
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean flag() {
		if(flagged == false && dug == false) {
			flagged = true;
			marked = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean mark() {
		if(dug == false && marked == false) {
			flagged = false;
			marked = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean unflag() {
		if(flagged == true && dug == false) {
			flagged = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean unmark() {
		if(marked == true && dug == false) {
			marked = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean isMarked() {
		return marked;
	}
	
	private static void test(boolean isMine) {
		System.out.println("测试开始: isMine == " + isMine);
		Unit2D u = new Unit2D(isMine);
		System.out.println("" + u);
		assert(u.isDug() == false);
		assert(u.isFlagged() == false);
		assert(u.isMine() == isMine);
		assert(u.mark() == true);
		assert(u.isMarked() == true);
		assert(u.isFlagged() == false);
		assert(u.isDug() == false);
		assert(u.flag() == true);
		assert(u.isMarked() == false);
		assert(u.isFlagged() == true);
		assert(u.isDug() == false);
		assert(u.mark() == true);
		assert(u.isMarked() == true);
		assert(u.isFlagged() == false);
		assert(u.mark() == false);
		assert(u.flag() == true);
		assert(u.flag() == false);
		assert(u.isFlagged() == true);
		assert(u.isMarked() == false);
		assert(u.dig() == false);
		assert(u.unflag() == true);
		assert(u.isFlagged() == false);
		assert(u.mark() == true);
		assert(u.isMarked() == true);
		assert(u.unmark() == true);
		assert(u.isMarked() == false);
		assert(u.dig() == true);
		assert(u.isDug() == true);
		assert(u.isFlagged() == false);
		assert(u.isMarked() == false);
		assert(u.isMine() == isMine);
		assert(u.dig() == false);
		assert(u.isDug() == true);
		assert(u.mark() == false);
		assert(u.flag() == false);
		System.out.println("" + u);
	}
	
	public static void main(String[] args) {
		try {
			test(true);
			test(false);
		} catch(AssertionError e) {
			System.out.println("测试出现异常");
			e.printStackTrace();
			return;
		}
		try {
			assert(false);
		} catch(AssertionError e) {
			System.out.println("测试完毕");
			return;
		}
		System.out.println("assert未开启");
	}

	@Override
	public void showAnswer() {
		showing = true;
		showingReceiver.onReceiver();
	}

	@Override
	public boolean isShowing() {
		return showing;
	}
	@Override
	public boolean cancelMine() {
		if(isDug() == false && isMine() == true) {
			mine = false;
			return true;
		}
		return false;
	}
	@Override
	public boolean setMine() {
		if(isDug() == false && isMine() == false) {
			mine = true;
			return true;
		}
		return false;
	}
}
