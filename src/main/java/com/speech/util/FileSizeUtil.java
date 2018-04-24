package com.speech.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FileSizeUtil {

	public static String countFileSize(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		File file = new File(path); 
		Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(file);
		clip.open(ais);
		double s = clip.getMicrosecondLength() / 1000000D;
		Integer min = (int) (s/60);
		Integer sec = (int) (s- min*60);
		String timeSize ="00:"+min+":"+sec;
		System.out.println(timeSize);
		return timeSize;
	}
	
	//上传文件进行切片的时候 转换文件 格式
	public static String convertToPcm(String path,String name) {
		//String exe="ffmpeg -y  -i "+path+"  -acodec pcm_s16le -f s16le -ac 1 -ar 16000 d:\\test\\REC003.pcm"; 
		String newPath = "speech\\"+ name;
		String exe="ffmpeg -y  -i "+path+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 speech\\"+ name;
		String cmd = "cmd.exe /c start /b "+exe;   
		Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象  
        try {  
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return newPath;  
	}
	
	/*public static void main(String[] args) {
		String path ="D:\\sdk_demo\\SpeechRecognition\\speech\\Test1.WAV"; 
		String filename = "Test1.WAV";
		String newFilename = filename.replace("WAV", "pcm");
		System.out.println("path:" + path);
		System.out.println("newFilename:" + newFilename);
		String exe="ffmpeg -y  -i "+path+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 D:\\sdk_demo\\SpeechRecognition\\speech\\"+ newFilename;
		System.out.println("exe:" + exe);
		String cmd = "cmd.exe /c start /b "+exe;   
		Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象  
	        try {  
	            Process p = run.exec(cmd);// 启动另一个进程来执行命令
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	}*/
}
