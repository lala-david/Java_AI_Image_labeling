
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
			// FTP 연결 예외 
			ftpClient.connect(host, port);
			replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				throw new FTPException("FTP 서버가 연결을 거부했습니다.");
			}

			boolean logged = ftpClient.login(username, password);
			if (!logged) {
				// failed to login
				ftpClient.disconnect();
				throw new FTPException("서버에 로그인할 수 없습니다.");
			}

			ftpClient.enterLocalPassiveMode();

		} catch (IOException ex) {
			throw new FTPException("I/O 에러: " + ex.getMessage());
		}
	}

	public long getFileSize(String filePath) throws FTPException {
		try {
			// FTP 파일 예외 
			FTPFile file = ftpClient.mlistFile(filePath);
			if (file == null) {
				throw new FTPException("파일이 서버에 없습니다!");
			}
			return file.getSize();
		} catch (IOException ex) {
			throw new FTPException("파일의 크기를 결정할 수 없습니다: " + ex.getMessage());
		}
	}

	public void uploadFile(File uploadFile, String destDir) throws FTPException {
		try {
			// FTP 업로드 디렉토리 예외 
			boolean success = ftpClient.changeWorkingDirectory(destDir);
			if (!success) {
				throw new FTPException(
						"작업 디렉토리를 여기로 변경할 수 없습니다. " + destDir + ". 디렉토리가 존재하지 않을 수 있습니다.");
			}

			success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (!success) {
				throw new FTPException("바이너리 파일 형식을 설정할 수 없습니다.");
			}

			outputStream = ftpClient.storeFileStream(((File) uploadFile).getName());

		} catch (IOException ex) {
			throw new FTPException("파일 업로드 오류: " + ex.getMessage());
		}
	}

	public void downloadFile(String downloadPath) throws FTPException {
		try {
			// FTP 다운로드 디렉토리 예외 

			boolean success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (!success) {
				throw new FTPException("바이너리 파일 형식을 설정할 수 없습니다.");
			}

			inputStream = ftpClient.retrieveFileStream(downloadPath);

			if (inputStream == null) {
				throw new FTPException("입력 스트림을 열 수 없습니다. 파일이 서버에 없을 수 있습니다.");
			}
		} catch (IOException ex) {
			throw new FTPException("파일 다운로드 오류: " + ex.getMessage());
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
					throw new FTPException("서버에서 로그아웃할 수 없습니다.");
				}
				ftpClient.disconnect();
			} catch (IOException ex) {
				throw new FTPException("서버에서 연결 오류: " + ex.getMessage());
			}
		}
	}
	public InputStream getInputStream() {
		return inputStream;
	}
}