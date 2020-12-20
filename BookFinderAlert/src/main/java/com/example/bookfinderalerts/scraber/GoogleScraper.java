package com.example.bookfinderalerts.scraber;

import com.example.bookfinderalerts.dto.BookListing;
import com.example.bookfinderalerts.dto.PriceListing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GoogleScraper {

    public BookListing scrabeOld(String id) throws MalformedURLException {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        WebDriver driver = new FirefoxDriver(options);
        driver.get("https://books.google.dk/books?id="+id+"&sitesec=buy&hl=da&source=gbs_vpt_buy");
        //WebElement elementDiv = driver.findElement(By.xpath("//div[@id='summary_content']"));
        WebElement elementDiv = driver.findElement(By.id("summary_content"));
        elementDiv.findElement(By.tagName("table"));
        List<WebElement> list = elementDiv.findElements(By.tagName("tr"));
        List<PriceListing> prices = new ArrayList<>();
        for(int i = 1;i<list.size();i++) {
            WebElement elementTr = list.get(i);
            List<WebElement> elementsTd = elementTr.findElements(By.tagName("td"));
            String seller = elementsTd.get(0).getText();
            String price = elementsTd.get(1).getText();
            String sellerLink = elementsTd.get(0).findElement(By.tagName("a")).getAttribute("href");
            prices.add(new PriceListing(price,sellerLink,seller));
        }
        String bookTitle = driver.findElement(By.className("gb-volume-title")).getText();
        BookListing bookListing = new BookListing(id,bookTitle,prices);
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        System.out.println(gson.toJson(bookListing));
        return bookListing;
    }

    public BookListing scrape(String id) throws IOException {
        Document doc = Jsoup.connect("https://books.google.dk/books?id="+id+"&sitesec=buy&hl=da&source=gbs_vpt_buy").get();
        Element elementDiv = doc.getElementById("summary_content");
        Elements list = elementDiv.getElementsByTag("tr");
        List<PriceListing> prices = new ArrayList<>();
        for(int i = 1;i<list.size();i++) {
            Element elementTr = list.get(i);
            Elements elementsTd = elementTr.getElementsByTag("td");
            String seller = elementsTd.get(0).text();
            String price = elementsTd.get(1).text();
            String sellerLink = elementsTd.get(0).getElementsByTag("a").attr("abs:href");
            prices.add(new PriceListing(price,sellerLink,seller));
        }
        String bookTitle = doc.getElementsByClass("gb-volume-title").text();
        BookListing bookListing = new BookListing(id,bookTitle,prices);
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        System.out.println(gson.toJson(bookListing));
        return bookListing;
    }

}
