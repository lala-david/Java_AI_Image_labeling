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
          
           String B_add2 = JOptionPane.showInputDialog("입력하세요.(최대 10개)");
           B_add1[i]=B_add2;
           int answer = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "confirm",JOptionPane.YES_NO_OPTION );
           if(answer == JOptionPane.YES_OPTION){
              //사용자가 yes를 눌렀을 떄
              System.out.println("프로그램을 종료합니다.");
              break;
           } else{
           
              continue;
           }
       }
       return B_add1;
   }
}

//route, input--> 사진 개수, 사진 경로 입력받음

class route extends JFrame {
   public String route() {
      String strroute = JOptionPane.showInputDialog("사진 경로를 입력해주세요: ");
      String path=strroute;
      String message=String.format("입력하신 경로는 %s입니다.", path);
      JOptionPane.showMessageDialog(null, message);
      return path;
   }
}

class input extends JFrame {
   public int input() {
      String inputnum= JOptionPane.showInputDialog("사진 갯수를 입력해주세요: ");
      int num=Integer.parseInt(inputnum); //string->int
      String message=String.format("입력하신 갯수는 %d입니다.", num);
      JOptionPane.showMessageDialog(null, message);
      return num;
   }
}
class CsvAddToFile {
	public static void saveCsvFile(String imgFileName, String imgTag) {
		// 원본파일경로
		  String fileName = "C:\\Users\\User\\Desktop\\zero\\20221102\\Data.csv";
		  // file 객체 생성
		  File inputFile = new File(fileName);
		  File outputFile = new File(fileName+".temp");

		  FileInputStream fileInputStream = null;
		  BufferedReader bufferedReader = null;
		  FileOutputStream fileOutputStream = null;
		  BufferedWriter bufferedWriter = null;

		  boolean result = false;
		  
		  try {
		   // FileInputStream,FileOutputStream, BufferdReader, BufferedWriter
		   // 생성
		   fileInputStream = new FileInputStream(inputFile);
		   fileOutputStream = new FileOutputStream(outputFile);

		   bufferedReader = new BufferedReader(new InputStreamReader(
		     fileInputStream));
		   bufferedWriter = new BufferedWriter(new OutputStreamWriter(
		     fileOutputStream));

		   // 원본 파일에서 읽어 들이는 한라인
		   String line;
		   // 패턴에 일치하는 문자로 대체하고 난 후의 string
		   String repLine;

		   // 바꾸고자 하는 string과 바꿀 string 정의
		   String originalString =" ";
		   String replaceString = imgTag;

		   // 원본 파일에서 한라인씩 읽는다.
		   while ((line = bufferedReader.readLine()) != null) {
			   if (line.contains(imgFileName))
			   {
				    // 일치하는 패턴에서는 바꿀 문자로 변환
				    // repLine = line.replaceAll(originalString, replaceString);
					repLine = line+replaceString;
			   }
			   else
				   repLine = line;

		    // 새로운 파일에 쓴다.
		    bufferedWriter.write(repLine, 0, repLine.length());
		    bufferedWriter.newLine();
		   }
		   // 정상적으로 수행되었음을 알리는 flag
		   result = true;
		  } catch (IOException ex) {
		   ex.printStackTrace();
		  } finally {
		   // 리소스 해제. 개별적으로 해제한다.
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

		   // 정상적으로 수행되었을 경우 원본 파일을 지우고 새로운 파일명을 원본파일명으로 rename한다.
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
    route rr=new route(); //이미지 경로값
    input ii=new input(); //이미지 갯수값
    

    //자바에서 이미지는 toolkit을 통해 제어된다!!
    Toolkit kit=Toolkit.getDefaultToolkit();
    Image img;
    String url =rr.route();//현재보고있는 이미지의 경로 정보
    int number=ii.input();
    //버튼 누를때마다 증가될 경로값. 
    public static int count =0;
    
    //사진정보를 담을 Vector를 만들자.
    public static Vector<String> list = new Vector<String>();
    
    //쓰레드 관련
    Thread thread =new Thread(this); 
    
    boolean flag = true;//반복문을 제어할수 있는 기준값, true실행, false비실행
    
    public Gallery(){
        path=new JTextField();  
        south = new JPanel();
        prev = new RoundedButton("◀");
		next = new RoundedButton("▶");
		stop = new RoundedButton("■");
		auto = new RoundedButton("정제 사유 추가하기");
        delete = new RoundedButton("삭제");
        interval = new JTextField(3);
        interval.setText("1000");
        
//        JCheckBox button[];//버튼 생성 배열
        
        
        photoData(); /*이미지가 먼저 준비되어야 최초의 한개의 사진이라도 보여지므로
                    먼저 photodata(백터)를 불러왔다!*/        
        //위에 Vector를 제너릭<>으로STring형으로 선언했기 때문에 String으로 형변환 필요없어요!
        path.setText((String)list.get(0));  
        img = kit.getImage((String)list.get(0));
        path.setText(list.get(0));  
        img = kit.getImage(list.get(0));
        can = new Canvas(){
         public void paint(Graphics g){
        	 g.drawImage(img, 45, 50, 1300, 750, this); //옵져버는 Canvas인데. 여기 내부익명이니까 this는 canvas자신이 되겠네.
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
        
        //버튼에 액션리스너
        prev.addActionListener(this);  
        next.addActionListener(this);
        stop.addActionListener(this);
        auto.addActionListener(this);
        delete.addActionListener(this);
        
        setTitle("쓰레기 정제 프로그램"); // 타이틀
		setSize(1400, 900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true); // 프레임의 타이틀바를 없앰
		Container contentPane = getContentPane();
		setLocationRelativeTo(null);
		contentPane.setBackground(Color.getHSBColor(0, 42, 42)); // 배경색상
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
           String tmp[]=new String[10];//사유 저장 배열
           Button_Add add=new Button_Add();
           tmp=add.Button_Add();//배열 생성
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
     
    //버튼 추가 메서드 
    public void Add_Button(String arr[]) { //사유 저장 배열을 인자로 받음
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
    
    
    /*갤러리에 보여질 이미지 정보 담기(배열말고 백터로가자!)*/
    public void photoData(){
     //우리 사진은 1~10까지 규칙이 있으니까 for로 돌리자..
       String a = url;
       for(int i=1;i<=number;i++){
          list.add(a + i + ".jpg");
       }
    } 
    
    public void itemPerformed(ItemEvent e) {
    	String[] btn = null;
		int i=0;
		if(btn[i] == "쓰레기없음") {
			CsvAddToFile();
		}
    }
    private void CsvAddToFile() {
		// TODO Auto-generated method stub
		
	}


	/*이전 사진 보여주기(prev버튼)*/
    public void prevNext(){
       count--;
     if(count<0){
         count=0; //-1로 된 카운트 값을 다시 정상 복귀
         JOptionPane.showMessageDialog(getParent(), "처음사진입니다");
     }
     else{
         img=kit.getImage(list.get(count));
         path.setText(list.get(count));
         can.repaint();
        }
     
    }
    
    /* 다음 사진을 보여준다. (next버튼)*/
    public void showNext(){
        count++;
      
        if(count>=list.size()){ //내가 가진 사진의 갯수를 넘어서면 욕
            count=list.size();
            JOptionPane.showMessageDialog(getParent(), "마지막입니다.");
            flag=false;
        }else{
           img=kit.getImage(list.get(count));
           path.setText(list.get(count));
           can.repaint();
        }
    }
 
     //오토로 계속 돌아갈 메서드 정의 
    //쓰레드 관련 run메서드
     public void run(){
         while(flag){ //한번만 하고 끝나지 않을 거니까 while문 돌려야겠죠?
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