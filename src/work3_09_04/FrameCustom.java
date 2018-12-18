package work3_09_04;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameCustom extends JFrame {

	private static final long serialVersionUID = 8759838538119209823L;
	
	public static interface ConfigurationReceiver {
		void onReceiver(int width, int height, int mineTotal);
	}
	
	private ConfigurationReceiver configurationReceiver = (wid, hei, total) -> {
		System.out.println(String.format("wid: %d, hei: %d, total: %d", wid, hei, total));
	};
	public void setConfigurationReceiver(ConfigurationReceiver receiver) {
		if(receiver != null) {
			configurationReceiver = receiver;
		}
	}
	
	private JPanel pane_main;
	private JTextField txt_height;
	private JTextField txt_width;
	private JTextField txt_total;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameCustom frame = new FrameCustom(10, 11, 12);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameCustom(int wid, int hei, int mine_num) {
		setType(Type.UTILITY);
		setResizable(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setTitle("\u81EA\u5B9A\u4E49\u96F7\u533A");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pane_main = new JPanel();
		pane_main.setBorder(new EmptyBorder(30, 15, 30, 15));
		setContentPane(pane_main);
		pane_main.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_input = new JPanel();
		pane_main.add(panel_input, BorderLayout.CENTER);
		GridBagLayout gbl_panel_input = new GridBagLayout();
		gbl_panel_input.columnWidths = new int[] {0, 0};
		gbl_panel_input.rowHeights = new int[] {0, 0, 0};
		gbl_panel_input.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel_input.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel_input.setLayout(gbl_panel_input);
		
		JLabel lbl_height = new JLabel("\u9AD8\u5EA6(H):");
		lbl_height.setDisplayedMnemonic('H');
		GridBagConstraints gbc_lbl_height = new GridBagConstraints();
		gbc_lbl_height.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_height.gridx = 0;
		gbc_lbl_height.gridy = 0;
		panel_input.add(lbl_height, gbc_lbl_height);
		
		txt_height = new JTextField();
		lbl_height.setLabelFor(txt_height);
		txt_height.setText(Integer.toString(hei));
		GridBagConstraints gbc_txt_height = new GridBagConstraints();
		gbc_txt_height.insets = new Insets(0, 0, 5, 5);
		gbc_txt_height.gridx = 1;
		gbc_txt_height.gridy = 0;
		panel_input.add(txt_height, gbc_txt_height);
		txt_height.setColumns(7);
		
		JPanel panel_ctrl = new JPanel();
		GridBagConstraints gbc_panel_ctrl = new GridBagConstraints();
		gbc_panel_ctrl.gridheight = 3;
		gbc_panel_ctrl.fill = GridBagConstraints.VERTICAL;
		gbc_panel_ctrl.gridx = 2;
		gbc_panel_ctrl.gridy = 0;
		panel_input.add(panel_ctrl, gbc_panel_ctrl);
		panel_ctrl.setLayout(new GridLayout(0, 1, 0, 20));
		
		JButton btn_yes = new JButton("\u786E\u5B9A");
		this.getRootPane().setDefaultButton(btn_yes);
		btn_yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int wid = 0;
				int hei = 0;
				int total = 0;
				try {
					wid = Integer.parseInt(txt_width.getText());
				} catch(NumberFormatException ex) {}
				try {
					hei = Integer.parseInt(txt_height.getText());
				} catch(NumberFormatException ex) {}
				try {
					total = Integer.parseInt(txt_total.getText());
				} catch(NumberFormatException ex) {}
				configurationReceiver.onReceiver(wid, hei, total);
				dispose();
			}
		});
		btn_yes.setPreferredSize(new Dimension(60, 23));
		panel_ctrl.add(btn_yes);
		JButton btn_no = new JButton("\u53D6\u6D88");
		btn_no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btn_no.setPreferredSize(new Dimension(60, 23));
		panel_ctrl.add(btn_no);
		
		JLabel lbl_width = new JLabel("\u5BBD\u5EA6(W):");
		lbl_width.setDisplayedMnemonic('W');
		GridBagConstraints gbc_lbl_width = new GridBagConstraints();
		gbc_lbl_width.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_width.gridx = 0;
		gbc_lbl_width.gridy = 1;
		panel_input.add(lbl_width, gbc_lbl_width);
		
		txt_width = new JTextField();
		lbl_width.setLabelFor(txt_width);
		txt_width.setText(Integer.toString(wid));
		GridBagConstraints gbc_txt_width = new GridBagConstraints();
		gbc_txt_width.insets = new Insets(0, 0, 5, 5);
		gbc_txt_width.gridx = 1;
		gbc_txt_width.gridy = 1;
		panel_input.add(txt_width, gbc_txt_width);
		txt_width.setColumns(7);
		
		JLabel lbl_total = new JLabel("\u96F7\u6570(M):");
		lbl_total.setDisplayedMnemonic('M');
		GridBagConstraints gbc_lbl_total = new GridBagConstraints();
		gbc_lbl_total.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_total.gridx = 0;
		gbc_lbl_total.gridy = 2;
		panel_input.add(lbl_total, gbc_lbl_total);
		
		txt_total = new JTextField();
		lbl_total.setLabelFor(txt_total);
		txt_total.setText(Integer.toString(mine_num));
		GridBagConstraints gbc_txt_total = new GridBagConstraints();
		gbc_txt_total.insets = new Insets(0, 0, 0, 5);
		gbc_txt_total.gridx = 1;
		gbc_txt_total.gridy = 2;
		panel_input.add(txt_total, gbc_txt_total);
		txt_total.setColumns(7);
		
		pack();
	}

}
