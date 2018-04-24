package com.speech.model.entity;


public class PieceInfoEntity {
	
	private Integer id;
	private Integer fileId;//源文件id
	private String pieceName;//切片名称
	private String path;//文件地址
	private String pieceContent;//切片后转换的文字内容
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getPieceName() {
		return pieceName;
	}
	public void setPieceName(String pieceName) {
		this.pieceName = pieceName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPieceContent() {
		return pieceContent;
	}
	public void setPieceContent(String pieceContent) {
		this.pieceContent = pieceContent;
	}
	 
}
