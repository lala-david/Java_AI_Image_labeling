import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
public class image_Panel extends JPanel {  
	private Image img;
	  
	  public image_Panel(Image img) {
	      this.img = img;
	      setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);
	  }
	  
	  public void paintComponent(Graphics g) {
	      g.drawImage(img, 3, 0, null);
	  }
}
