package com.speech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@EnableAutoConfiguration
@ComponentScan("com.speech")
@ImportResource("classpath:applicationContext.xml")
public class SpeechServer {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpeechServer.class, args);
	}

}
