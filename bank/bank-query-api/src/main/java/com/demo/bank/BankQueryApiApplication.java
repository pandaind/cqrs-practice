package com.demo.bank;

import com.demo.bank.core.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class})
public class BankQueryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankQueryApiApplication.class, args);
    }

}
