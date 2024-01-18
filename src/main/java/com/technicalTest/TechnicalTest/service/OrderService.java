package com.technicalTest.TechnicalTest.service;

import com.technicalTest.TechnicalTest.model.Order;
import com.technicalTest.TechnicalTest.model.Product;
import com.technicalTest.TechnicalTest.repository.OrderRepository;
import com.technicalTest.TechnicalTest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order, List<Long> productIds) {
        List<Product> products = getProductsByIds(productIds);
        double finalPrice = calculateFinalPrice(products);

        Optional<Order> existingOrder = orderRepository.findByNumberOrder(order.getNumberOrder());
        if (existingOrder.isPresent()) {
            throw new IllegalArgumentException("This order already exists");
        }

        order.setProducts(products);
        order.setFinalPrice(finalPrice);
        order.setDate(new Date());

        Order savedOrder = orderRepository.save(order);

        // Save each product with the new order
        for (Product product : products) {
            product.getOrders().add(savedOrder);
            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);
        }

        return savedOrder;
    }

    public Order updateOrder(Long id, int numberOrder, List<Long> productIds) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<Product> products = getProductsByIds(productIds);
        double finalPrice = calculateFinalPrice(products);

        order.setNumberOrder(numberOrder);
        order.setFinalPrice(finalPrice);
        order.setDate(new Date());
        order.setProducts(products);

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private List<Product> getProductsByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    private double calculateFinalPrice(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getUnitPrice)
                .sum();
    }
}
