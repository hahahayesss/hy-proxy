package com.r00t.hyproxy;

import com.r00t.hyproxy.anno.EnableHyProxies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableHyProxies
public class HyProxyApplication {

    @Bean
    public CommandLineRunner runner(DemoRequests requests) {
        return args -> {
            requests.getList("URL", "TOKEN");
            requests.getPage("URL", "TOKEN", 1);
            requests.getOne("URL", "TOKEN", "qwe123");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(HyProxyApplication.class, args);
    }
}
