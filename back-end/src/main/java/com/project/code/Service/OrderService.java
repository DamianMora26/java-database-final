package com.project.code.Service;

import com.project.code.Model.*;
import com.project.code.Repository.*;
import com.project.code.dto.PlaceOrderRequestDTO;
import com.project.code.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 1. Recuperar o Crear Cliente
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getEmail());
        if (customer == null) {
            customer = new Customer(placeOrderRequest.getName(), placeOrderRequest.getEmail(), placeOrderRequest.getPhone());
            customer = customerRepository.save(customer);
        }

        // 2. Recuperar Tienda
        Store store = storeRepository.findById(placeOrderRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // 3. Crear OrderDetails y guardar utilizando el resultado guardado
        OrderDetails orderDetails = new OrderDetails(
                customer,
                store,
                placeOrderRequest.getTotalPrice(),
                LocalDateTime.now()
        );
        OrderDetails savedOrderDetails = orderDetailsRepository.save(orderDetails);

        // 4. Reducir stock de inventario y guardar el inventario actualizado
        if (placeOrderRequest.getPurchaseProduct() != null) {
            for (ProductDTO productDTO : placeOrderRequest.getPurchaseProduct()) {
                Inventory inventory = inventoryRepository.findByProductIdandStoreId(productDTO.getProductId(), store.getId());
                if (inventory != null) {
                    inventory.setStockLevel(inventory.getStockLevel() - productDTO.getQuantity());
                    inventoryRepository.save(inventory);
                }

                Product product = productRepository.findById(productDTO.getProductId()).orElse(null);
                if (product != null) {
                    OrderItem orderItem = new OrderItem(savedOrderDetails, product, productDTO.getQuantity(), product.getPrice());
                    orderItemRepository.save(orderItem);
                }
            }
        }
    }
}
