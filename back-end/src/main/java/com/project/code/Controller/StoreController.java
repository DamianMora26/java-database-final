package com.project.code.Controller;

import com.project.code.Model.Store;
import com.project.code.Repository.StoreRepository;
import com.project.code.Service.OrderService;
import com.project.code.dto.PlaceOrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, String>> addStore(@RequestBody Store store) {
        Store savedStore = storeRepository.save(store);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tienda creada con éxito con ID: " + savedStore.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("validate/{storeId}")
    public ResponseEntity<Boolean> validateStore(@PathVariable Long storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        if (store.isPresent()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @GetMapping("validate/store/{storeId}")
    public ResponseEntity<Boolean> validateStorePath(@PathVariable Long storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        if (store.isPresent()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            orderService.saveOrder(placeOrderRequest);
            response.put("message", "Pedido realizado con éxito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("Error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
