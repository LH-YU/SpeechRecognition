package com.speech.commandinfo;

import com.speech.model.entity.FileInfoEntity;

public class FileInfo extends FileInfoEntity{
	private String textSize;
	private String fileSize;

	public String getTextSize() {
		return textSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}
