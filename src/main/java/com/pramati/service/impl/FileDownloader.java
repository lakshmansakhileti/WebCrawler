package com.pramati.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.pramati.utility.HttpDownloadUtility;

public class FileDownloader implements Runnable {
	private static Logger logger = Logger.getLogger(FileDownloader.class);
	private String sourceUrl;
	private String destinationPath;
	
	FileDownloader() {
		
	}
	
	public FileDownloader(String sourceUrl, String destinationPath) {
		super();
		this.sourceUrl = sourceUrl;
		this.destinationPath = destinationPath;
	}


	public void run() {
		try {
			//Downloads file on specified destination path from given source URL.
			logger.info("Source url :"+sourceUrl);
			logger.info("Destination url : "+destinationPath);
			HttpDownloadUtility.downloadFile(sourceUrl, destinationPath);
			
		} catch (IOException ex) {
			logger.error("Problem while downloading file of url :"+sourceUrl,ex);
		}
	}

}
