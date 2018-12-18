package work3_09_04;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogAbout extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogAbout dialog = new DialogAbout();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogAbout() {
		setResizable(false);
		setTitle("\u5173\u4E8E\"\u626B\u96F7\"");
		setBounds(100, 100, 370, 487);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 15, 10, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JLabel lblNewLabel = new JLabel(
					"<html>"
					+ "<h1>版本</h1>"
					+ "&emsp;&emsp;版本 aplha 0，使用Java awt和swing所作。"
					+ "<h1>唯一许可用途</h1>"
					+ "&emsp;&emsp;作者的某一次Java课设作业及学术讨论。"
					+ "<h1>作者</h1>"
					+ "&emsp;&emsp;airaou 2018<br>"
					+ "&emsp;&emsp;联系作者: https://github.com/airaou"
					+ "<h1>声明</h1>"
					+ "&emsp;&emsp;本程序为学习Java等相关知识而模仿Windows的“扫雷”<br>"
					+ "游戏所作，并只用作学术相关的研究，本程序引发的所有法律<br>"
					+ "纠纷与作者无关。本程序的最终解释权归作者所有。"
					+ "</html>"
			);
			lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(0, 0, 5, 0));
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				JLabel lblNewLabel_1 = new JLabel("<html><h1 style=\"font-size: 40px\">扫雷</h1></html>");
				panel.add(lblNewLabel_1);
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
		pack();
	}

}
