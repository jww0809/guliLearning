package com.junwei.educenter;


import com.junwei.educenter.entity.UcenterMember;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.junwei")
@SpringBootApplication
@MapperScan("com.junwei.educenter.mapper")  //要用到mapper目录下的.xml文件
public class UcenterMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMember.class,args);
    }
}
