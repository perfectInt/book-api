package io.sultanov.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "io.sultanov.bookservice",
                "io.sultanov.feignclients"
        }
)
@EnableFeignClients(
        basePackages = "io.sultanov.feignclients"
)
@PropertySources({
        @PropertySource("classpath:clients-default.properties")
})
public class BookServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }
}
