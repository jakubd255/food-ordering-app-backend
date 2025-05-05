package pl.jakubdudek.foodorderingappbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "basket_items")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer variant;
}
