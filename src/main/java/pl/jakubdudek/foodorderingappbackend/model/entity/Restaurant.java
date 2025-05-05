package pl.jakubdudek.foodorderingappbackend.model.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;
import pl.jakubdudek.foodorderingappbackend.model.json.WorkingHours;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Address address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    private String mainPhoto;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> photos;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private WorkingHours workingHours;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders;
}
