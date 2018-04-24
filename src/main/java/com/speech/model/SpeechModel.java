package com.speech.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.speech.model.entity.FileInfoEntity;
import com.speech.model.entity.PieceInfoEntity;
import com.speech.model.mapper.FileInfoEntityMapper;
import com.speech.model.mapper.PieceInfoEntityMapper;

@Component
public class SpeechModel {
	
	@Autowired
	private FileInfoEntityMapper mapper;
	
	@Autowired
	private PieceInfoEntityMapper pieceMapper;
	
	public List<FileInfoEntity> getAll(){
		return mapper.selectAll();
	}
	public List<PieceInfoEntity> getPieceByFileId(Integer fileId){
		return pieceMapper.selectPieceByFileId(fileId);
	}
	
	public PieceInfoEntity getPieceByPieceId(Integer pieceId,Integer fileId){
		return pieceMapper.getPieceByPieceId(pieceId,fileId);
	}
	
	public PieceInfoEntity getPieceById(Integer id){
		return pieceMapper.getPieceById(id);
	}
	
	public int fileInsert(FileInfoEntity file){
		return mapper.insert(file);
	}
	//根据md5查询是否有重复的数据
	public FileInfoEntity getCountByMd5(String md5) {
		return mapper.selectCountByMd5(md5);
	}
	
	public FileInfoEntity selectFileById(Integer fileId) {
		return mapper.selectFileById(fileId);
	}
	
	public Integer getBig(Integer fileId) {
		return pieceMapper.getBig(fileId);
	}
	
	public Integer getSmall(Integer fileId) {
		return pieceMapper.getSmall(fileId);
	}
	
}
