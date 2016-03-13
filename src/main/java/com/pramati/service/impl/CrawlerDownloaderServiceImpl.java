package com.pramati.service.impl;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.pramati.bean.MessageData;
import com.pramati.service.CrawlerDownloaderService;
import com.pramati.utility.CrawlerProperties;
import com.pramati.utility.FileHelper;

public class CrawlerDownloaderServiceImpl implements CrawlerDownloaderService {

	private static Logger logger = Logger.getLogger(CrawlerDownloaderServiceImpl.class);
	
	public void downloadFiles(List<MessageData> messageDataList, String monthUrl) {
		CrawlerProperties crawlerProperties = CrawlerProperties.getInstance();
		if(null == crawlerProperties) {
			logger.info("Problem while loading property file...");
			return;
		}
		ExecutorService executorService = Executors.newFixedThreadPool(crawlerProperties.getTHREAD_POOL_SIZE());
		try {
			FileHelper.createDirectory(crawlerProperties.DESTINATION_PATH +File.separator+ monthUrl.split("/thread")[0]);
			for (MessageData messageData : messageDataList) {
				String sourceUrl = crawlerProperties.getBASE_URL() + monthUrl.split("/thread")[0] +File.separator+ messageData.getUrl();
				String destinationPath = crawlerProperties.getDESTINATION_PATH() +File.separator+ monthUrl.split("/thread")[0] +File.separator+ messageData.getDate() + crawlerProperties.getFILE_EXTENTION();
				executorService.execute(new FileDownloader(sourceUrl, destinationPath));

			}

		} catch (Exception ex) {
			logger.error("Exception while processing mail" , ex);
		} finally {
			executorService.shutdown();
		}
	}
}
