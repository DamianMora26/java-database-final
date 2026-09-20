package com.project.code.Controller;
import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import com.project.code.Model.PlaceOrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired private StoreRepository storeRepository;
    @Autowired private OrderService orderService;

    @GetMapping("/validate/store/{storeId}")
    public ResponseEntity<Boolean> validateStore(@PathVariable Long storeId) {
        return storeRepository.findById(storeId).isPresent() ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody PlaceOrderRequestDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            orderService.saveOrder(request);
            response.put("message", "Pedido realizado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addStore(@RequestBody Store store) {
        Store savedStore = storeRepository.save(store);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tienda creada con éxito con ID: " + savedStore.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/validate/{storeId}")
    public ResponseEntity<Boolean> validateStoreAlt(@PathVariable Long storeId) {
        return storeRepository.findById(storeId).isPresent() ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
}
