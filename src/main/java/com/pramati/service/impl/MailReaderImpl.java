package com.pramati.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pramati.bean.MessageData;
import com.pramati.service.JsoupService;
import com.pramati.service.MailReader;
import com.pramati.utility.ParserUtility;

public class MailReaderImpl implements MailReader {

	private JsoupService service;
	

	public JsoupService getService() {
		return service;
	}

	public void setService(JsoupService service) {
		this.service = service;
	}
	
	
	public MailReaderImpl(JsoupService service ) {
		this.service = service;
	}
	
	private static Logger logger = Logger.getLogger(MailReaderImpl.class);

	public List<MessageData> readMail(String url) {

		if (null == url) {
			return null;
		}
		
		if(null == service) {
			logger.info("Jsoup Service is empty");
			return  null;
		}	
		
		// Getting MailIndex information
		String response = service.getResponse(url);
		if (null == response) {
			logger.info("Response is :" + response);
			return null;
		}
		// Converting as document object from response string
		Document doc = ParserUtility.getDocument(response);
		List<MessageData> mailMessageDataList = new ArrayList<MessageData>();
		// Getting  table in the document
		Node node = ParserUtility.getTable(doc, "msglist");

		if (node == null) {
			return null;
		}
		
		Element msgListTable = (Element) node;
		NodeList msgRowList = msgListTable.getElementsByTagName("tr");

		if (null == msgRowList) {
			return null;
		}

		int msgRowLength = msgRowList.getLength();

		for (int row = 0; row < msgRowLength; row++) {

			Element mailData = (Element) msgRowList.item(row);
			MessageData messageData = populateMailData(mailData);
			if (messageData != null) {
				mailMessageDataList.add(messageData);
			}
		}

		return mailMessageDataList;
	}

	private MessageData populateMailData(Element mailData) {
		if (null == mailData) {
			return null;
		}

		NodeList mailDetails = mailData.getElementsByTagName("td");

		if (null == mailDetails) {
			return null;
		}

		String author = null;
		String link = null; // subject
		String date = null;

		int mailDataLength = mailDetails.getLength();
		for (int md = 0; md < mailDataLength; md++) {
			Node mailInfo = mailDetails.item(md);
			String attValue = ParserUtility.getAttributeValue(mailInfo, "class");

			if (null != attValue) {

				if (attValue.equalsIgnoreCase("date")) {
					date = mailInfo.getTextContent();
				}

				if (attValue.equalsIgnoreCase("author")) {
					author = mailInfo.getTextContent();
				}

				if (attValue.equalsIgnoreCase("subject")) {
					Element mail = (Element) mailInfo;
					Node UrlNode = ParserUtility.getFirstNodeByTagName(mail, "a");
					link = ParserUtility.getAttributeValue(UrlNode, "href");

				}
			}

		}
		if (null == link) {
			return null;
		}

		MessageData messageData = new MessageData();
		messageData.setDate(date);
		messageData.setUrl(link);
		messageData.setAuthor(author);
		return messageData;

	}

}
