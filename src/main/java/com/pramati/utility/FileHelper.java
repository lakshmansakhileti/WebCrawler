package com.pramati.utility;

import java.io.File;

import org.apache.log4j.Logger;

public class FileHelper {
	public static Logger logger = Logger.getLogger(FileHelper.class);

	public static Boolean createDirectory(String directoryName) {

		if (null == directoryName) {
			return Boolean.FALSE;
		}
		
		
		File theDir = new File(directoryName);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			logger.info("creating directory: " + directoryName);

			try {
				theDir.mkdir();
			} catch (SecurityException se) {
				logger.error("While creating direcoty have problem :" + se.getLocalizedMessage());
				return Boolean.FALSE;
			}

			logger.info("Directory  created with name " + directoryName);
			return Boolean.TRUE;
		}

		logger.info("Directory  already exist with name " + directoryName);
		return Boolean.TRUE;
	}
	
	
	
	

}
