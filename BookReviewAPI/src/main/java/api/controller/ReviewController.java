package api.controller;

import api.model.AverageDTO;
import api.model.Review;
import api.model.ReviewDTO;
import api.model.ReviewRepository;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/review", produces = "application/hal+json")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody Review review) {
        Link selfLink;
        if (ISBNValidator.getInstance().isValidISBN13(review.isbn13)) {
            try{
                Review saved = reviewRepository.save(review);
                selfLink = linkTo(ReviewController.class).slash(review.getId()).withSelfRel();
                ReviewDTO reviewDTO = new ReviewDTO(selfLink, review);
                return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
            } catch (Exception ex){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<CollectionModel<ReviewDTO>> getReviewsFromISBN(@PathVariable String isbn) {
        if (ISBNValidator.getInstance().isValidISBN13(isbn)) {
            try {
                List<Review> reviews = reviewRepository.findAllByIsbn13(isbn);
                return getCollectionModelResponseEntity(reviews);
            }catch (Exception ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("")
    public ResponseEntity<CollectionModel<ReviewDTO>> getReviews(@PathVariable String isbn) {
        try {
            List<Review> reviews = reviewRepository.findAll();
            return getCollectionModelResponseEntity(reviews);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/average/{isbn}")
    public ResponseEntity<AverageDTO> getAverageRatingFromISBN(@PathVariable String isbn){
        if (ISBNValidator.getInstance().isValidISBN13(isbn)) {
            try {
                List<Review> reviews = reviewRepository.findAllByIsbn13(isbn);
                Double average = 0.0;
                if(reviews.size() != 0) {
                    Double total = 0.0;
                    for (Review review: reviews){
                        total += review.getRating().getValue();
                    }
                    average = total/reviews.size();
                }
                return new ResponseEntity<>(new AverageDTO(reviews.size(), average, isbn), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable String id) {
        try {
            Review review = reviewRepository.findById(id).get();
            if (review!= null) {
                reviewRepository.delete(review);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<CollectionModel<ReviewDTO>> getCollectionModelResponseEntity(List<Review> reviews) {
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(Review review: reviews){
            Link reviewLink = linkTo(ReviewController.class).slash(review.getId()).withSelfRel();
            reviewDTOS.add(new ReviewDTO(reviewLink, review));
        }
        Link link = linkTo(ReviewController.class).withSelfRel();
        CollectionModel<ReviewDTO> result = CollectionModel.of(reviewDTOS,link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}