package com.pramati.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pramati.bean.MonthlyMailIndexData;
import com.pramati.utility.ParserUtility;

public class MailIndexReader {

	private static Logger logger = Logger.getLogger(MailIndexReader.class);

	public List<MonthlyMailIndexData> readMailIndexes(final String url, String year) {
		
		if (null == url) {
			return null;
		}

		JsoupService service = new JsoupService();
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
			return null;
		}
		
		int length = nodeList.getLength();
		List<MonthlyMailIndexData> monthlyMailIndexDataList = new ArrayList<MonthlyMailIndexData>();
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			String tableId = ParserUtility.getAttributeValue(node, "id");

			if (tableId != null && tableId.equalsIgnoreCase("grid")) {
				Element gridTable = (Element) node;
				NodeList gridTableNodeList = gridTable.getElementsByTagName("table");
				int gridTableLength = gridTableNodeList.getLength();

				for (int j = 0; j < gridTableLength; j++) {
					Element yearTable = (Element) gridTableNodeList.item(j);
					Node monthAndYearNode = ParserUtility.getFirstNodeByTagName(yearTable, "th");
					if (monthAndYearNode != null
							&& ParserUtility.isNodeTextContentEqual(monthAndYearNode, "Year "+year)) {
						NodeList mailMonthNodeList = yearTable.getElementsByTagName("tr");
						int mailMonthListLength = mailMonthNodeList.getLength();
						
						for (int m = 0; m < mailMonthListLength; m++) {
							Element monthMailData = (Element) mailMonthNodeList.item(m);
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
