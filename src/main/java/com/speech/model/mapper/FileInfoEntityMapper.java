package com.speech.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.speech.model.entity.FileInfoEntity;


@Component
public interface FileInfoEntityMapper {

    List<FileInfoEntity> selectAll();
    
    int insert(FileInfoEntity file);
    
    public void updateFile(@Param(value="id")Integer id);
    
    FileInfoEntity selectCountByMd5(@Param(value="md5")String md5);
    
    FileInfoEntity selectFileById(@Param(value="id")Integer id);
    
    FileInfoEntity selectFile(@Param(value="pageIndex")Integer pageIndex,@Param(value="pageNum")Integer pageNum);
}