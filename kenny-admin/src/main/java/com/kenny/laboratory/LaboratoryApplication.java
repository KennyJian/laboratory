package com.kenny.laboratory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot方式启动类
 *
 * @author kenny
 * @Date 2018/10/30 12:06
 */
@SpringBootApplication
public class LaboratoryApplication {

    private final static Logger logger = LoggerFactory.getLogger(LaboratoryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryApplication.class, args);
        logger.info("江苏科技大学实验室预约管理系统启动成功");
    }
}
