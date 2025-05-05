package pl.jakubdudek.foodorderingappbackend.model.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderStatus;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderType;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItem> items;

    private Date date;

    private Float total;

    private String message;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @PrePersist
    public void onCreate() {
        this.date = new Date();

        float price = 0;

        for(BasketItem item : items) {
            Variant variant = item.getProduct().getVariants().get(item.getVariant());
            price += variant.getPrice();
        }

        this.total = price;
    }
}
