package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonManagedReference
    private Store store;

    private Double totalPrice;

    /**
     * Date and time when the order was placed.
     */
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public OrderDetails() {}

    public OrderDetails(Customer customer, Store store, Double totalPrice, LocalDateTime orderDate) {
        this.customer = customer;
        this.store = store;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    // Compatibilidad por si algún componente busca getDate()
    public LocalDateTime getDate() { return orderDate; }
    public void setDate(LocalDateTime date) { this.orderDate = date; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}
