package com.project.code.Controller;

import com.project.code.Model.Customer;
import com.project.code.Model.Review;
import com.project.code.Repository.CustomerRepository;
import com.project.code.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId, @PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);
        List<Map<String, Object>> formattedReviews = new ArrayList<>();

        for (Review review : reviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("comment", review.getComment());
            reviewMap.put("rating", review.getRating());

            String customerName = "Desconocido";
            if (review.getCustomerId() != null) {
                Customer customer = customerRepository.findById(review.getCustomerId());
                if (customer != null && customer.getName() != null) {
                    customerName = customer.getName();
                }
            }
            reviewMap.put("customerName", customerName);
            formattedReviews.add(reviewMap);
        }

        response.put("reviews", formattedReviews);
        return response;
    }
}
