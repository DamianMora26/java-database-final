package com.project.code.Model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Customer {
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderDetails> orders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "El nombre no puede ser nulo")
    private String name;
    @NotNull(message = "El email no puede ser nulo")
    private String email;
    @NotNull(message = "El teléfono no puede ser nulo")
    private String phone;

    public Customer() {}
    public Customer(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<OrderDetails> getOrders() { return orders; }
    public void setOrders(List<OrderDetails> orders) { this.orders = orders; }
}
