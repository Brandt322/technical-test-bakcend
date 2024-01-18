package com.technicalTest.TechnicalTest.repository;

import com.technicalTest.TechnicalTest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
