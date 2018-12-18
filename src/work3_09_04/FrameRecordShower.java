package work3_09_04;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameRecordShower extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static interface RecordClearReceiver {
		void onReceiver();
	}
	
	private RecordClearReceiver recordClearReceiver = () -> {
		System.out.println("清空!");
	};
	public void setRecordClearReceiver(RecordClearReceiver receiver) {
		if(receiver != null) {
			recordClearReceiver = receiver;
		}
	}
	

	private JPanel contentPane;
	private JLabel lblNewLabel;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameRecordShower frame = new FrameRecordShower();
					frame.setRecord(1, "abc", 22, "def", 333, "ghi");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrameRecordShower() {
		setTitle("\u626B\u96F7\u82F1\u96C4\u699C");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 271, 151);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(20);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("\u91CD\u65B0\u8BA1\u5206(R)");
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recordClearReceiver.onReceiver();
				repaint();
			}
		});
		btnNewButton.setMnemonic(KeyEvent.VK_R);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u786E\u5B9A");
		getRootPane().setDefaultButton(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 10, 10, 10));
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lblNewLabel = new JLabel("<html>初级:  0 秒  匿名<br>中级:  0 秒  匿名<br>高级:  0 秒  匿名</html>");
		lblNewLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel_1.add(lblNewLabel);
	}
	
	public void setRecord(int bottom_sec, String bottom_name, int intermediate_sec, String intermediate_name, int enterprising_sec, String enterprising_name) {
		lblNewLabel.setText(String.format(
				"<html>初级:  %3d 秒        %s<br>中级:  %3d 秒        %s<br>高级:  %3d 秒        %s</html>",
				bottom_sec, bottom_name, intermediate_sec, intermediate_name, enterprising_sec, enterprising_name).replace(" ", "&ensp;"));
	}

}
