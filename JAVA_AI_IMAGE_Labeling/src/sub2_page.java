import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.event.*;

public class sub2_page extends JFrame {
	sub2_page() {
		setTitle("FTP");
		
 
		JLabel Host = new JLabel("Host:");
		JLabel Port = new JLabel("Port:");
		JLabel Username = new JLabel("사용자이름:");
		JLabel Password = new JLabel("비밀번호:");
		JLabel UpPath = new JLabel("업로드 경로:");
		JLabel DownPath = new JLabel("다운로드 경로: ");
 
		JTextField THost = new JTextField(40);
		JTextField TPort = new JTextField(5);
		JTextField TUsername = new JTextField(30);
		JPasswordField TPassword = new JPasswordField(30);
		JTextField TPath = new JTextField(30);
		JTextField DPath = new JTextField(30);
 
		JFilePicker filePicker = new JFilePicker("선택: ", "브라우저");
 
		JButton buttonUpload = new JButton("업로드");
		JButton buttonDownload = new JButton("다운로드");
		JButton buttonBack = new JButton("뒤로");
		JFilePicker filePicker2 = new JFilePicker("저장: ", "브라우저");
		filePicker.setMode(JFilePicker.MODE_OPEN);

		// 파일 선택
		filePicker2.setMode(JFilePicker.MODE_SAVE);
		filePicker2.getFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		Container c = getContentPane();
		setLocationRelativeTo(null);
		c.setBackground(Color.getHSBColor(0, 42, 42));
		// getHSBColor(0, 42, 42)
		setSize(1400, 900);

		setLayout(new GridBagLayout());
		setVisible(true); 
		GridBagConstraints g = new GridBagConstraints();
 
		g.anchor = GridBagConstraints.WEST;
		g.insets = new Insets(5, 5, 5, 5);

		// IP
		g.gridx = 0;
		g.gridy = 0;
		add(Host, g);

		g.gridx = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		g.weightx = 1.0;
		add(THost, g);

		g.gridx = 0;
		g.gridy = 1;
		add(Port, g);

		g.gridx = 1;
		add(TPort, g);

		g.gridx = 0;
		g.gridy = 2;
		add(Username, g);

		g.gridx = 1;
		add(TUsername, g);

		g.gridx = 0;
		g.gridy = 3;
		add(Password, g);

		g.gridx = 1;
		add(TPassword, g);

		g.gridx = 0;
		g.gridy = 4;
		add(UpPath, g);

		g.gridx = 1;
		add(TPath, g);

		g.gridx = 0;
		g.gridy = 5;
		add(DownPath, g);

		g.gridx = 1;
		add(DPath, g);

		g.gridx = 0;
		g.gridwidth = 2;
		g.gridy = 6;
		g.anchor = GridBagConstraints.WEST;
		add(filePicker, g);

		g.gridx = 0;
		g.gridwidth = 2;
		g.gridy = 7; // 10
		g.anchor = GridBagConstraints.WEST;
		add(filePicker2, g);

		g.gridx = 1;
		g.gridy = 8;
		g.anchor = GridBagConstraints.EAST;
		g.fill = GridBagConstraints.NONE;
		add(buttonUpload, g);

		g.gridx = 1;
		g.gridy = 8; // 6
		g.anchor = GridBagConstraints.CENTER;
		g.fill = GridBagConstraints.NONE;
		add(buttonDownload, g);
	 
		
		g.gridx = 0;
		g.gridy = 8;
		g.anchor = GridBagConstraints.WEST;
		g.fill = GridBagConstraints.NONE;
		add(buttonBack, g);
 
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new FTP_LIST();
				setVisible(false);

			}
		});
		
		buttonDownload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				buttonDownloadActionPerformed(event);
			}

			private void buttonDownloadActionPerformed(ActionEvent event) {
				String host = THost.getText();
				int port = Integer.parseInt(TPort.getText());
				String username = TUsername.getText();
				String password = new String(TPassword.getPassword());
				String downloadPath = DPath.getText();
				String saveDir = filePicker2.getSelectedFilePath();

				DownloadTask task = new DownloadTask(host, port, username, password, downloadPath, saveDir);

				task.execute();
			}

		});

		buttonUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonUploadActionPerformed(e);
			}

			private void buttonUploadActionPerformed(ActionEvent e) {
				String host = THost.getText();
				int port = Integer.parseInt(TPort.getText());
				String username = TUsername.getText();
				String password = new String(TPassword.getPassword());
				String uploadPath = TPath.getText();
				String filePath = filePicker.getSelectedFilePath();

				File uploadFile = new File(filePath);

				UploadTask task = new UploadTask(host, port, username, password, uploadPath, uploadFile);

				task.execute();
			}
		});

	}
}


