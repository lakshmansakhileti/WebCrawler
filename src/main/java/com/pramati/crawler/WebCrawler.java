package com.pramati.crawler;
/**
 * 
 */


import java.util.List;

import org.apache.log4j.Logger;

import com.pramati.bean.MessageData;
import com.pramati.bean.MonthlyMailIndexData;
import com.pramati.service.CrawlerDownloaderService;
import com.pramati.service.JsoupService;
import com.pramati.service.MailIndexReader;
import com.pramati.service.MailReader;
import com.pramati.service.impl.CrawlerDownloaderServiceImpl;
import com.pramati.service.impl.JsoupServiceImpl;
import com.pramati.service.impl.MailIndexReaderImpl;
import com.pramati.service.impl.MailReaderImpl;
import com.pramati.utility.CrawlerProperties;

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
			// loading properties
			CrawlerProperties crawlerProperties = CrawlerProperties.getInstance();
			final String baseUrl = crawlerProperties.getBASE_URL();
			logger.info("Crawler Base URL  :" + baseUrl);
			String year = crawlerProperties.getYEAR();
			logger.info("Reading mail of year " + year);
			//Reading mails of all months of given year.
			
			if (null == baseUrl) {
				return;
			}

			JsoupService service = new JsoupServiceImpl();
			// Getting MailIndex information
			MailIndexReader mailIndexReader = new MailIndexReaderImpl(service);
			List<MonthlyMailIndexData> mailIndexesList = mailIndexReader.readMailIndexes(baseUrl, year);
			if(null == mailIndexesList) {
				logger.info("mail index is empty");
				return;
			}
			
			//Reading mails of month
			MailReader mailReader = new MailReaderImpl(service);
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
				CrawlerDownloaderService crawlerDownloaderService = new CrawlerDownloaderServiceImpl();
			    crawlerDownloaderService.downloadFiles(mailList,monthlyMailIndexData.getUrl());
				
			}
		
	}

}
