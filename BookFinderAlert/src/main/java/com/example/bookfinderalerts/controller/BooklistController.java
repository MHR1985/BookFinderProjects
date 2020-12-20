package com.example.bookfinderalerts.controller;

import com.example.bookfinderalerts.dto.BookListing;
import com.example.bookfinderalerts.dto.PriceListing;
import com.example.bookfinderalerts.kafka.ProducerServiceCallback;
import com.example.bookfinderalerts.scraber.GoogleScraper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@RestController
public class BooklistController {

    @Autowired
    private ProducerServiceCallback service;

    @PostMapping("/{username}")
    public ResponseEntity<String> postBookList(@RequestBody String[] subscribedBooks, @PathVariable String username){
        try{
            GoogleScraper scraper = new GoogleScraper();
            List<BookListing> bookListing = new ArrayList<>();
            for(String book:subscribedBooks){
                bookListing.add(scraper.scrape(book));
            }
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            service.sendMessageCallBack(username,gson.toJson(bookListing));
            return new ResponseEntity<>("All went well", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
