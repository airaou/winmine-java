package work3_09_04;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import work3_09_04.mine.Mine;
import work3_09_04.mine.Position;
import work3_09_04.mine.Unit;
import work3_09_04.mine.normal.Mine2D;
import work3_09_04.mine.normal.Position2D;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class PanelGame extends JPanel {

	private static final long serialVersionUID = 4085986166940548538L;
	
	private Position2D beforePos = null;
	private ButtonField beforeButton = null;
	private boolean doublePressed = false;
	private boolean can_marked = false;;
	
	private final MouseListener mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
			//System.out.println("released");

			if(doublePressed && ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0 || (e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == 0)) {
				doublePressed = false;
				if(beforePos != null) {
					setPressedStateAround(beforePos, false);
					game.digAround(beforePos);
				}
				//System.out.println("doubleReleased");
			}
			switch(e.getButton()) {
			case MouseEvent.BUTTON1:
				if(beforeButton == null) {
					mouseMovedTo(e);
				}
				if(beforeButton != null) {
					beforeButton.setPressedState(false);
					if((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == 0) {
						game.dig(beforePos);
					}
					beforeButton = null;
					beforePos = null;
				}
				break;
			default:
				//System.out.println("mouseReleased: err: " + e.getButton());
				break;
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			ButtonField btn;
			if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0 && (e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
				doublePressed = true;
				System.out.println("doublePressed");
			}
			switch(e.getButton()) {
			case MouseEvent.BUTTON1:
				mouseMovedTo(e);
				break;
			case MouseEvent.BUTTON3:
				if(!game.isOver()) {
					if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
						btn = (ButtonField)e.getSource();
						btn.rightClick(can_marked);
					} else {
						mouseMovedTo(e);
					}
				}
				break;
			default:
				//System.out.println("mousePressed: err: " + e.getButton());
				break;
			}
		}

	};
	private final MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			if((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == 0) {
				return;
			}
			mouseMovedTo(e);
		}
	};
	
	private Mine game;
	private ButtonField[][] fields;

	public PanelGame(Mine mine) {
		setLayout(null);
		setGame(mine);
		setBorder(new EmptyBorder(3, 3, 3, 3));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width - 1, 3);
		g.fillRect(0, 0, 3, height - 1);
		g.setColor(Color.WHITE);
		g.fillRect(width - 3, 1, width - 1, height - 1);
		g.fillRect(1, height - 3, width - 1, height - 1);
		g.setColor(Color.GRAY);
		g.drawLine(width - 3, 1, width - 3, 1);
		g.drawLine(1, height - 3, 1, height - 3);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(1, height - 2, 2, height -3);
		g.drawLine(width - 3, 2, width - 2, 1);
	}
	
	private void mouseMovedTo(MouseEvent e) {
		if(game.isOver()) {
			return;
		}
		int x = (e.getXOnScreen() - (getLocationOnScreen().x + 3));
		int y = (e.getYOnScreen() - (getLocationOnScreen().y + 3));
		if(x >= 0 && y >= 0 && x < game.getRows() * 16 && y < game.getColums() * 16) {
			ButtonField newButton = fields[y / 16][x / 16];
			if(beforeButton != null) {
				beforeButton.setPressedState(false);
				if(doublePressed) {
					setPressedStateAround(beforePos, false);
				}
			}
			beforePos = Position2D.getInstance(x / 16, y / 16);
			beforeButton = newButton;
			beforeButton.setPressedState(true);
			if(doublePressed) {
				setPressedStateAround(beforePos, true);
			}
		} else {
			if(beforeButton != null) {
				beforeButton.setPressedState(false);
				if(doublePressed) {
					setPressedStateAround(beforePos, false);
				}
				beforeButton = null;
				beforePos = null;
			}
		}
	}
	
	private void setPressedStateAround(Position pos, boolean state) {
		for(Position p : game.getAround(pos)) {
			Position2D pos2d = (Position2D)p;
			fields[pos2d.toArray()[1]][pos2d.toArray()[0]].setPressedState(state);
		}
	}
	
	@Override
	public void addMouseListener(MouseListener listener) {
		super.addMouseListener(listener);
		for(ButtonField[] btns : fields) {
			for(ButtonField btn : btns) {
				btn.addMouseListener(listener);
			}
		}
	}
	
	public void setGame(Mine mine) {
		if(mine == null) {
			return;
		}
		this.removeAll();
		game = mine;
		int rows = mine.getRows();
		int cols = mine.getColums();
		setLayout(new GridLayout(0, rows, 0, 0));
		fields = new ButtonField[cols][rows];
		for(int y = 0; y < cols; y++) {
			for(int x = 0; x < rows; x++) {
				Unit u = game.get(Position2D.getInstance(x, y));
				ButtonField btn = new ButtonField(u);
				fields[y][x] = btn;
				btn.addMouseMotionListener(mouseMotionAdapter);
				btn.addMouseListener(mouseAdapter);
				for(MouseListener listener : getMouseListeners()) {
					btn.addMouseListener(listener);
				}
				add(btn);
			}
		}
	}
	
	public void setCanMarked(boolean canMarked) {
		can_marked = canMarked;
	}
	public boolean getCanMarked() {
		return can_marked;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Mine2D.Configurer cfg = new Mine2D.Configurer();
		cfg.setSize(10, 20).setAmount(10);
		PanelGame pan = new PanelGame(cfg.getGame());
		frame.add(pan);
		frame.setVisible(true);
	}
}
