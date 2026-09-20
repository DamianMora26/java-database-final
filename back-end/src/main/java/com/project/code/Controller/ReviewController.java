package com.project.code.Controller;
import com.project.code.Model.*;
import com.project.code.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private CustomerRepository customerRepository;

    @GetMapping
    public Map<String, Object> getAllReviews() {
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewRepository.findAll());
        return response;
    }

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
                if (customer != null && customer.getName() != null) customerName = customer.getName();
            }
            reviewMap.put("customerName", customerName);
            formattedReviews.add(reviewMap);
        }
        response.put("reviews", formattedReviews);
        return response;
    }
}
