package com.pramati.poi.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.pramati.bean.MessageData;

public class POIWordServiceImpl {
	
	public static Logger logger = Logger.getLogger(POIWordServiceImpl.class);
	
	public void addToTable(XWPFTable table, List<MessageData> mailList){
		if(null == table || null == mailList || mailList.isEmpty()) {
			return ;
		}
		for (MessageData messageData : mailList) {
			addRow(table,messageData);
		}
		
	}
	
	public void addRow(XWPFTable table, MessageData msgData) {
		if(table == null || msgData == null) {
			return;
		}
		XWPFTableRow row = table.createRow();
		
		XWPFTableCell cell1 = row.getCell(0);
		cell1.setText(msgData.getAuthor());
		XWPFTableCell cell2 = row.getCell(1);
		cell2.setText(msgData.getUrl());
		XWPFTableCell cell3 = row.getCell(2);
		cell3.setText(msgData.getDate());
		
	}
	
	public void addHeaderRow(XWPFTable table){
		XWPFTableRow row = table.getRow(0);
		row.getCell(0).setText("Author");
		row.addNewTableCell().setText("URL");
		row.addNewTableCell().setText("Date");
	}
	
	
	

}
