package work3_09_04;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import work3_09_04.mine.Mine;
import work3_09_04.mine.Mine.LoseReceiver;
import work3_09_04.mine.normal.Mine2D;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class DataKeeper implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String defaultCacheFileName = "mine.dat";
	public static DataKeeper getSavedData(File path) {
		FileInputStream fis;
		ObjectInputStream ois;
		DataKeeper keeper = null;
		try {
			fis = new FileInputStream(path);
			ois = new ObjectInputStream(fis);
			keeper = (DataKeeper)ois.readObject();
		} catch(FileNotFoundException e) {
		} catch (ClassNotFoundException e) {
			System.out.println("�����ȡʧ��");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(keeper == null) {
			keeper = new DataKeeper();
		}
		return keeper;
	}
	public static DataKeeper getSavedData() {
		return getSavedData(new File(defaultCacheFileName));
	}
	public static void saveData(File path, DataKeeper keeper) {
		try(FileOutputStream fos = new FileOutputStream(path)) {
			
			try(ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(keeper);
				oos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void saveData(DataKeeper keeper) {
		saveData(new File(defaultCacheFileName), keeper);
	}
	
	public MainFrame.Level level = MainFrame.Level.bottom;
	public int custom_rows = 9;
	public int custom_colums = 9;
	public int custom_total = 10;
	
	public int bottom_min_sec = 999;
	public String bottom_min_name = "����";
	
	public int intermediate_min_sec = 999;
	public String intermediate_min_name = "����";
	
	public int enterprising_min_sec = 999;
	public String enterprising_min_name = "����";
	
	public boolean can_marked = true;
	public boolean enable_sound = true;
	public void clearRecord() {
		bottom_min_sec = 999;
		bottom_min_name = "����";
		
		intermediate_min_sec = 999;
		intermediate_min_name = "����";
		
		enterprising_min_sec = 999;
		enterprising_min_name = "����";
	}
	
	// ����
}

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -1849051572258132389L;
	private final JPanel pane_main;
	
	private final PanelDigitsShower mine_shower;
	private final PanelDigitsShower time_shower;
	private final PanelGame pane_game;
	private final ButtonReplay btn_start = new ButtonReplay();
	private final JCheckBoxMenuItem menuitem_bottom;
	private final JCheckBoxMenuItem menuitem_intermediate;
	private final JCheckBoxMenuItem menuitem_enterprising;
	private final JCheckBoxMenuItem menuitem_custom;
	Mine game;
	Timer timer = null;
	DataKeeper dataKeeper = DataKeeper.getSavedData();
	
	private final Mine2D.Configurer game_cfg = new Mine2D.Configurer();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public MainFrame() {
		setResizable(false);
		setTitle("ɨ��");
		setBounds(new Rectangle(0, 0, 200, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 194, 225);
		
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu menu_game = new JMenu("��Ϸ(G)");
		menu_game.setMnemonic(KeyEvent.VK_G);
		menubar.add(menu_game);
		
		JMenuItem menuitem_new = new JMenuItem("����(N)");
		menuitem_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new_game();
			}
		});
		menuitem_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		menuitem_new.setMnemonic(KeyEvent.VK_N);
		menu_game.add(menuitem_new);
		menu_game.addSeparator();
		
		menuitem_bottom = new JCheckBoxMenuItem("����(B)");
		menuitem_bottom.setSelected(true);
		menuitem_bottom.addActionListener((e) -> { setLevel(Level.bottom); });
		menuitem_bottom.setMnemonic(KeyEvent.VK_B);
		menu_game.add(menuitem_bottom);

		menuitem_intermediate = new JCheckBoxMenuItem("�м�(I)");
		menuitem_intermediate.addActionListener((e) -> { setLevel(Level.intermediate); });
		menuitem_intermediate.setMnemonic(KeyEvent.VK_I);
		menu_game.add(menuitem_intermediate);
		
		menuitem_enterprising = new JCheckBoxMenuItem("�߼�(E)");
		menuitem_enterprising.addActionListener((e) -> { setLevel(Level.enterprising); });
		menuitem_enterprising.setMnemonic(KeyEvent.VK_E);
		menu_game.add(menuitem_enterprising);
		
		menuitem_custom = new JCheckBoxMenuItem("�Զ���(C)...");
		menuitem_custom.addActionListener((e) -> { setLevel(Level.custom); });
		menuitem_custom.setMnemonic(KeyEvent.VK_C);
		menu_game.add(menuitem_custom);
		menu_game.addSeparator();
		
		JCheckBoxMenuItem menuitem_mark = new JCheckBoxMenuItem("���(?)(M)");
		menuitem_mark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getSource();
				setCanMarked(item.isSelected());
			}
		});
		menuitem_mark.setSelected(true);
		menuitem_mark.setMnemonic(KeyEvent.VK_M);
		menu_game.add(menuitem_mark);
		
		JCheckBoxMenuItem menuitem_sound = new JCheckBoxMenuItem("����(������...)(S)");
		menuitem_sound.setEnabled(false);
		menuitem_sound.setMnemonic(KeyEvent.VK_S);
		menu_game.add(menuitem_sound);
		menu_game.addSeparator();
		
		JMenuItem menuitem_top = new JMenuItem("���а�(T)...");
		menuitem_top.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRecord();
			}
		});
		menuitem_top.setMnemonic(KeyEvent.VK_T);
		menu_game.add(menuitem_top);
		menu_game.addSeparator();
		
		JMenuItem menuitem_exit = new JMenuItem("�˳�(X)");
		menuitem_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onExit();
				System.exit(0);
			}
		});
		menuitem_exit.setMnemonic(KeyEvent.VK_X);
		menu_game.add(menuitem_exit);
		
		JMenu menu_help = new JMenu("����(H)");
		menu_help.setMnemonic(KeyEvent.VK_H);
		menubar.add(menu_help);
		
		JMenuItem menuitem_learning = new JMenuItem("����(L)...");
		menuitem_learning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogStringShower("����", "<html>"
						+ "&emsp;&emsp;ÿ��������Χ��������8�����ӣ������ڱ��ϵĸ��ӻ�ֻ��5��������3�����ӡ�ÿ���������������ʾ�������������Χ�ĸ�������׵�������<br><br>"
						+ "&emsp;&emsp;������в����׵ĸ��ӣ�����ʤ����<br><br>"
						+ "&emsp;&emsp;̫�ѣ���� ��Ϸ->�Զ���->�Զ�������->���ø߶�Ϊ24�����Ϊ30������Ϊ10 ���԰ɡ�<br><br>"
						+ "tips: �Զ��������²����¼��óɼ�Ŷ"
						+ "</html>").setVisible(true);
			}
		});
		menuitem_learning.setMnemonic(KeyEvent.VK_L);
		menu_help.add(menuitem_learning);
		
		JMenuItem menuitem_more = new JMenuItem("����(M)...");
		menuitem_more.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogStringShower("����", "<html>"
						+ "&emsp;&emsp;��һ��������Χ��������������������ϵ�������ͬʱ�����Ҽ�һ�𵥻�������ӿ��Կ��ٰ���Χû����ĸ��Ӷ��򿪡�<br><br>"
						+ "&emsp;&emsp;��ʱ�����ͨ�������֪�׵�λ�á�һ��������Χ���׵�λ����ʱ������ȫȷ�������ǿ���ȷ���״�ŵķ�Χ������Щ�׵Ĵ�ŵķ�Χǡ���ڸ���һ�����ֵ���Χ��Χ��ʱ��������������ֵ�һ�����׵�λ�þͿ��Ա�ȷ���ˡ�������Щ�׵��������þ����������ʱ��������ֵ���Χ���˿������׵ĵط�������û���׵ġ�"
						+ "</html>").setVisible(true);
			}
		});
		menuitem_more.setMnemonic(KeyEvent.VK_M);
		menu_help.add(menuitem_more);
		menu_help.addSeparator();
		
		JMenuItem menuitem_about = new JMenuItem("����(A)...");
		menuitem_about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogAbout().setVisible(true);
			}
		});
		menuitem_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menuitem_about.setMnemonic(KeyEvent.VK_A);
		menu_help.add(menuitem_about);
		
		pane_main = new JPanel();
		pane_main.setBackground(Color.LIGHT_GRAY);
		pane_main.setBorder(new EmptyBorder(5, 5, 5, 5));
		pane_main.setLayout(new BorderLayout(0, 0));
		setContentPane(pane_main);
		
		//******************* �ϲ�
		JPanel pane_ctrl = new JPanel();
		pane_ctrl.setLayout(new BorderLayout(0, 0));
		pane_ctrl.setBorder(BorderFactory.createLoweredBevelBorder());
		pane_main.add(pane_ctrl, BorderLayout.NORTH);
		
		JPanel pane_mine = new JPanel();
		pane_mine.setBackground(Color.LIGHT_GRAY);
		pane_mine.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pane_ctrl.add(pane_mine, BorderLayout.WEST);
		mine_shower = new PanelDigitsShower(0);
		mine_shower.setNumber(123);
		pane_mine.add(mine_shower);

		JPanel pane_time = new JPanel();
		pane_time.setBackground(Color.LIGHT_GRAY);
		pane_ctrl.add(pane_time, BorderLayout.EAST);
		time_shower = new PanelDigitsShower(0);
		pane_time.add(time_shower);
		
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new_game();
			}
		});
		JPanel panel_replay = new JPanel();
		panel_replay.setBackground(Color.LIGHT_GRAY);
		panel_replay.add(btn_start);
		pane_ctrl.add(panel_replay, BorderLayout.CENTER);

		//****************** �в�
		JPanel pane_center = new JPanel();
		pane_center.setBorder(new EmptyBorder(6, 0, 0, 0));
		pane_center.setBackground(Color.LIGHT_GRAY);
		FlowLayout flowLayout = (FlowLayout) pane_center.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		pane_main.add(pane_center, BorderLayout.CENTER);
		pane_game = new PanelGame(null);
		pane_center.add(pane_game);
		
		setCanMarked(dataKeeper.can_marked);
		if(dataKeeper.level == Level.custom) {
			setCustomLevel(dataKeeper.custom_rows, dataKeeper.custom_colums, dataKeeper.custom_total);
		} else {
			setLevel(dataKeeper.level);
		}
		refreshDigitShower();
		
		pane_game.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				refreshDigitShower();
				if(!game.isOver()) {
					startTime();
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				checkWin();
			}
		});
		
		//****************** �������ӵ���¼�
		pane_main.addMouseListener(btn_start.mousePressed);
		pane_center.addMouseListener(btn_start.mousePressed);
		pane_ctrl.addMouseListener(btn_start.mousePressed);
		pane_game.addMouseListener(btn_start.mousePressed);
		game.setLoseReceiver(loseReceiver);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onExit();
			}
		});
		
		pack();
	}
	
	public void new_game() {
		game = game_cfg.getGame();
		btn_start.setIconId(ButtonReplay.ICON_SMILE);
		pane_game.setGame(game);
		game.setLoseReceiver(loseReceiver);
		refreshDigitShower();
		time_shower.setNumber(0);
		if(timer != null) {
			timer.setRepeats(false);
			timer = null;
		}
		pack();
	}
	
	public void refreshDigitShower() {
		mine_shower.setNumber(game.getRestMine());
	}
	
	private Level level = Level.bottom;
	public enum Level {
		bottom, intermediate, enterprising, custom
	}
	public void setCustomLevel(int wid, int hei, int total) {
		menuitem_bottom.setSelected(false);
		menuitem_intermediate.setSelected(false);
		menuitem_enterprising.setSelected(false);
		menuitem_custom.setSelected(true);
		level = Level.custom;
		if(wid == 0) {
			wid = game_cfg.getRows();
		}
		if(wid < 9) {
			wid = 9;
		}
		if(wid > 30) {
			wid = 30;
		}
		if(hei == 0) {
			hei = game_cfg.getColums();
		}
		if(hei < 9) {
			hei = 9;
		}
		if(hei > 24) {
			hei = 24;
		}
		if(total < 10) {
			total = 10;
		}
		if(total > hei * wid * 0.9) {
			total = (int)(hei * wid * 0.9);
		}
		game_cfg.setAmount(total).setSize(wid, hei);
		new_game();
	}
	public void setLevel(Level l) {
		menuitem_bottom.setSelected(false);
		menuitem_intermediate.setSelected(false);
		menuitem_enterprising.setSelected(false);
		menuitem_custom.setSelected(false);
		level = l;
		switch(l) {
		case bottom:
			menuitem_bottom.setSelected(true);
			game_cfg.setAmount(10).setSize(9, 9);
			break;
		case intermediate:
			menuitem_intermediate.setSelected(true);
			game_cfg.setSize(16, 16).setAmount(40);
			break;
		case enterprising:
			menuitem_enterprising.setSelected(true);
			game_cfg.setSize(30, 16).setAmount(99);
			break;
		case custom:
			menuitem_custom.setSelected(true);
			FrameCustom frame = new FrameCustom(game_cfg.getRows(), game_cfg.getColums(), game_cfg.getAmount());
			frame.setConfigurationReceiver((wid, hei, total) -> { setCustomLevel(wid, hei, total); });
			frame.setVisible(true);
			Rectangle rect = new Rectangle();
			rect.setSize(frame.getSize());
			Point p = getLocation();
			rect.setLocation(10 + p.x, 100 + p.y);
			frame.setBounds(rect);
			break;
		}
		new_game();
	}
	
	public void checkWin() {
		if(game.isWon()) {
			btn_start.setIconId(ButtonReplay.ICON_WINNER);
			refreshDigitShower();
			if(timer != null) {
				timer.setRepeats(false);
				timer = null;
			}
			int time = time_shower.getNumber();
			if(time < 999 && level != Level.custom) {
				FrameNewrecord.NameSetter setter = null;
				String info = null;
				switch(level) {
				case bottom:
					if(time < dataKeeper.bottom_min_sec) {
						dataKeeper.bottom_min_sec = time;
						info = "���Ƴ�����¼��";
						setter = name -> {
							dataKeeper.bottom_min_name = name;
						};
					}
					break;
				case intermediate:
					if(time < dataKeeper.intermediate_min_sec) {
						dataKeeper.intermediate_min_sec = time;
						info = "�����м���¼��";
						setter = name -> {
							dataKeeper.intermediate_min_name = name;
						};
					}
					break;
				case enterprising:
					if(time < dataKeeper.enterprising_min_sec) {
						dataKeeper.enterprising_min_sec = time;
						info = "���Ƹ߼���¼��";
						setter = name -> {
							dataKeeper.enterprising_min_name = name;
						};
					}
					break;
				default:
					return;
				}
				if(info != null && setter != null) {
					FrameNewrecord frame = new FrameNewrecord(setter, info);
					Rectangle rect = new Rectangle();
					rect.setSize(frame.getSize());
					Point p = getLocation();
					rect.setLocation(10 + p.x, 100 + p.y);
					frame.setBounds(rect);
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							System.out.println("closing");
						}
						@Override
						public void windowClosed(WindowEvent e) {
							System.out.println("closed");
							showRecord();
						}
					});
				}
			}
			repaint();
		}
	}
	
	public void startTime() {
		if(timer == null) {
			time_shower.setNumber(1);
			timer = new Timer(1000, (e) -> {
				if(((Timer)e.getSource()).isRunning()) {
					time_shower.setNumber(time_shower.getNumber() + 1);
				}
			});
			timer.start();
		}
	}
	
	public void setCanMarked(boolean canMarked) {
		pane_game.setCanMarked(canMarked);
	}
	
	public void showRecord() {
		FrameRecordShower shower = new FrameRecordShower();
		Rectangle rect = new Rectangle();
		rect.setSize(shower.getSize());
		Point p = getLocation();
		rect.setLocation(10 + p.x, 100 + p.y);
		shower.setBounds(rect);
		shower.setRecord(dataKeeper.bottom_min_sec,
				dataKeeper.bottom_min_name,
				dataKeeper.intermediate_min_sec,
				dataKeeper.intermediate_min_name,
				dataKeeper.enterprising_min_sec,
				dataKeeper.enterprising_min_name);
		shower.setRecordClearReceiver(() -> {
			dataKeeper.clearRecord();
			shower.setRecord(dataKeeper.bottom_min_sec,
					dataKeeper.bottom_min_name,
					dataKeeper.intermediate_min_sec,
					dataKeeper.intermediate_min_name,
					dataKeeper.enterprising_min_sec,
					dataKeeper.enterprising_min_name);
		});
		shower.setVisible(true);
	}
	
	void onExit() {
		dataKeeper.level = level;
		if(level == Level.custom) {
			dataKeeper.custom_colums = game_cfg.getColums();
			dataKeeper.custom_rows = game_cfg.getRows();
			dataKeeper.custom_total = game_cfg.getAmount();
		}
		dataKeeper.can_marked = pane_game.getCanMarked();
		DataKeeper.saveData(dataKeeper);
		System.out.println("saved");
	}
	
	private final LoseReceiver loseReceiver = (s) -> {
		System.out.println("lose2");
		btn_start.setIconId(ButtonReplay.ICON_CRY);
		if(timer != null) {
			timer.setRepeats(false);
			timer = null;
		}
	};
}
