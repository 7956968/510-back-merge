package com.warmnut;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableTransactionManagement
//@PropertySource(value = "classpath:application.yml", encoding = "UTF-8", factory = YamlPropertyLoaderFactory.class)
public class Application extends SpringBootServletInitializer implements CommandLineRunner{

	@Value("${faceImagePath}")
	public  String faceImagePath;
	
	@Value("${tempimagePath}")
	public  String tempimagePath;
	
	public static void main(String[] args) {
//		args = new String[] { "-configfile", "src\\main\\resources\\mybatis-generator.xml", "-overwrite" };
//		ShellRunner.main(args);
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//创建程序需要的文件目录
		File file1 = new File(faceImagePath);		
		if(!file1.exists()) {
			file1.mkdirs();		
		}
		File file2 = new File(tempimagePath);		
		if(!file2.exists()) {
			file2.mkdirs();		
		}
	}
}
