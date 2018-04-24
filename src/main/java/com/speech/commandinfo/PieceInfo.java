package com.speech.commandinfo;

import com.speech.model.entity.PieceInfoEntity;

public class PieceInfo extends PieceInfoEntity{
	private String textSize;

	public String getTextSize() {
		return textSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public PieceInfo(PieceInfoEntity piece) {
		this.setFileId(piece.getFileId());
		this.setId(piece.getId());
		this.setPath(piece.getPath());
		this.setPieceContent(piece.getPieceContent());
		this.setPieceName(piece.getPieceName());
	}
	
	
}
