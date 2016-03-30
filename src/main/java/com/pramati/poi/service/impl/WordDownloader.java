package com.pramati.poi.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.pramati.poi.service.POIDownloader;


public class WordDownloader implements POIDownloader {
	public static Logger logger = Logger.getLogger(WordDownloader.class);
	XWPFDocument doc;
	public WordDownloader(XWPFDocument doc) {
		
		this.doc = doc;
	}
	@Override
	public void downloadFile(String fileName) throws FileNotFoundException {
		downloadWordFile(this.doc,fileName);
	}
	
	
	private void downloadWordFile(XWPFDocument doc, String path) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			doc.write(out);
		} catch (FileNotFoundException e) {
			logger.error("Exception while writing mail info into word document", e);
		} catch (IOException e) {
			logger.error("Exception while writing mail info into word document", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error("Problem while closing stream", e);
				e.printStackTrace();
			}
		}

	}
}
