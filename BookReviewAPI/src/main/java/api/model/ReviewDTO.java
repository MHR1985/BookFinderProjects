package api.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class ReviewDTO extends RepresentationModel<ReviewDTO> {
    Review review;

    public ReviewDTO(Review review) {
        this.review = review;
    }

    public ReviewDTO(Link initialLink, Review review) {
        super(initialLink);
        this.review = review;
    }

    public ReviewDTO(Iterable<Link> initialLinks, Review review) {
        super(initialLinks);
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
