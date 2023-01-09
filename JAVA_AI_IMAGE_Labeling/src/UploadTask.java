
import java.awt.event.ActionListener;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class UploadTask extends SwingWorker<Void, Void> {
	private static final int BUFFER_SIZE = 4096;

	private String host;
	private int port;
	private String username;
	private String password;

	private String destDir;
	private File uploadFile;

	public UploadTask(String host, int port, String username, String password, String destDir, File uploadFile) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.destDir = destDir;
		this.uploadFile = uploadFile;

	}

	/**
	 * Executed in background thread
	 */
	@Override
	protected Void doInBackground() throws Exception {
		FTPUtility util = new FTPUtility(host, port, username, password);
		try {
			util.connect();
			util.uploadFile(uploadFile, destDir);

			FileInputStream inputStream = new FileInputStream(uploadFile);

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			long totalBytesRead = 0;
			int percentCompleted = 0;
			long fileSize = uploadFile.length();

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				util.writeFileBytes(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
				percentCompleted = (int) (totalBytesRead * 100 / fileSize);
				setProgress(percentCompleted);
			}

			inputStream.close();

			util.finish();
		} catch (FTPException ex) {
			JOptionPane.showMessageDialog(null, "에러: " + ex.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "파일 업로드 성공", "메세지", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}