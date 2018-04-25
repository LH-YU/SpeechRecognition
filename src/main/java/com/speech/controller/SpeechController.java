package com.speech.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.speech.commandinfo.FileInfo;
import com.speech.commandinfo.PieceInfo;
import com.speech.model.SpeechModel;
import com.speech.model.entity.FileInfoEntity;
import com.speech.model.entity.PieceInfoEntity;
import com.speech.model.mapper.FileInfoEntityMapper;
import com.speech.model.mapper.PieceInfoEntityMapper;
import com.speech.util.FileCutUtil;
import com.speech.util.FileSizeUtil;
import com.speech.util.GetFileNameUtil;
import com.speech.util.SpeechUtil;



@Controller
@RestController
@RequestMapping("/speech")
public class SpeechController {

	@Autowired
	private SpeechModel speechModel;
	@Autowired
	private PieceInfoEntityMapper  pieceInfoEntityMapper;
	@Autowired
	private FileInfoEntityMapper  fileInfoEntityMapper;

	//获取file的上传记录
	@RequestMapping(value = "file/list")
	public ModelAndView info() {
		Map<String,Object> infos = new HashMap<String,Object>();
		List<FileInfoEntity> list = speechModel.getAll();
		infos.put("data", list);
		return new ModelAndView("index","infos",infos);
	}
	
	//根据文件id查询分片
	@RequestMapping(value = "file/piece")
	public ModelAndView getPiece(Integer fileId) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		Map<String,Object> infos = new HashMap<String,Object>();
		List<PieceInfoEntity> list = speechModel.getPieceByFileId(fileId);
		List<PieceInfo> pieceInfolist = new ArrayList<PieceInfo>();
		StringBuffer permission = new StringBuffer();  
		for(PieceInfoEntity pieceInfoEntity :list) {
			permission.append(pieceInfoEntity.getPieceContent()+",");
			PieceInfo piece = new PieceInfo(pieceInfoEntity);
			piece.setTextSize(pieceInfoEntity.getPieceContent().length()+"字");
			pieceInfolist.add(piece);
		}
		FileInfoEntity file = speechModel.selectFileById(fileId);
		FileInfo lastFile = new FileInfo();
		lastFile.setId(file.getId());
		lastFile.setFileName(file.getFileName());
		lastFile.setPath(file.getPath());
		lastFile.setTextSize(permission.toString().length()+"字");
		lastFile.setFiletime(file.getFiletime());
		//计算音频时间
		/*File source =new File(file.getPath()); 
		Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(source);
		clip.open(ais);
		System.out.println( clip.getMicrosecondLength() / 1000000D + " s" );//获取音频文件时长
		 */		
		Integer big = speechModel.getBig(fileId);
		infos.put("big", big);
		infos.put("data", pieceInfolist);
		infos.put("file", lastFile);
		return new ModelAndView("list","infos",infos);
	}
	
	//上传记录
	@RequestMapping(value = "file/upload")
	public ModelAndView insert(MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException, LineUnavailableException, UnsupportedAudioFileException {
		Map<String,Object> rep = new HashMap<String,Object>();	
		//检测文件是否有相同的md5
		String detectionMd5 = DigestUtils.md5Hex(file.getBytes());
		FileInfoEntity detectionFile = speechModel.getCountByMd5(detectionMd5);
		FileInfoEntity fileInfo = new FileInfoEntity();
		//如果md5相同，插入数据，不上传
		if (null != detectionFile) {
			String newfilename = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
			fileInfo.setFileName(newfilename);
			fileInfo.setPath(detectionFile.getPath());
			fileInfo.setStatus(detectionFile.getStatus());
			fileInfo.setType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
			Date date = new Date();
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String dateString = formatter.format(date);
			fileInfo.setUpDate(dateString);
			fileInfo.setSize((int) (file.getSize()/(1024*1024)));
			fileInfo.setMd5(null);
			fileInfo.setNumber(0);
			Integer success = speechModel.fileInsert(fileInfo);
			rep.put("success", success);
		} else {
			//md5不相同，上传并插入数据
			//String path = request.getSession().getServletContext().getRealPath("/"); 
			String realpath = "speech";
			String fileName = file.getOriginalFilename(); 
	        File dir = new File(realpath,fileName);          
	        if(!dir.exists()){  
	            dir.mkdirs();  
	        }  
	        //MultipartFile自带的解析方法  
	        file.transferTo(dir);
	        
	        String newfilename = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
			fileInfo.setFileName(newfilename);
			fileInfo.setPath("speech/"+newfilename);
			fileInfo.setStatus(0);
			String su = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			fileInfo.setType(su);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateString = formatter.format(date);
			fileInfo.setUpDate(dateString);
			fileInfo.setSize((int) (file.getSize()/(1024*1024)));
			fileInfo.setMd5(detectionMd5);
			fileInfo.setNumber(0);
			if (su.equals("wav") || su.equals("WAV")) {
				fileInfo.setFiletime(FileSizeUtil.countFileSize("speech/"+newfilename));
			}
			
			Integer success = speechModel.fileInsert(fileInfo);
			rep.put("success", success);
		}
		return new ModelAndView("redirect:./list");
	}	
	
	//语音识别（短语音直接识别 长语音先调用c中vad切片 拆分成短语音）
	@RequestMapping(value = "file/convert")
	public ModelAndView convert(Integer fileId) {
		
		Map<String,Object> infos = new HashMap<String,Object>();
		/*
		 * 跟据文件id获取文件信息
		 * 根据文件时长 进行判断该语音文件是长语音还是短语音 
		 * 长语音条件下根据文件格式判断是否需要先进行格式转换
		 */
		FileInfoEntity fileinfo = speechModel.selectFileById(fileId);
		int a_value = fileinfo.getFiletime().compareTo("00:1:00");
		//如果d_value大于0 文件时长超过一分钟 进行切片处理
		if(a_value > 0) {
			
			String url = fileinfo.getPath();
			String name = fileinfo.getFileName();
			
			String hz = name.substring(name.length()-4, name.length());
			//如果上传文件格式为wav 先转换格式再切片
			if(!hz.equals("") && hz.equals(".WAV")){
				name = name.replace("WAV", "pcm");
				url  = FileSizeUtil.convertToPcm(url,name);
			}else if ( hz.equals(".wav")){
				name = name.replace("wav", "pcm");
				url  = FileSizeUtil.convertToPcm(url,name);
			}
			
			//先创建相应的目录 再传参目录
	    	String realpath = "output_speech\\"+name.substring(0, name.length()-4);
	        File dir = new File(realpath);          
	        if(!dir.exists()){  	            
	        	dir.mkdirs();  
	        } 
	    	
	    	char[] filename = url.toCharArray();
	    	char[] output_filename_prefix = name.toCharArray();
	    	char[] output_dir = realpath.toCharArray();
	    	
	    	FileCutUtil fileCut = new FileCutUtil(filename, output_filename_prefix,output_dir);  
	    	int res = fileCut.setFile(filename, output_filename_prefix,output_dir);
	    	
	    	//如果res=0 切片结束 根据路径和文件名读取切片后的文件进行语音识别并且入库
	    	if(res != 0){
	    		System.out.println("切片失败！");
	    		res = fileCut.setFile(filename, output_filename_prefix,output_dir);
	    	}
	    	String[] fileName = GetFileNameUtil.getFileName(realpath);
	    	List<String> fileNameList = Arrays.asList(fileName);
	    	
	    	PieceInfoEntity piece = new PieceInfoEntity();
	    	String content = "";
	    	for(String newName : fileNameList){
	    		
	    		File dir2 = new File(realpath+"\\"+newName);   
	    		JSONObject json = SpeechUtil.client.asr(dir2.getPath(), "pcm", 16000, null);
	    		
	    		//语音识别 获取json对象 从json中获取识别结果
	    		JSONArray ja = json.optJSONArray("result");
	    		String result = "";
	    		if( ja!=null){
	    			result = ja.toString();
	    			result = result.substring(2, result.length()-2);
	    		}
	    		
	    		piece.setFileId(fileId);
	    		piece.setPieceName(newName);
	    		piece.setPath(realpath+"\\"+newName);
	    		piece.setPieceContent(result);
	    		pieceInfoEntityMapper.insert(piece);
	    		content += result;
	    		
	    	}
	    	if(res == 0){
	    		//切片成功 记录fileinfo表中切片数量 语音识别信息
	    		int number = pieceInfoEntityMapper.getCountByFileId(fileId);
	    		HashMap map = new HashMap();
	    		map.put("number", number);
	    		map.put("fileId", fileId);
	    		map.put("content", content);
	    		fileInfoEntityMapper.updateFileNumber(map);
	    		//切片成功 修改fileinfo表中文件识别状态
	    		fileInfoEntityMapper.updateFile(fileId);
	    		
	    	}
	    	
		}else {
			
			//上传语音文件不需要进行切片处理 直接进行语音识别并记录相关信息 （识别结果：result 切片数量：1）
			File dir2 = new File(fileinfo.getPath());   
    		JSONObject json = SpeechUtil.client.asr(dir2.getPath(), "pcm", 16000, null);
    		
    		//语音识别 获取json对象 从json中获取识别结果
    		JSONArray ja = json.optJSONArray("result");
    		String result = "";
    		if( ja!=null){
    			result = ja.toString();
    		}
    		//记录语音识别后相关信息
    		HashMap map = new HashMap();
    		map.put("number", 1);
    		map.put("content", result);
    		map.put("fileId", fileId);
    		fileInfoEntityMapper.updateFileNumber(map);
    		//修改文件识别状态
    		fileInfoEntityMapper.updateFile(fileId);
			
		}
		return new ModelAndView("redirect:./list");
	}
	
	//根据文件id查询分片
	@RequestMapping(value = "file/getContent")
	public ModelAndView getContent(Integer pieceId,Integer fileId) {
		if (pieceId <=0) {
			pieceId = 1;
		}
		Map<String,Object> infos = new HashMap<String,Object>();
		PieceInfoEntity list = speechModel.getPieceByPieceId(pieceId,fileId);
		if (list == null) {
			list = speechModel.getPieceByPieceId(pieceId-1,fileId);
			List<PieceInfoEntity> piecelist = speechModel.getPieceByFileId(fileId);
			StringBuffer permission = new StringBuffer();  
			for(PieceInfoEntity piece :piecelist) {
				permission.append(piece.getPieceContent()+",");
			}
			list.setId(list.getId()+1);
			list.setPieceContent(permission.toString());
		}
		Integer big = speechModel.getBig(fileId);
		Integer small = speechModel.getSmall(fileId);
		infos.put("data", list);
		infos.put("big", big);
		infos.put("small", small);
		return new ModelAndView("detail","info",infos);
	}
	
	//下载文件
	@RequestMapping("file/download")  
	public void fileDown(HttpServletRequest request,HttpServletResponse response,Integer pieceId,Integer fileId){  
	    //通过文件名找出文件的所在目录 
		String relpath="";
		if (pieceId != null) {
			PieceInfoEntity list = speechModel.getPieceById(pieceId);
			relpath = list.getPath();
		} else {
			FileInfoEntity fileinfo = speechModel.selectFileById(fileId);
			relpath = fileinfo.getPath();
		}
		
	    String path = request.getSession().getServletContext().getRealPath("D:\123.WAV");  
	    ServletOutputStream out;  
	    //得到要下载的文件  
	    File file = new File(relpath);  
	    try {  
	        //设置响应头，控制浏览器下载该文件  
	        response.setContentType("multipart/form-data");  
	        //获得浏览器信息,并处理文件名  
	        String headerType=request.getHeader("User-Agent").toUpperCase();  
	        String fileName = null;  
	        if (headerType.indexOf("EDGE") > 0||headerType.indexOf("MSIE")>0||headerType.indexOf("GECKO")>0) {    
	            fileName=URLEncoder.encode(file.getName(), "UTF-8");  
	        }else{  
	            fileName= new String(file.getName().replaceAll(" ", "").getBytes("utf-8"), "iso8859-1");  
	        }  
	        response.addHeader("Content-Disposition", "attachment;filename="+fileName);  
	        response.addHeader("Content-Length", "" + file.length());  
	        FileInputStream inputStream = new FileInputStream(file);  
	  
	        out = response.getOutputStream();  
	  
	        int b = 0;  
	        byte[] buffer = new byte[1024];  
	        while (b != -1) {  
	                b = inputStream.read(buffer);  
	                //写到输出流(out)中  
	                if(b!=-1)  
	                out.write(buffer, 0, b);  
	        }  
	        inputStream.close();  
	        out.close();//关闭输出流  
	        out.flush();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}  
}
