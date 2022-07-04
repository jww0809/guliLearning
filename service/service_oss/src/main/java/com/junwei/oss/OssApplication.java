package com.junwei.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//默认不去加载数据库的配置，否则启动报错
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@ComponentScan("com.junwei")
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }

}
