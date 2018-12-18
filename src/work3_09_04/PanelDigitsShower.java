package work3_09_04;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.border.EmptyBorder;

class LabelDigit extends JLabel {
	
	private static final long serialVersionUID = 5726607041822128369L;
	static private ImageIcon[] ico_digits;
	static private ImageIcon ico_neg;
	static {
		ico_digits = new ImageIcon[10];
		for(int i = 0; i < ico_digits.length; i++) {
			ico_digits[i] = new ImageIcon(String.format("res/d%d.png", i));
		}
		ico_neg = new ImageIcon("res/d-.png");
	}
	
	private char digit;
	
	public LabelDigit() {
		setHorizontalAlignment(JLabel.CENTER);
		setPreferredSize(new Dimension(11, 21));
	}
	
	public LabelDigit(char digit) {
		this();
		setDigit(digit);
	}
	
	public void setDigit(char digit) {
		this.digit = digit;
		if(digit == '-') {
			setIcon(ico_neg);
		} else {
			assert(digit >= '0' && digit <= '9');
			setIcon(ico_digits[digit - '0']);
		}
	}
	
	public int getDigit() {
		return digit;
	}
}

public class PanelDigitsShower extends JPanel {
	
	private static final long serialVersionUID = -3079239602619801205L;
	public final LabelDigit[] digits;
	private int number;
	
	public PanelDigitsShower(int number) {
		setBorder(new EmptyBorder(2, 2, 2, 2));
		setLayout(new GridLayout(1, 0, 2, 0));
		digits = new LabelDigit[3];
		for(int i = 0; i < digits.length; i++) {
			digits[i] = new LabelDigit();
			add(digits[i]);
		}
		setNumber(number);
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
		boolean is_neg;
		if(is_neg = number < 0) {
			number = -number;
		}
		int d0 = number % 10;
		number /= 10;
		int d1 = number % 10;
		number /= 10;
		int d2 = number % 10;
		if(is_neg) {
			digits[0].setDigit('-');
		} else {
			digits[0].setDigit((char)(d2 + '0'));
		}
		digits[1].setDigit((char)(d1 + '0'));
		digits[2].setDigit((char)(d0 + '0'));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		g.setColor(Color.GRAY);
		g.drawLine(0, 0, width - 2, 0);
		g.drawLine(0, 0, 0, height - 2);
		g.setColor(Color.WHITE);
		g.drawLine(width - 1, 1, width - 1, height - 1);
		g.drawLine(1, height - 1, width - 1, height - 1);
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, width - 2, height -2);
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new PanelDigitsShower(123));
		frame.getContentPane().add(new PanelDigitsShower(-1));
		frame.getContentPane().add(new PanelDigitsShower(-99));
		frame.getContentPane().add(new PanelDigitsShower(0));
		frame.getContentPane().add(new PanelDigitsShower(1));
		frame.setVisible(true);
	}

}
