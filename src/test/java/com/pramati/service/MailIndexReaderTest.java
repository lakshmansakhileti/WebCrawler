package com.pramati.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.pramati.bean.MonthlyMailIndexData;
import com.pramati.service.impl.MailIndexReaderImpl;
import com.pramati.utility.CrawlerProperties;

public class MailIndexReaderTest {

	public static MailIndexReader mailIndexReader;
	public static CrawlerProperties properties;
	public static JsoupService jsoupMockService = Mockito.mock(JsoupService.class);

	@BeforeClass
	public static void init() {
		properties = CrawlerProperties.getInstance();
		mailIndexReader = new MailIndexReaderImpl(jsoupMockService);
	}

	@Test
	public void testReadMailIndexes() {
		String url = properties.getBASE_URL();

		String response = getResponseString();

		when(jsoupMockService.getResponse(url)).thenReturn(response);

		List<MonthlyMailIndexData> readMailIndexes = mailIndexReader.readMailIndexes(url, "2016");

		Assert.assertNotNull(readMailIndexes);
		Assert.assertTrue(readMailIndexes.size() == 1);

	}

	@Test
	public void testReadMailIndexesWithEmptyResponse() {
		String url = properties.getBASE_URL();

		String response = null;

		when(jsoupMockService.getResponse(url)).thenReturn(response);

		List<MonthlyMailIndexData> readMailIndexes = mailIndexReader.readMailIndexes(url, "2016");

		Assert.assertNull(readMailIndexes);

	}

	private String getResponseString() {
		StringBuilder responseBuilder = new StringBuilder();

		responseBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		responseBuilder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
		responseBuilder.append(" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		responseBuilder.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		responseBuilder.append("<head> </head>");
		responseBuilder.append("<body id=\"archives\">");
		responseBuilder.append("<table id=\"grid\">");
		responseBuilder.append(
				"<tr><td class=\"left\"> <table class=\"year\"> <thead><tr> <th colspan=\"3\">Year 2016</th> </tr></thead> <tbody>");
		responseBuilder.append(
				"<tr> <td class=\"date\">Mar 2016</td> <td class=\"links\"><span class=\"links\" id=\"201603\"><a href=\"201603.mbox/thread\">Thread</a> &middot; <a href=\"201603.mbox/date\">Date</a>");
		responseBuilder.append("<a href=\"201603.mbox/author\">Author</a></span></td>");
		responseBuilder.append(" <td class=\"msgcount\">69</td>");
		responseBuilder.append(" </tr> </tbody></table></td>");
		responseBuilder.append("  <td class=\"right\"></td></tr> </table>");
		responseBuilder.append(" </body>");
		responseBuilder.append(" </html>");
		return responseBuilder.toString();
	}
}
