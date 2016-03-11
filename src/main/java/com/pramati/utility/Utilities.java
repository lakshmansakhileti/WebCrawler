package com.pramati.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {
	
	private static InputStream inputStream = null;
	
	public static Properties loadPropetiesFile(final String propFileName) throws IOException{
		
		if(null == propFileName) {
			return null;
		}
		
		Properties prop = new Properties();

		inputStream = new Utilities().getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}

		return prop;
	}

}
