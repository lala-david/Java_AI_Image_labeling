import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

class Button_Add extends JFrame{
public String[] Button_Add(){
      String B_add1[]=new String[10];
      for(int i=0;i<10;i++) {
          
           String B_add2 = JOptionPane.showInputDialog("�Է��ϼ���.(�ִ� 10��)");
           B_add1[i]=B_add2;
           int answer = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "confirm",JOptionPane.YES_NO_OPTION );
           if(answer == JOptionPane.YES_OPTION){
              //����ڰ� yes�� ������ ��
              System.out.println("���α׷��� �����մϴ�.");
              break;
           } else{
           
              continue;
           }
       }
       return B_add1;
   }
}

//route, input--> ���� ����, ���� ��� �Է¹���

class route extends JFrame {
   public String route() {
      String strroute = JOptionPane.showInputDialog("���� ��θ� �Է����ּ���: ");
      String path=strroute;
      String message=String.format("�Է��Ͻ� ��δ� %s�Դϴ�.", path);
      JOptionPane.showMessageDialog(null, message);
      return path;
   }
}

class input extends JFrame {
   public int input() {
      String inputnum= JOptionPane.showInputDialog("���� ������ �Է����ּ���: ");
      int num=Integer.parseInt(inputnum); //string->int
      String message=String.format("�Է��Ͻ� ������ %d�Դϴ�.", num);
      JOptionPane.showMessageDialog(null, message);
      return num;
   }
}
class CsvAddToFile {
	public static void saveCsvFile(String imgFileName, String imgTag) {
		// �������ϰ��
		  String fileName = "C:\\Users\\User\\Desktop\\zero\\20221102\\Data.csv";
		  // file ��ü ����
		  File inputFile = new File(fileName);
		  File outputFile = new File(fileName+".temp");

		  FileInputStream fileInputStream = null;
		  BufferedReader bufferedReader = null;
		  FileOutputStream fileOutputStream = null;
		  BufferedWriter bufferedWriter = null;

		  boolean result = false;
		  
		  try {
		   // FileInputStream,FileOutputStream, BufferdReader, BufferedWriter
		   // ����
		   fileInputStream = new FileInputStream(inputFile);
		   fileOutputStream = new FileOutputStream(outputFile);

		   bufferedReader = new BufferedReader(new InputStreamReader(
		     fileInputStream));
		   bufferedWriter = new BufferedWriter(new OutputStreamWriter(
		     fileOutputStream));

		   // ���� ���Ͽ��� �о� ���̴� �Ѷ���
		   String line;
		   // ���Ͽ� ��ġ�ϴ� ���ڷ� ��ü�ϰ� �� ���� string
		   String repLine;

		   // �ٲٰ��� �ϴ� string�� �ٲ� string ����
		   String originalString =" ";
		   String replaceString = imgTag;

		   // ���� ���Ͽ��� �Ѷ��ξ� �д´�.
		   while ((line = bufferedReader.readLine()) != null) {
			   if (line.contains(imgFileName))
			   {
				    // ��ġ�ϴ� ���Ͽ����� �ٲ� ���ڷ� ��ȯ
				    // repLine = line.replaceAll(originalString, replaceString);
					repLine = line+replaceString;
			   }
			   else
				   repLine = line;

		    // ���ο� ���Ͽ� ����.
		    bufferedWriter.write(repLine, 0, repLine.length());
		    bufferedWriter.newLine();
		   }
		   // ���������� ����Ǿ����� �˸��� flag
		   result = true;
		  } catch (IOException ex) {
		   ex.printStackTrace();
		  } finally {
		   // ���ҽ� ����. ���������� �����Ѵ�.
		   try {
		    bufferedReader.close();
		   } catch (IOException ex1) {
		    ex1.printStackTrace();
		   }
		   try {
		    bufferedWriter.close();
		   } catch (IOException ex2) {
		    ex2.printStackTrace();
		   }

		   // ���������� ����Ǿ��� ��� ���� ������ ����� ���ο� ���ϸ��� �������ϸ����� rename�Ѵ�.
		   if (result) {
		    inputFile.delete();
		    outputFile.renameTo(new File(fileName));
		   }
		}
	}
}
   
public class Gallery extends JFrame implements ActionListener, Runnable{
    JTextField path;
    Canvas can;
    JPanel south;
    JButton prev, next, stop, auto, delete;
    JTextField interval;
    route rr=new route(); //�̹��� ��ΰ�
    input ii=new input(); //�̹��� ������
    

    //�ڹٿ��� �̹����� toolkit�� ���� ����ȴ�!!
    Toolkit kit=Toolkit.getDefaultToolkit();
    Image img;
    String url =rr.route();//���纸���ִ� �̹����� ��� ����
    int number=ii.input();
    //��ư ���������� ������ ��ΰ�. 
    public static int count =0;
    
    //���������� ���� Vector�� ������.
    public static Vector<String> list = new Vector<String>();
    
    //������ ����
    Thread thread =new Thread(this); 
    
    boolean flag = true;//�ݺ����� �����Ҽ� �ִ� ���ذ�, true����, false�����
    
    public Gallery(){
        path=new JTextField();  
        south = new JPanel();
        prev = new RoundedButton("��");
		next = new RoundedButton("��");
		stop = new RoundedButton("��");
		auto = new RoundedButton("���� ���� �߰��ϱ�");
        delete = new RoundedButton("����");
        interval = new JTextField(3);
        interval.setText("1000");
        
//        JCheckBox button[];//��ư ���� �迭
        
        
        photoData(); /*�̹����� ���� �غ�Ǿ�� ������ �Ѱ��� �����̶� �������Ƿ�
                    ���� photodata(����)�� �ҷ��Դ�!*/        
        //���� Vector�� ���ʸ�<>����STring������ �����߱� ������ String���� ����ȯ �ʿ�����!
        path.setText((String)list.get(0));  
        img = kit.getImage((String)list.get(0));
        path.setText(list.get(0));  
        img = kit.getImage(list.get(0));
        can = new Canvas(){
         public void paint(Graphics g){
        	 g.drawImage(img, 45, 50, 1300, 750, this); //�������� Canvas�ε�. ���� �����͸��̴ϱ� this�� canvas�ڽ��� �ǰڳ�.
         }
        };  
        
        
        add(path, BorderLayout.NORTH);
        add(can);
        south.add(prev);
        south.add(interval);
        south.add(next);
        south.add(stop);
        south.add(auto);
        south.add(delete);
        
        add(south, BorderLayout.SOUTH);
        
        //��ư�� �׼Ǹ�����
        prev.addActionListener(this);  
        next.addActionListener(this);
        stop.addActionListener(this);
        auto.addActionListener(this);
        delete.addActionListener(this);
        
        setTitle("������ ���� ���α׷�"); // Ÿ��Ʋ
		setSize(1400, 900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true); // �������� Ÿ��Ʋ�ٸ� ����
		Container contentPane = getContentPane();
		setLocationRelativeTo(null);
		contentPane.setBackground(Color.getHSBColor(0, 42, 42)); // ������
		setVisible(true); 
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object obj = ae.getSource();
        if(obj.equals(prev)){
           prevNext();
        }
        
        if(obj.equals(next)){
           showNext();
        }
        
        if(obj.equals(auto)){
           String tmp[]=new String[10];//���� ���� �迭
           Button_Add add=new Button_Add();
           tmp=add.Button_Add();//�迭 ����
           Add_Button(tmp);

        }
        if(obj.equals(stop)){
           flag=false;
        } 
        if(obj.equals(delete)) {
        	try {
        		
        		Path OriginalFile = Paths.get(list.get(count));
        		String newPath = list.get(count).replace("20221102", "Delete");
        		Path DeletedFile = Paths.get(newPath);
        		Files.move(OriginalFile, DeletedFile, StandardCopyOption.ATOMIC_MOVE);
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
    }
     
    //��ư �߰� �޼��� 
    public void Add_Button(String arr[]) { //���� ���� �迭�� ���ڷ� ����
       int len=0;
       for(String str:arr) {
          if(str==null) {
             break;
          }
          len++;
       }
       //int x=50;
       JCheckBox button[]= new JCheckBox[len];
       int index=0;
       for(JCheckBox btn: button) {
          btn = new JCheckBox(arr[index++]);
          south.add(btn);
          btn.addItemListener(new ItemListener()
          {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String path = list.get(count);
				int lastSlashIndex = path.lastIndexOf("\\");
				int dotIndex = path.indexOf(".");
				
				String imgFileName = path.substring(lastSlashIndex + 1, dotIndex);
				JCheckBox button = (JCheckBox)e.getItem();
				CsvAddToFile.saveCsvFile(imgFileName, button.getText());
			}
          });
          //x=+100;
       }
       this.refreshProcess();
    }
    
    
    /*�������� ������ �̹��� ���� ���(�迭���� ���ͷΰ���!)*/
    public void photoData(){
     //�츮 ������ 1~10���� ��Ģ�� �����ϱ� for�� ������..
       String a = url;
       for(int i=1;i<=number;i++){
          list.add(a + i + ".jpg");
       }
    } 
    
    public void itemPerformed(ItemEvent e) {
    	String[] btn = null;
		int i=0;
		if(btn[i] == "���������") {
			CsvAddToFile();
		}
    }
    private void CsvAddToFile() {
		// TODO Auto-generated method stub
		
	}


	/*���� ���� �����ֱ�(prev��ư)*/
    public void prevNext(){
       count--;
     if(count<0){
         count=0; //-1�� �� ī��Ʈ ���� �ٽ� ���� ����
         JOptionPane.showMessageDialog(getParent(), "ó�������Դϴ�");
     }
     else{
         img=kit.getImage(list.get(count));
         path.setText(list.get(count));
         can.repaint();
        }
     
    }
    
    /* ���� ������ �����ش�. (next��ư)*/
    public void showNext(){
        count++;
      
        if(count>=list.size()){ //���� ���� ������ ������ �Ѿ�� ��
            count=list.size();
            JOptionPane.showMessageDialog(getParent(), "�������Դϴ�.");
            flag=false;
        }else{
           img=kit.getImage(list.get(count));
           path.setText(list.get(count));
           can.repaint();
        }
    }
 
     //����� ��� ���ư� �޼��� ���� 
    //������ ���� run�޼���
     public void run(){
         while(flag){ //�ѹ��� �ϰ� ������ ���� �Ŵϱ� while�� �����߰���?
             try {
                thread.sleep(Integer.parseInt(interval.getText()));    
             } catch (InterruptedException e) {
                e.printStackTrace();
             }
             showNext();
         }
     }
   
   
    public static void main(String[] args) {
     new Gallery();
     }
    private void refreshProcess() {
       this.invalidate();
       this.validate();
       this.repaint();
    }
}

//��ư ������
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