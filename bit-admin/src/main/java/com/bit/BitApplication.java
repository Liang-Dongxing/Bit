package com.bit;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 启动程序
 *
 * @author bit
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableWebSecurity
@EnableCaching
@EnableWebMvc
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.bit.**.mapper")
@Slf4j
public class BitApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BitApplication.class, args);
        log.info("({}◠‿◠)ﾉﾞ  {} 启动成功   ლ(´ڡ`ლ)ﾞ", AnsiOutput.toString(AnsiColor.RED, "♥"), run.getEnvironment().getProperty("bit.name"));
    }
}
