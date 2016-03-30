package com.pramati.poi.service.impl;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.pramati.bean.MessageData;
import com.pramati.poi.service.POIExcelService;

public class POIExcelServiceImpl implements POIExcelService{
	@Override
	public void makeWorkBook(List<MessageData> mailList, Workbook workbook, String fileName) {
		if (null == mailList || mailList.isEmpty()) {
			return;
		}
		
		POIExcelService excelService = new POIExcelServiceImpl();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(fileName);
		excelService.addHeaderRow(sheet);
		int rowNum = 1;
		for (MessageData messageData : mailList) {
			excelService.addRow(sheet, messageData, rowNum++);

		}

	}

	@Override
	public HSSFSheet addNewSheet(HSSFWorkbook workBook, String sheetName) {
		
		if(null == sheetName || sheetName.trim().length() <= 0) {
			return null;
		}
		return workBook.createSheet(sheetName);
	}

	@Override
	public void addRow(HSSFSheet sheet, MessageData msgData,int rowNum) {
		if(null != sheet && null != msgData ) {
			HSSFRow row = sheet.createRow(rowNum);
			HSSFCell column1 = row.createCell(0);
			column1.setCellValue(msgData.getAuthor());
			
			HSSFCell column2 = row.createCell(1);
			column2.setCellValue(msgData.getUrl());
			
			HSSFCell column3 = row.createCell(2);
			column3.setCellValue(msgData.getDate());
		}
		
		
	}

	@Override
	public void addCell(Row row) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHeaderRow(HSSFSheet sheet) {
		if(sheet != null) {
			HSSFRow row = sheet.createRow(0);
			HSSFCell column1 = row.createCell(0);
			column1.setCellValue("Author");
			
			HSSFCell column2 = row.createCell(1);
			column2.setCellValue("URL");
			
			HSSFCell column3 = row.createCell(2);
			column3.setCellValue("Date");
		}		
	}

}
