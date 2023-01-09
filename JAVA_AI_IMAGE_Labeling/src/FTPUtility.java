
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import java.util.*;

public class FTPUtility {

	private String host;
	private int port;
	private String username;
	private String password;

	private FTPClient ftpClient = new FTPClient();
	private int replyCode;

	private OutputStream outputStream;

	private InputStream inputStream;

	public FTPUtility(String host, int port, String user, String pass) {
		this.host = host;
		this.port = port;
		this.username = user;
		this.password = pass;
	}

	//
	public void connect() throws FTPException {
		try {
			// FTP ���� ���� 
			ftpClient.connect(host, port);
			replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				throw new FTPException("FTP ������ ������ �ź��߽��ϴ�.");
			}

			boolean logged = ftpClient.login(username, password);
			if (!logged) {
				// failed to login
				ftpClient.disconnect();
				throw new FTPException("������ �α����� �� �����ϴ�.");
			}

			ftpClient.enterLocalPassiveMode();

		} catch (IOException ex) {
			throw new FTPException("I/O ����: " + ex.getMessage());
		}
	}

	public long getFileSize(String filePath) throws FTPException {
		try {
			// FTP ���� ���� 
			FTPFile file = ftpClient.mlistFile(filePath);
			if (file == null) {
				throw new FTPException("������ ������ �����ϴ�!");
			}
			return file.getSize();
		} catch (IOException ex) {
			throw new FTPException("������ ũ�⸦ ������ �� �����ϴ�: " + ex.getMessage());
		}
	}

	public void uploadFile(File uploadFile, String destDir) throws FTPException {
		try {
			// FTP ���ε� ���丮 ���� 
			boolean success = ftpClient.changeWorkingDirectory(destDir);
			if (!success) {
				throw new FTPException(
						"�۾� ���丮�� ����� ������ �� �����ϴ�. " + destDir + ". ���丮�� �������� ���� �� �ֽ��ϴ�.");
			}

			success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (!success) {
				throw new FTPException("���̳ʸ� ���� ������ ������ �� �����ϴ�.");
			}

			outputStream = ftpClient.storeFileStream(((File) uploadFile).getName());

		} catch (IOException ex) {
			throw new FTPException("���� ���ε� ����: " + ex.getMessage());
		}
	}

	public void downloadFile(String downloadPath) throws FTPException {
		try {
			// FTP �ٿ�ε� ���丮 ���� 

			boolean success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (!success) {
				throw new FTPException("���̳ʸ� ���� ������ ������ �� �����ϴ�.");
			}

			inputStream = ftpClient.retrieveFileStream(downloadPath);

			if (inputStream == null) {
				throw new FTPException("�Է� ��Ʈ���� �� �� �����ϴ�. ������ ������ ���� �� �ֽ��ϴ�.");
			}
		} catch (IOException ex) {
			throw new FTPException("���� �ٿ�ε� ����: " + ex.getMessage());
		}
	}

	public void writeFileBytes(byte[] bytes, int offset, int length) throws IOException {
		outputStream.write(bytes, offset, length);
	}

	public void finish() throws IOException {
		outputStream.close();
		ftpClient.completePendingCommand();
	}

	public void disconnect() throws FTPException {
		if (ftpClient.isConnected()) {
			try {
				if (!ftpClient.logout()) {
					throw new FTPException("�������� �α׾ƿ��� �� �����ϴ�.");
				}
				ftpClient.disconnect();
			} catch (IOException ex) {
				throw new FTPException("�������� ���� ����: " + ex.getMessage());
			}
		}
	}
	public InputStream getInputStream() {
		return inputStream;
	}
}