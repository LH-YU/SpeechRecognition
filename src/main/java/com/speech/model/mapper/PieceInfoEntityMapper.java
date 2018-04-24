package com.speech.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.speech.model.entity.FileInfoEntity;
import com.speech.model.entity.PieceInfoEntity;



@Component
public interface PieceInfoEntityMapper {

    List<PieceInfoEntity> selectPieceByFileId(@Param(value="fileId")Integer fileId);
    
    int insert(PieceInfoEntity file);
    
    PieceInfoEntity getPieceByPieceId(@Param(value="id")Integer id,@Param(value="fileId")Integer fileId);
    
    Integer getBig(@Param(value="fileId")Integer fileId);
    
    Integer getSmall(@Param(value="fileId")Integer fileId);
    
    PieceInfoEntity getPieceById(@Param(value="id")Integer id);
}