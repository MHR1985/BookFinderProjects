package com.example.bookfinderalerts.dto;

import java.io.Serializable;

public class PriceListing implements Serializable {

    private String price;

    private String link;

    private String seller;

    public PriceListing(String price, String link, String seller) {
        this.price = price;
        this.link = link;
        this.seller = seller;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
