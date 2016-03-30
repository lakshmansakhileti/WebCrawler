package com.pramati.poi.service;

import java.io.FileNotFoundException;

public interface POIDownloader {
	
	public void downloadFile(String fileName) throws FileNotFoundException;
	
	
}
