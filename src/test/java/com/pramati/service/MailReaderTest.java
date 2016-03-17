package com.pramati.service;

import static org.mockito.Mockito.when;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.pramati.bean.MessageData;
import com.pramati.service.impl.MailReaderImpl;

public class MailReaderTest {
	private static Logger logger = Logger.getLogger(MailIndexReader.class);

	public static MailReader mailReader;
	public static JsoupService jsoupMockService = Mockito.mock(JsoupService.class);

	@BeforeClass
	public static void initializeObject() {
		mailReader = new MailReaderImpl(jsoupMockService);
		logger.info("Running MailReade test cases...");
	}

	@Test
	public void testReadMailIndexesWithEmptyUrl() {
		logger.info("Calling readMail method with parameters url as null ");
		List<MessageData> readMail = mailReader.readMail(null);
		Assert.assertNull(readMail);
	}

	@Test
	public void testReadMailWithUrl() throws ParserConfigurationException {

		logger.info("Calling readMail method with parameters url as null ");
		String url="http://mail-archives.apache.org/mod_mbox/maven-users/201603.mbox/browser";
		String response = getResponseString();
		when(jsoupMockService.getResponse(url)).thenReturn(response);
    
		List<MessageData> readMails = mailReader.readMail(url);
		Assert.assertNotNull(readMails);
		Assert.assertTrue(readMails.size() == 1);
	}

	@Test
	public void testReadMailWithEmptyResponse() throws ParserConfigurationException {

		logger.info("Calling readMail method with parameters url as null ");
		String url="http://mail-archives.apache.org/mod_mbox/maven-users/201603.mbox/browser";
		String response = null;
		when(jsoupMockService.getResponse(url)).thenReturn(response);
    
		List<MessageData> readMails = mailReader.readMail(url);
		Assert.assertNull(readMails);
	}

	private String getResponseString() {
		StringBuilder responseBuilder = new StringBuilder();

		responseBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		responseBuilder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
		responseBuilder.append(" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		responseBuilder.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		responseBuilder.append("<head> </head>");
		responseBuilder.append("<body id=\"archives\">");
		responseBuilder.append("<table id=\"msglist\">");
		responseBuilder.append(
				"<tr> <td class=\"author\">lakshman</td> <td class=\"subject\"><a href=\"ajax/%3C03C4D574-5F08-44D0-8DDA-E59E1AA03214%40tu-clausthal.de%3E\">plugin and executable jar</a> &middot;</td> ");
		responseBuilder.append("<td class=\"date\">Tue, 01 Mar, 21:44</td>");
		responseBuilder.append(" </tr> </table>");
		responseBuilder.append(" </body>");
		responseBuilder.append(" </html>");
		return responseBuilder.toString();
	}
}
