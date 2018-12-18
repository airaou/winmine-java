package work3_09_04;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import work3_09_04.mine.Unit;
import work3_09_04.mine.normal.Unit2D;

public class ButtonField extends JButton {
	
	private static final long serialVersionUID = -5670997588185013328L;
	private static final ImageIcon icon_normal;
	private static final ImageIcon icon_pressed;
	
	private static final ImageIcon[] icon_numbers;
	private static final ImageIcon icon_mine;
	private static final ImageIcon icon_wrong;
	private static final ImageIcon icon_flag;
	private static final ImageIcon icon_mark;
	
	static {
		icon_normal = new ImageIcon("res/b1.png");
		icon_pressed = new ImageIcon("res/b1d.png");
		icon_numbers = new ImageIcon[9];
		for(int i = 1; i < 9; i++) {
			icon_numbers[i - 1] = new ImageIcon(String.format("res/m%d.png", i));
		}
		icon_mine = new ImageIcon("res/mm.png");
		icon_wrong = new ImageIcon("res/mw.png");
		icon_flag = new ImageIcon("res/uf.png");
		icon_mark = new ImageIcon("res/uq.png");
	}
	
	private final Unit impl;
	private boolean is_pressed;
	private int around = 0;
	private boolean is_wrong = false;;
	
	public ButtonField(Unit mine_core) {
		assert(mine_core != null);
		impl = mine_core;
		impl.setDugReceiver(() -> {
			if(impl.isMine()) {
				setIsWrong();
			}
			setPressedStateForcely(true);
		});
		impl.setNumberSetter((n) -> {
			//System.out.println("setAound: " + n);
			setAround(n);
		});
		impl.setShowingReceiver(() -> {
			if((!impl.isFlagged() && impl.isMine()) || (impl.isFlagged() && !impl.isMine())) {
				setPressedStateForcely(true);
			}
		});
		setBackground(Color.RED);
		setOpaque(false);
		setIcon(icon_normal);
		setBorderPainted(false);
		setMargin(new Insets(0, 0, 0, 0));
		setFocusable(false);
		setContentAreaFilled(false);
		Dimension size = new Dimension(16, 16);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
	}
	
	void setPressedState(boolean isPressed) {
		if(!impl.isDug() && !impl.isFlagged()) {
			setPressedStateForcely(isPressed);
		}
	}
	
	void setPressedStateForcely(boolean isPressed) {
		is_pressed = isPressed;
		//System.out.println("pressed: " + isPressed);
		if(is_pressed) {
			this.setIcon(icon_pressed);
		} else {
			this.setIcon(icon_normal);
		}
		repaint();
	}
	
	void setIsWrong() {
		is_wrong = true;
		repaint();
	}
	
	public void rightClick(boolean canMarked) {
		if(impl.isDug() == false) {
			if(impl.isFlagged()) {
				if(canMarked) {
					impl.mark();
				} else {
					impl.unflag();
				}
			} else if(impl.isMarked()) {
				impl.unmark();
			} else {
				impl.flag();
			}
			repaint();
		}
	}
	
	public void setAround(int around) {
		this.around = around;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int wid = getWidth();
		int hei = getHeight();
		int x = wid / 2 - 8;
		int y = hei / 2 - 8;
		
		if(is_wrong) {
			g.setColor(Color.RED);
			g.fillRect(1, 1, wid - 1, hei - 1);
		}
		
		ImageIcon to_be_printed = null;
		
		assert(impl != null);
		if(impl.isDug()) {
			if(impl.isMine()) {
				to_be_printed = icon_mine;
			} else {
				if(around > 0) {
					to_be_printed = icon_numbers[around - 1];
				}
			}
		} else {
			if(impl.isShowing()) {
				if(impl.isFlagged()) {
					if(impl.isMine()) {
						to_be_printed = icon_flag;
					} else {
						to_be_printed = icon_wrong;
					}
				} else if(impl.isMine()) {
					to_be_printed = icon_mine;
				} else if(impl.isMarked()) {
					to_be_printed = icon_mark;
				} 
			} else {
				if(impl.isFlagged()) {
					to_be_printed = icon_flag;
				} else if(impl.isMarked()) {
					to_be_printed = icon_mark;
				}
			}
		}
		if(to_be_printed != null) {
			if(is_pressed && !impl.isDug() && !impl.isShowing()) {
				g.drawImage(to_be_printed.getImage(), x + 1, y + 1, this);
			} else {
				g.drawImage(to_be_printed.getImage(), x, y, this);
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(new FlowLayout());
		
		Unit u = new Unit2D(false);
		assert(u.isMine() == true);
		ButtonField btn = new ButtonField(u);
		btn.setAround(3);
		frame.getContentPane().add(btn);
	}
}
