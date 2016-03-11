package com.pramati.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * A utility that downloads a file from a URL.
 * 
 * @author lakshman
 *
 */
public class HttpDownloadUtility {

	public static Logger logger = Logger.getLogger(HttpDownloadUtility.class);
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Downloads a file from a URL
	 * 
	 * @param fileUrl
	 *            HTTP URL of the file to be download
	 * @param destinationPath
	 *            path of the directory to save the file
	 * @throws IOException
	 */
	public static Boolean downloadFile(String fileUrl, String destinationPath) throws IOException {
		URL url = null;
		HttpURLConnection httpConn = null;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;

		try {
			url = new URL(fileUrl);
			httpConn = (HttpURLConnection) url.openConnection();

			if (null == httpConn) {
				logger.error("Http Connect is null :" + fileUrl);
				return Boolean.FALSE;
			}

			int responseCode = httpConn.getResponseCode();

			// always check HTTP response code first
			if (responseCode == HttpURLConnection.HTTP_OK) {

				// opens input stream from the HTTP connection
				
				inputStream = httpConn.getInputStream();

				// String saveFilePath = destinationPath + File.separator +
				// fileName;
				if (null == inputStream) {
					logger.error("InputStream is null :" + fileUrl);
					return Boolean.FALSE;
				}
				// opens an output stream to save into file
				outputStream = new FileOutputStream(destinationPath);

				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				return Boolean.TRUE;
			} else {
				logger.info("No file to download. Server replied HTTP code: " + responseCode);
				return Boolean.FALSE;
			}

		} catch (MalformedURLException malEx) {
			logger.error("Problem while downloading file ", malEx);
			return Boolean.FALSE;
		} catch (IOException ioEx) {
			logger.error("Problem while downloading file ", ioEx);
			return Boolean.FALSE;
		} finally {
			inputStream.close();
			outputStream.close();
			httpConn.disconnect();
		}

	}
}
