package work3_09_04;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonReplay extends JButton {
	
	private static final long serialVersionUID = -2161872470399033696L;
	static public final int ICON_SMILE = 0;
	static public final int ICON_OH = 1;
	static public final int ICON_CRY = 2;
	static public final int ICON_WINNER = 3;
	static private final ImageIcon[] icon_expressions;
	static private final ImageIcon icon_normal;
	static private final ImageIcon icon_pressed;
	static {
		icon_expressions = new ImageIcon[4];
		icon_expressions[0] = new ImageIcon("res/b2s.png");
		icon_expressions[1] = new ImageIcon("res/b2o.png");
		icon_expressions[2] = new ImageIcon("res/b2c.png");
		icon_expressions[3] = new ImageIcon("res/b2w.png");
		icon_normal = new ImageIcon("res/b2.png");
		icon_pressed = new ImageIcon("res/b2d.png");
	}
	
	
	boolean mouse_pressed = false;
	boolean is_pressed = false;
	int icon_id = ICON_SMILE;
	
	public ButtonReplay() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				is_pressed = true;
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				is_pressed = false;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
					is_pressed = false;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
					is_pressed = true;
				}
			}
		});
		setPreferredSize(new Dimension(26, 26));
		setFocusPainted(false);
		setMargin(new Insets(0, 0, 0, 0));
		setBorderPainted(false);
		setContentAreaFilled(false);
		setIcon(icon_normal);
		setPressedIcon(icon_pressed);
	}
	
	public final MouseListener mousePressed = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			if(icon_id == ICON_SMILE && e.getButton() == MouseEvent.BUTTON1) {
				icon_id = ICON_OH;
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(icon_id == ICON_OH && e.getButton() == MouseEvent.BUTTON1) {
				icon_id = ICON_SMILE;
				repaint();
			}
		}
	};
	
	public void setIconId(int icon_id) {
		this.icon_id = icon_id;
		repaint();
	}
	
	public int getIconId() {
		return icon_id;
	}
	
	public void setSomethingPressed() {
		if(icon_id == ICON_SMILE) {
			setIconId(ICON_OH);
		}
	}
	
	public void setNothingPressed() {
		setIconId(ICON_SMILE);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(is_pressed) {
			g.drawImage(icon_expressions[icon_id].getImage(), 6, 6, this);
		} else {
			g.drawImage(icon_expressions[icon_id].getImage(), 5, 5, this);
		}
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());
		ButtonReplay btn = new ButtonReplay();
		frame.getContentPane().add(btn);
		frame.setVisible(true);
		
		JButton test_btn = new JButton();
		test_btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				btn.setSomethingPressed();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				btn.setNothingPressed();
			}
		});
		frame.add(test_btn);
	}

}
