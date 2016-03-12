package com.pramati.crawler;
/**
 * 
 */


import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.pramati.bean.MessageData;
import com.pramati.bean.MonthlyMailIndexData;
import com.pramati.service.CrawlerDownloaderService;
import com.pramati.service.MailIndexReader;
import com.pramati.service.MailReader;
import com.pramati.utility.Utilities;

/**
 * @author lakshmanar
 *
 */
public class WebCrawler {

	final static Logger logger = Logger.getLogger(WebCrawler.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// loading properties
			Properties crawlerPerperties = Utilities.loadPropetiesFile("crawler.properties");
			final String baseUrl = crawlerPerperties.getProperty("BASE_URL");
			logger.info("Crawler Base URL  :"+baseUrl);
			//Reading mails of all months of given year.
			MailIndexReader mailIndexReader = new MailIndexReader();
			List<MonthlyMailIndexData> mailIndexesList = mailIndexReader.readMailIndexes(baseUrl, crawlerPerperties.getProperty("YEAR"));
			if(null == mailIndexesList) {
				return;
			}
			
			//Reading mails of month
			MailReader mailReader = new MailReader();
			for (MonthlyMailIndexData monthlyMailIndexData : mailIndexesList) {
				
				if(null == monthlyMailIndexData) {
					continue;
				}
				// Reading all mail conversation
				String monthUrl = baseUrl+monthlyMailIndexData.getUrl();
				List<MessageData> mailList = mailReader.readMail(monthUrl);
				
				if(null == mailList) {
					continue;
				}
				// Downloading to local disk.
				CrawlerDownloaderService crawlerDownloaderService = new CrawlerDownloaderService();
			    crawlerDownloaderService.downloadFiles(mailList,monthlyMailIndexData.getUrl());
				
			}
			
		} catch (IOException ioException) {
			logger.info("Having problem while loading properties from file : crawler.properties"+ioException.getLocalizedMessage());
		}
		
	}

}
