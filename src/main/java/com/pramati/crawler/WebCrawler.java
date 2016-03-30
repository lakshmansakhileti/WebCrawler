package com.pramati.crawler;
/**
 * 
 */


import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.pramati.bean.MessageData;
import com.pramati.bean.MonthlyMailIndexData;
import com.pramati.poi.service.POIDownloader;
import com.pramati.poi.service.POIExcelService;
import com.pramati.poi.service.impl.ExcelDownloaderImpl;
import com.pramati.poi.service.impl.POIExcelServiceImpl;
import com.pramati.poi.service.impl.POIWordServiceImpl;
import com.pramati.poi.service.impl.WordDownloader;
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
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
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
			//Creating Ex-cell work book
			HSSFWorkbook workBook = new HSSFWorkbook();

			// Creating Word document
			  XWPFDocument doc = new XWPFDocument();
		      XWPFTable table = doc.createTable();
		      POIWordServiceImpl wordService = new POIWordServiceImpl();
		      wordService.addHeaderRow(table);
		      
		      POIExcelService excelService = new POIExcelServiceImpl();
		    // Rendering mail information  
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
			    
				// Write all information into Excel file using poi.
				
				
				logger.info("Created work book");
				
				excelService.makeWorkBook(mailList, workBook, monthlyMailIndexData.getMonthAndYear());
				
				// Write all information into word file using poi.
				logger.info("Preparing word document");
				
				wordService.addToTable(table, mailList);
				
			    
			}
			// Downloading Ex-cell file
			POIDownloader excelDownloader = new ExcelDownloaderImpl(workBook);
			excelDownloader.downloadFile( crawlerProperties.getUSER_HOME()+"MailInformation.xls");
			// Downloading Word file
			POIDownloader wordDownloader = new WordDownloader(doc);
			wordDownloader.downloadFile(crawlerProperties.getUSER_HOME()+"MailInformation.docx");
	}

}
