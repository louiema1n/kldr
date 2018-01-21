package com.lm.kldr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lm.kldr.mapper")
public class KldrApplication {

	public static void main(String[] args) {
		SpringApplication.run(KldrApplication.class, args);
	}
}
