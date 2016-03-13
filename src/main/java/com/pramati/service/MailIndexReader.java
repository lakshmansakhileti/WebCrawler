package com.pramati.service;

import java.util.List;

import com.pramati.bean.MonthlyMailIndexData;

public interface MailIndexReader {
	public List<MonthlyMailIndexData> readMailIndexes(final String url, String year);
}
