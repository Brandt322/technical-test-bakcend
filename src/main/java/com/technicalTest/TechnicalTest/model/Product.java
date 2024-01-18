package com.technicalTest.TechnicalTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Price is required")
    @Column(nullable = false)
    private double unitPrice;

    @NotNull(message = "Quantity is required")
    @Column(nullable = false)
    private int quantity;

    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    private List<Order> orders;

}
