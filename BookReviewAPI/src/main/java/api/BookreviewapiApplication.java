package api;

import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookreviewapiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context=SpringApplication.run(BookreviewapiApplication.class, args);
    }

}
