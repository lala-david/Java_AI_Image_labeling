
import javax.swing.*;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FTP_LIST extends JFrame {
	static RoundedButton b1_;

	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();

	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();
	JTextField t3 = new JTextField();
	JTextField t4 = new JTextField();
	JTextField t5 = new JTextField();
	JTextField t6 = new JTextField();
	JTextArea ta = new JTextArea();
	JButton btn1, btn2, btn3, btn4, btn5;

	JLabel ¤Ó1 = new JLabel("Host : ");
	JLabel ¤Ó2 = new JLabel("Port : ");
	JLabel ¤Ó3 = new JLabel("Name : ");
	JLabel ¤Ó4 = new JLabel("Password : ");
	JLabel ¤Ó5 = new JLabel("Server : ");

	FTP_LIST() {

		// »ó´Ü¹Ù µðÀÚÀÎ
		JLabel la_ = new JLabel("¾²·¹±â Á¤Á¦ ÇÁ·Î±×·¥");

		la_.setLocation(5, 0); // la¸¦ (130,50) À§Ä¡·Î ÁöÁ¤
		la_.setSize(700, 55); // la¸¦ 500, 35 Å©±â·Î ÁöÁ¤
		la_.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		la_.setForeground(Color.getHSBColor(221, 160, 221)); // ±Û¾¾ »ö»ó
		jpanel.add(la_); // la¸¦ ÄÁÅÙÆ®ÆÒ¿¡ ºÎÂø

		b1_ = new RoundedButton("X");
		b1_.setBounds(1325, 0, 75, 75); // °¡·Î, ¼¼·Î, ¹öÆ° °¡·Î, ¹öÆ° ¼¼·Î
		b1_.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 30));
		b1_.setBackground(Color.getHSBColor(33, 84, 54));
		b1_.setForeground(Color.getHSBColor(0, 0, 30));
		jpanel.add(b1_);

		b1_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		GUI_init();
	}

	public void GUI_init() {
		jframe.setSize(1400, 900);
		jframe.setTitle("FTP Á¤º¸");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jpanel.setBackground(Color.getHSBColor(0, 42, 42));
		jpanel.setLayout(null);
		jframe.setLocationRelativeTo(null);
		jframe.setUndecorated(true); // ÇÁ·¹ÀÓÀÇ Å¸ÀÌÆ²¹Ù¸¦ ¾ø¾Ú
		jframe.add(jpanel);
		jframe.setVisible(true);

		t1.setBounds(250, 283, 100, 30);
		t1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		jpanel.add(t1);
		¤Ó1.setBounds(170, 270, 100, 50);
		¤Ó1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		¤Ó1.setForeground(Color.getHSBColor(221, 160, 221)); // ±Û¾¾ »ö»ó
		jpanel.add(¤Ó1);

		t2.setBounds(455, 283, 100, 30);
		t2.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		jpanel.add(t2);
		¤Ó2.setBounds(380, 270, 100, 50);
		¤Ó2.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		¤Ó2.setForeground(Color.getHSBColor(221, 160, 221)); // ±Û¾¾ »ö»ó
		jpanel.add(¤Ó2);

		t3.setBounds(680, 283, 100, 30);
		t3.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		jpanel.add(t3);
		¤Ó3.setBounds(580, 270, 100, 50);
		¤Ó3.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		¤Ó3.setForeground(Color.getHSBColor(221, 160, 221)); // ±Û¾¾ »ö»ó
		jpanel.add(¤Ó3);

		t4.setBounds(950, 283, 100, 30);
		t4.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		jpanel.add(t4);
		¤Ó4.setBounds(810, 270, 200, 50);
		¤Ó4.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		¤Ó4.setForeground(Color.getHSBColor(221, 160, 221)); // ±Û¾¾ »ö»ó
		jpanel.add(¤Ó4);

		t5.setBounds(1185, 283, 100, 30);
		t5.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		jpanel.add(t5);
		¤Ó5.setBounds(1080, 270, 200, 50);
		¤Ó5.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		¤Ó5.setForeground(Color.getHSBColor(221, 160, 221)); // ±Û¾¾ »ö»ó
		jpanel.add(¤Ó5);

		t6.setBounds(500, 130, 250, 60);
		jpanel.add(t6);

		JScrollPane jsp = new JScrollPane(ta);
		jsp.setBounds(100, 450, 1200, 400);
		jpanel.add(jsp);

		jpanel.add(btn1 = new RoundedButton("ÀÔ·Â"));
		btn1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btn1.setBackground(Color.getHSBColor(33, 84, 54));
		btn1.setForeground(Color.getHSBColor(0, 0, 30));
		btn1.setBounds(455, 353, 100, 50);

		jpanel.add(btn2 = new RoundedButton("Ãâ·Â"));
		btn2.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btn2.setBackground(Color.getHSBColor(33, 84, 54));
		btn2.setForeground(Color.getHSBColor(0, 0, 30));
		btn2.setBounds(950, 353, 100, 50);

		jpanel.add(btn3 = new RoundedButton("µÚ·Î"));
		btn3.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btn3.setBackground(Color.getHSBColor(33, 84, 54));
		btn3.setForeground(Color.getHSBColor(0, 0, 30));
		btn3.setBounds(250, 353, 100, 50);

		jpanel.add(btn5 = new RoundedButton("°Ë»ö"));
		btn5.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btn5.setBackground(Color.getHSBColor(33, 84, 54));
		btn5.setForeground(Color.getHSBColor(0, 0, 30));
		btn5.setBounds(800, 130, 100, 50);

		jpanel.add(btn4 = new RoundedButton("FTPÀÌµ¿"));
		btn4.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btn4.setBackground(Color.getHSBColor(33, 84, 54));
		btn4.setForeground(Color.getHSBColor(0, 0, 30));
		btn4.setBounds(1185, 353, 100, 50);

		DB dao = new DB();

		// È¸¿ø Ãß°¡
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ta.setText("");

				String Host = t1.getText();
				String Port = t2.getText();
				String Name = t3.getText();
				String Password = t4.getText();
				String Server = t5.getText();

				dao.insertMember(new FTP_INFO(Host, Port, Name, Password, Server));

				ta.append("ÀÔ·Â ¿Ï·á \n");

				t1.setText("");
				t2.setText("");
				t3.setText("");
				t4.setText("");
				t5.setText("");
				t6.setText("");
			}
		});

		// È¸¿ø ¸ñ·Ï Ãâ·Â
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ta.setText("");
				ArrayList<FTP_INFO> arr = new ArrayList<FTP_INFO>();
				arr = dao.readMember();
//				ta.append("\t" + "Host" + "\t" + "\t" +  "Port" + "\t" + "Name" + "\t" + "Password" + "\t" + "Server\n");
				ta.append("\t" + "\t" + "Host" + "\t" + "\t" + "\t" +  "Port" + "\t" + "\t" + "Name" + "\t" + "\t" + "Password" +  "\t" +  "\t"+ "Server\n");
				ta.append("\t" + "\t" +  "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

				for (int i = 0; i < arr.size(); i++) {
					ta.append("\t" + "\t" + arr.get(i).getHost() + " \t " + " \t " + "\t" + arr.get(i).getPort() + " \t "  + " \t "
							+ arr.get(i).getname() + " \t "  + " \t "+ arr.get(i).getpassword() + " \t " + " \t " + arr.get(i).getServer()  + " \t "
							+ " \t " + "\n");
				}
			}
		});

		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new sub2_page();
				jframe.setVisible(false);

			}
		});

		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new sub1_page();
				jframe.setVisible(false);

			}
		});

		// È¸¿ø °Ë»ö
		btn5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ta.setText("");
				String content = t4.getText();

				ArrayList<FTP_INFO> arr = new ArrayList<FTP_INFO>();
				arr = dao.searchMember(content);
				ta.append(" \n");

				ta.append("\t" + "\t" + "Host" + "\t" + "\t" + "\t" +  "Port" + "\t" + "\t" + "Name" + "\t" + "\t" + "Password" +  "\t" +  "\t"+ "Server\n");
				ta.append("\t"
						  +  "\t" + "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

				for (int i = 0; i < arr.size(); i++) {
					ta.append("\t" + "\t" + arr.get(i).getHost() + " \t " + " \t " + "\t" + arr.get(i).getPort() + " \t "  + " \t "
							+ arr.get(i).getname() + " \t "  + " \t "+ arr.get(i).getpassword() + " \t " + " \t " + arr.get(i).getServer()  + " \t "
							+ " \t " + "\n");
				}

				t1.setText("");
				t2.setText("");
				t3.setText("");
				t4.setText("");
				t5.setText("");
				t6.setText("");
			}
		});
	}
}

//¹öÆ° µðÀÚÀÎ
class RoundedButton extends JButton {

	public RoundedButton() {
		super();
		decorate();
	}

	public RoundedButton(String text) {
		super(text);
		decorate();
	}

	public RoundedButton(Action action) {
		super(action);
		decorate();
	}

	public RoundedButton(Icon icon) {
		super(icon);
		decorate();
	}

	public RoundedButton(String text, Icon icon) {
		super(text, icon);
		decorate();
	}

	protected void decorate() {
		setBorderPainted(false);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		Graphics2D graphics = (Graphics2D) g;

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (getModel().isArmed()) {
			graphics.setColor(getBackground().darker());
		} else if (getModel().isRollover()) {
			graphics.setColor(getBackground().brighter());
		} else {
			graphics.setColor(getBackground());
		}

		graphics.fillRoundRect(0, 0, width, height, 10, 10);

		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();

		int textX = (width - stringBounds.width) / 2;
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

		graphics.setColor(getForeground());
		graphics.setFont(getFont());
		graphics.drawString(getText(), textX, textY);
		graphics.dispose();

		super.paintComponent(g);
	}
}