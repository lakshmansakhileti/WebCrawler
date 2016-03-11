package com.pramati.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.pramati.bean.MessageData;
import com.pramati.utility.CrawlerProperties;
import com.pramati.utility.FileHelper;

public class CrawlerDownloaderService extends CrawlerProperties {

	private static Logger logger = Logger.getLogger(CrawlerDownloaderService.class);

	public void downloadFiles(List<MessageData> messageDataList, String monthUrl) {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		try {
			FileHelper.createDirectory(DESTINATION_PATH +File.separator+ monthUrl.split("/thread")[0]);
			for (MessageData messageData : messageDataList) {
				logger.info("** MothnUrl :"+monthUrl);
				String sourceUrl = BASE_URL + monthUrl.split("/thread")[0] +File.separator+ messageData.getUrl();
				String destinationPath = DESTINATION_PATH +File.separator+ monthUrl.split("/thread")[0] +File.separator+ messageData.getDate() + FILE_EXTENTION;
				executorService.execute(new FileDownloader(sourceUrl, destinationPath));

			}

		} catch (Exception ex) {
			logger.error("Exception while processing mail" , ex);
		} finally {
			executorService.shutdown();
		}
	}
}
