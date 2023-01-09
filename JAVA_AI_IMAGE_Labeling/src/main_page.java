import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class main_page extends JFrame { // 메인 작업 창
	static RoundedButton b1, b1_;

	main_page() {
		setTitle("쓰레기 정제 프로그램"); // 타이틀
		setSize(1400, 900); // 처음 창 뜰떄 크기
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true); // 프레임의 타이틀바를 없앰
		Container contentPane = getContentPane();
		contentPane.setLayout(null); // 컨텐트팬의 배치관리자 제거
		setLocationRelativeTo(null);
		contentPane.setBackground(Color.getHSBColor(0, 42, 42)); // 배경색상

		// 상단바 디자인
		JLabel la_ = new JLabel("쓰레기 정제 프로그램");

		la_.setLocation(5, 0); // la를 (130,50) 위치로 지정
		la_.setSize(700, 55); // la를 500, 35 크기로 지정
		la_.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		la_.setForeground(Color.getHSBColor(221, 160, 221)); // 글씨 색상
		contentPane.add(la_); // la를 컨텐트팬에 부착

		b1_ = new RoundedButton("X");
		b1_.setBounds(1325, 0, 75, 75); // 가로, 세로, 버튼 가로, 버튼 세로
		b1_.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		b1_.setBackground(Color.getHSBColor(33, 84, 54));
		b1_.setForeground(Color.getHSBColor(0, 0, 30));
		add(b1_);

		b1_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		// 글씨 삽입
		JLabel la = new JLabel("정제를 시작하시겠습니까?");

		la.setLocation(400, 270); // la를 (130,50) 위치로 지정
		la.setSize(700, 55); // la를 500, 35 크기로 지정
		la.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		la.setForeground(Color.getHSBColor(221, 160, 221)); // 글씨 색상
		contentPane.add(la); // la를 컨텐트팬에 부착

		// 버튼 만들기
		b1 = new RoundedButton("시작");
		b1.setBounds(600, 550, 200, 100); // 가로, 세로, 버튼 가로, 버튼 세로
		b1.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		b1.setBackground(Color.getHSBColor(33, 84, 54));
		b1.setForeground(Color.getHSBColor(0, 0, 30));
		add(b1);
		setVisible(true); // 화면에 창을 띄움

		// 마우스 클릭 이벤트
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new sub1_page();
				setVisible(false);

			}
		});

	}

}

//버튼 디자인
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

		graphics.fillRoundRect(0, 0, width, height, 50, 50);

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
