package com.example.bookfinderalerts.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookListing implements Serializable {

    private String bookId;

    private String bookTitle;

    private List<PriceListing> priceListings = new ArrayList<>();

    public BookListing(String bookId, String bookTitle, List<PriceListing> priceListings) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.priceListings = priceListings;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public List<PriceListing> getPriceListings() {
        return priceListings;
    }

    public void setPriceListings(List<PriceListing> priceListings) {
        this.priceListings = priceListings;
    }
}
