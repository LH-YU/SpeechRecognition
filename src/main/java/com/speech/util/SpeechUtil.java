package com.speech.util;


import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.baidu.aip.speech.AipSpeech;

@Component
public class SpeechUtil {
	private static Logger logger = Logger.getLogger(SpeechUtil.class);
	public static String APP_ID = "11073053";
    public static String API_KEY = "C85lZa7NCBql2NcRK46rElAO";
    public static String SECRET_KEY = "C2mjalpMLxfQD2eVPLCnv4m08s81qxkT";
    public static AipSpeech client;
    @PostConstruct
	public static void convert() {
    	logger.info("百度环境初始化开始");
        client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
		client.setConnectionTimeoutInMillis(5000);
        client.setSocketTimeoutInMillis(60000);
		//JSONObject res = client.asr(path, "pcm", 16000, null);
		//System.out.println(res.toString(2));
        logger.info("百度环境初始化结束");
	}
}
