package com.pramati.poi.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.pramati.bean.MessageData;

public interface POIExcelService {
	public void makeWorkBook(List<MessageData> mailList, Workbook workbook, String fileName);
	
	public HSSFSheet addNewSheet(HSSFWorkbook workBook,String sheetName);
	
	public void addRow(HSSFSheet sheet,MessageData msgData,int rowNum);
	
	public void addHeaderRow(HSSFSheet sheet);
	
	public void addCell(Row row);
	
	
}
