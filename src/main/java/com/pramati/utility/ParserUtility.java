package com.pramati.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserUtility {
	private static Logger logger = Logger.getLogger(ParserUtility.class);

	public static String getAttributeValue(Node node, String attributeName) {
		if (null == node) {
			return null;
		}

		if (!node.hasAttributes()) {
			return null;
		}
		NamedNodeMap tableAttributes = node.getAttributes();
		Node attributeNode = tableAttributes.getNamedItem(attributeName);

		if (null == attributeNode) {
			return null;
		}

		return attributeNode.getNodeValue();
	}

	public static Node getFirstNodeByTagName(Element element, String tagName) {
		if (null == element || null == tagName) {
			return null;
		}

		NodeList nodeList = element.getElementsByTagName(tagName);

		if (null == nodeList) {
			return null;
		}
		return nodeList.item(0);

	}

	public static Boolean isNodeTextContentEqual(Node node, String value) {

		if (null == node || null == value) {
			return Boolean.FALSE;
		}
		String textContent = node.getTextContent();
		if (null == textContent) {
			return Boolean.FALSE;
		}
		return textContent.equalsIgnoreCase(value);
	}

	public static Boolean isNodeValueEqual(Node node, String value) {

		if (null == node || null == value) {
			return Boolean.FALSE;
		}
		String nodeValue = node.getNodeValue();
		if (null == nodeValue) {
			return Boolean.FALSE;
		}
		return nodeValue.equalsIgnoreCase(value);
	}

	public static Document getDocument(String inputString) {

		InputStream inputStream = null;
		DocumentBuilder dBuilder = null;
		Document doc = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		inputStream = new ByteArrayInputStream(inputString.getBytes());
		try {
			dBuilder = docFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			logger.error("Problem with Parser configuration :", e);
		} catch (SAXException saxExc) {
			logger.error("Problem while parsing response", saxExc);
		} catch (IOException e) {
			logger.error("Problem while creating document", e);
		}

		doc.getDocumentElement().normalize();
		return doc;
	}

	/**
	 * Which returns all the rows of specified table based on table id value.
	 * 
	 * @param table
	 * @param inputTableId
	 * @return
	 */
	public static NodeList getRowsFromTable(Node table, String inputTableId) {
		NodeList rowNodeList = null;

		if (null == table || inputTableId == null) {
			return rowNodeList;
		}

		String tableId = ParserUtility.getAttributeValue(table, "id");

		if (tableId != null && tableId.equalsIgnoreCase(inputTableId)) {
			Element msgListTable = (Element) table;
			rowNodeList = msgListTable.getElementsByTagName("tr");
		}

		return rowNodeList;
	}

	public static Node getTable(Document doc, String tableId) {

		if (null == doc || tableId == null) {
			return null;
		}

		NodeList nodeList = doc.getElementsByTagName("table");

		if (null == nodeList) {
			return null;
		}
		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			String tableIdValue = ParserUtility.getAttributeValue(node, "id");
			if (tableIdValue != null && tableIdValue.equalsIgnoreCase(tableId)) {
				return node;
			}
		}
		return null;

	}

}
