package com.example.bookfinderalerts;

import com.example.bookfinderalerts.scraber.GoogleScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.MalformedURLException;

@SpringBootApplication
public class BookfinderalertsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookfinderalertsApplication.class, args);
    }


}
