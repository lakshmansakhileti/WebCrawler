package com.pramati.poi.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

import com.pramati.poi.service.POIDownloader;

public class ExcelDownloaderImpl implements POIDownloader {

	private Workbook workbook;
	
	public ExcelDownloaderImpl(Workbook workbook) {
		this.workbook = workbook;
	}
	

	private void downloadExcel(Workbook workbook, String fileName) throws FileNotFoundException {
		FileOutputStream out = new FileOutputStream(new File(fileName));
		try {
			workbook.write(out);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void downloadFile(String fileName) throws FileNotFoundException {
		downloadExcel(this.workbook, fileName);
	}

}
