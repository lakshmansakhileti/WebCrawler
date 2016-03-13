package com.pramati.service;

import java.util.List;

import com.pramati.bean.MessageData;

public interface CrawlerDownloaderService {

	public void downloadFiles(List<MessageData> messageDataList, String monthUrl);
}
