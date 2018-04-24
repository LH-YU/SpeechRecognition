package com.speech.model.entity;


public class FileInfoEntity {
	private Integer id;
	private String fileName;//文件名称
	private Integer size;//文件大小
	private String path;//文件地址
	private String upDate;//上传时间
	private String type;//数据格式类型
	private Integer number;//分片大小
	private Integer status;//是否识别
	private String content;//识别后的文字内容
	private String md5;//识别后的文字内容
	private String filetime;//音频时长
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getUpDate() {
		return upDate.substring(0,19);
	}
	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getFiletime() {
		return filetime;
	}
	public void setFiletime(String filetime) {
		this.filetime = filetime;
	}
}
