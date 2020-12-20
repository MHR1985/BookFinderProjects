package api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

public class Review {

    @Id
    public String id;
    public String isbn13;
    public Rating rating;

    public enum Rating {
        ONE(1.0),
        TWO(2.0),
        THREE(3.0),
        FOUR(4.0),
        FIVE(5.0);

        private Double value;

        Rating(Double value) {
            this.value = value;
        }

        public Double getValue() {
            return value;
        }
    }

    public Review(String isbn13, Rating rating) {
        this.isbn13 = isbn13;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}
