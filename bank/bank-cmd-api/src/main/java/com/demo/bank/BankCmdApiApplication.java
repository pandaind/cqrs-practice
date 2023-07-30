package com.demo.bank;

import com.demo.bank.core.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({AxonConfig.class})
public class BankCmdApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankCmdApiApplication.class, args);
    }

}
