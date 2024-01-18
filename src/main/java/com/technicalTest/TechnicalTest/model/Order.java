package com.technicalTest.TechnicalTest.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int numberOrder;

    @JsonFormat(pattern = "YYYY-MM-dd")
    @Column(nullable = false)
    private Date date;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns =  @JoinColumn(name = "product_id", referencedColumnName = "id")
    )

    private List<Product> products;

    @Column(nullable = false)
    private double finalPrice;
}
