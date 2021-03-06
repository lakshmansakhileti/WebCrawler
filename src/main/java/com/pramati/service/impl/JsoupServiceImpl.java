package com.pramati.service.impl;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.pramati.service.JsoupService;

public class JsoupServiceImpl implements JsoupService {
	private static Logger logger = Logger.getLogger(JsoupServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.pramati.service.JsoupService#getResponse(java.lang.String)
	 */
	public String getResponse(String url) {
		String responseBody = null;
		Connection.Response response = null;

		try {
			Connection connection = Jsoup.connect(url);
			connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0");
			connection.maxBodySize(0);
			connection.timeout(100000);
			response = connection.execute();
			responseBody = response.body();
		} catch (Exception ex) {
			logger.error("Problem in getting resposnse :", ex);
		}
		return responseBody;
	}

}
