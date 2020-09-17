package com.kinglead.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication注解代表这是一个spring boot应用
//它是一个组合注解
//@SpringBootConfiguration注解将该类声明为配置类，相当于@Configuration的特殊形式
//@EnableAutoConfiguration启动spring boot的自动配置
//@ComponentScan启动组件扫描：将通过@Component、@Controller、@Service这样注解的类，注册为spring应用上下文的组件
@MapperScan("com.kinglead.demo.dao")  //Mapper全局扫描
@SpringBootApplication
public class SpringInitDemoApplication {

	/**
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringInitDemoApplication.class, args);
	}

}
