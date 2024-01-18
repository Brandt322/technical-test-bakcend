package com.technicalTest.TechnicalTest.repository;

import com.technicalTest.TechnicalTest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByNumberOrder(int numberOrder);
}
