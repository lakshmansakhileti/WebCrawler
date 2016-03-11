package com.pramati.utility;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CrawlerProperties {

	private static Logger logger = Logger.getLogger(CrawlerProperties.class);

	public static String BASE_URL;
	public static Integer YEAR;
	public static Integer THREAD_POOL_SIZE;
	public static String DESTINATION_PATH;
	public static String FILE_EXTENTION;

	static {
		try {
			Properties crawlerPerperties = Utilities.loadPropetiesFile("crawler.properties");
			BASE_URL = crawlerPerperties.getProperty("BASE_URL");
			YEAR = Integer.parseInt(crawlerPerperties.getProperty("YEAR"));
			THREAD_POOL_SIZE = Integer.parseInt(crawlerPerperties.getProperty("THREAD_POOL_SIZE"));
			DESTINATION_PATH = System.getProperty("user.home") + File.separator + "YEAR " + YEAR;
			FileHelper.createDirectory(DESTINATION_PATH);
			FILE_EXTENTION = crawlerPerperties.getProperty("FILE_EXTENTION");

		} catch (IOException ex) {
			logger.error("Problem while loading properties.", ex);
			System.exit(0);
		}
	}

}
