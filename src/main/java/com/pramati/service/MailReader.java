package com.pramati.service;

import java.util.List;

import com.pramati.bean.MessageData;

public interface MailReader {
	public List<MessageData> readMail(String url);
}
