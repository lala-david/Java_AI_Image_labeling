import java.io.*;

import javax.swing.*;

 
public class DownloadTask extends SwingWorker<Void, Void> {

	private static final int BUFFER_SIZE = 4096;
	
	private String host;
	private int port;
	private String username;
	private String password;
	
	private String Dpath;
	private String saveDir;
 
	
	public DownloadTask(String host, int port, String username,
			String password, String Dpath, String saveDir
			 ) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.Dpath = Dpath;
		this.saveDir = saveDir;
	 
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		FTPUtility util = new FTPUtility(host, port, username, password);
		try {
			util.connect();
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			long totalBytesRead = 0;
			int percentCompleted = 0;
			
			long fileSize = util.getFileSize(Dpath);
			 
			
			String fileName = new File(Dpath).getName();
			
			File downloadFile = new File(saveDir + File.separator + fileName);
			FileOutputStream outputStream = new FileOutputStream(downloadFile);
			
			util.downloadFile(Dpath);
			InputStream inputStream = util.getInputStream();
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
				percentCompleted = (int) (totalBytesRead * 100 / fileSize);
				setProgress(percentCompleted);
			}
			outputStream.close();	
			util.finish();
		} catch (FTPException ex) {
			JOptionPane.showMessageDialog(null, "에러: " + ex.getMessage(),
					"에러", JOptionPane.ERROR_MESSAGE);			
			ex.printStackTrace();
			setProgress(0);
			cancel(true);			
		} finally {
			util.disconnect();
		}
		return null;
	}
	@Override
	protected void done() {
		if (!isCancelled()) {
			JOptionPane.showMessageDialog(null,
					"파일 다운로드 성공!", "메서지",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}	
}
