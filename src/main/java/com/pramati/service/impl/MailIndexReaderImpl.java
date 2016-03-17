package com.pramati.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pramati.bean.MonthlyMailIndexData;
import com.pramati.service.JsoupService;
import com.pramati.service.MailIndexReader;
import com.pramati.utility.ParserUtility;

public class MailIndexReaderImpl implements MailIndexReader {
	JsoupService service;
	
	public JsoupService getService() {
		return service;
	}

	public void setService(JsoupService service) {
		this.service = service;
	}
	public MailIndexReaderImpl(JsoupService service) {
		this.service = service;
	}
	
	private static Logger logger = Logger.getLogger(MailIndexReaderImpl.class);

	public List<MonthlyMailIndexData> readMailIndexes(final String url, String year) {
		
		if (null == url) {
			logger.info("reading mails from url :"+url);
			return null;
		}
		
		if(null == service) {
			logger.info("Jsoup service is null.");
			return null;
		}
		// Getting MailIndex information
		String response = service.getResponse(url);
		if (null == response) {
			logger.info("Response is :" + response);
			return null;
		}
		//logger.info("Response is :" + response);
		// Converting as document object from response string
		Document doc = ParserUtility.getDocument(response);
		
		// Listing all table in the document
		NodeList nodeList = doc.getElementsByTagName("table");
		

		if(null == nodeList) {
			logger.info("mail info of year is empty ");
			return null;
		}
		
		int length = nodeList.getLength();
		List<MonthlyMailIndexData> monthlyMailIndexDataList = new ArrayList<MonthlyMailIndexData>();
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			String tableId = ParserUtility.getAttributeValue(node, "id");
			//Reading mail index data from table which have id as grid.
			if (tableId != null && tableId.equalsIgnoreCase("grid")) {
				Element gridTable = (Element) node;
				NodeList gridTableNodeList = gridTable.getElementsByTagName("table");
				int gridTableLength = gridTableNodeList.getLength();

				for (int j = 0; j < gridTableLength; j++) {
					Element yearTable = (Element) gridTableNodeList.item(j);
					// reading  year information from thead.
					Node monthAndYearNode = ParserUtility.getFirstNodeByTagName(yearTable, "th");
					// Checking year information with required year information.
					if (monthAndYearNode != null
							&& ParserUtility.isNodeTextContentEqual(monthAndYearNode, "Year "+year)) {
						//reading monthly mailing information
						NodeList mailMonthNodeList = yearTable.getElementsByTagName("tr");
						int mailMonthListLength = mailMonthNodeList.getLength();
						
						for (int m = 0; m < mailMonthListLength; m++) {
							Element monthMailData = (Element) mailMonthNodeList.item(m);
							//Populating monthly mail information to model.
							MonthlyMailIndexData monthlyMailIndexData = populateMonthlyMailIndexData(monthMailData);
							if (monthlyMailIndexData != null) {
								monthlyMailIndexDataList.add(monthlyMailIndexData);
							}
						}
					}

				}

			}

		}
		return monthlyMailIndexDataList;
		
	}

	private MonthlyMailIndexData populateMonthlyMailIndexData(Element monthMailData) {
		if (null == monthMailData) {
			return null;
		}

		NodeList mailDetailsList = monthMailData.getElementsByTagName("td");
		String date = null;
		String link = null;
		String msgCount = null;

		int mailDataLength = mailDetailsList.getLength();
		for (int md = 0; md < mailDataLength; md++) {
			Node mailInfo = mailDetailsList.item(md);
			String attValue = ParserUtility.getAttributeValue(mailInfo, "class");

			if (null != attValue) {

				if (attValue.equalsIgnoreCase("date")) {
					date = mailInfo.getTextContent();
				}

				if (attValue.equalsIgnoreCase("msgcount")) {
					msgCount = mailInfo.getTextContent();
				}

				if (attValue.equalsIgnoreCase("links")) {
					Element mail = (Element) mailInfo;
					Node UrlNode = ParserUtility.getFirstNodeByTagName(mail, "a");
					link = ParserUtility.getAttributeValue(UrlNode, "href");

				}
			}

		}
		if (null == link) {
			return null;
		}

		MonthlyMailIndexData monthlyMailIndexData = new MonthlyMailIndexData();
		monthlyMailIndexData.setMessageCount(null != msgCount ? Integer.parseInt(msgCount) : null);
		monthlyMailIndexData.setUrl(link);
		monthlyMailIndexData.setMonthAndYear(date);
		return monthlyMailIndexData;

	}
}
