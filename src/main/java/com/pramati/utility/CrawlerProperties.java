package com.pramati.utility;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CrawlerProperties {

	private static Logger logger = Logger.getLogger(CrawlerProperties.class);
	private static CrawlerProperties crawlerProperties;

	public  String BASE_URL;
	public  String YEAR;
	public  Integer THREAD_POOL_SIZE;
	public  String DESTINATION_PATH;
	public  String FILE_EXTENTION;
	
	private CrawlerProperties() {
		
	}
	
	private CrawlerProperties(String propertyFileName) {
		try {
			Properties crawlerPerperties = Utilities.loadPropetiesFile(propertyFileName);
			BASE_URL = crawlerPerperties.getProperty("BASE_URL");
			YEAR = crawlerPerperties.getProperty("YEAR");
			THREAD_POOL_SIZE = Integer.parseInt(crawlerPerperties.getProperty("THREAD_POOL_SIZE"));
			DESTINATION_PATH = System.getProperty("user.home") + File.separator + "YEAR " + YEAR;
			FileHelper.createDirectory(DESTINATION_PATH);
			FILE_EXTENTION = crawlerPerperties.getProperty("FILE_EXTENTION");
		} catch (IOException ioe) {
			logger.error("Problem while loading properties.", ioe);
		}
	}
	
	
	
	public String getBASE_URL() {
		return BASE_URL;
	}

	public String getYEAR() {
		return YEAR;
	}

	public Integer getTHREAD_POOL_SIZE() {
		return THREAD_POOL_SIZE;
	}

	public String getDESTINATION_PATH() {
		return DESTINATION_PATH;
	}

	public String getFILE_EXTENTION() {
		return FILE_EXTENTION;
	}


	public static CrawlerProperties getInstance() {
		synchronized (CrawlerProperties.class ) {
			if(null == crawlerProperties) {
				crawlerProperties = new CrawlerProperties("crawler.properties");
			}
			return crawlerProperties;
		}
	}

}
