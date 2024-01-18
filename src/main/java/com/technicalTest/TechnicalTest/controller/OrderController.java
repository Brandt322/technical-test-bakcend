package com.technicalTest.TechnicalTest.controller;

import com.technicalTest.TechnicalTest.model.Order;
import com.technicalTest.TechnicalTest.repository.ProductRepository;
import com.technicalTest.TechnicalTest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> payload) {
        Order order = new Order();

        List<Integer> productIdsInt = (List<Integer>) payload.get("productIds");
        List<Long> productIds = productIdsInt.stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());

        int numberOrder = (int) payload.get("numberOrder");
        order.setNumberOrder(numberOrder);

        order = orderService.saveOrder(order, productIds);

        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("editOrder/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        List<Integer> productIdsInt = (List<Integer>) payload.get("productIds");

        List<Long> productIds = productIdsInt.stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());

        Integer numberOrderObj = (Integer) payload.get("numberOrder");
        if (numberOrderObj == null) {
            throw new IllegalArgumentException("numberOrder is required");
        }
        int numberOrder = numberOrderObj.intValue();

        Order updatedOrder = orderService.updateOrder(id, numberOrder, productIds);

        return ResponseEntity.ok(updatedOrder);
    }
}
