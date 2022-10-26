package com.bit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 启动程序
 *
 * @author bit
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableWebMvc
public class BitApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(BitApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Bit 启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
