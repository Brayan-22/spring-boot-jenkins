package dev.alejandro.jenkinsproject.service;

import dev.alejandro.jenkinsproject.dto.OrderDto;
import dev.alejandro.jenkinsproject.entity.OrderEntity;
import dev.alejandro.jenkinsproject.exceptions.OrderNotCreatedException;
import dev.alejandro.jenkinsproject.exceptions.OrderNotFoundException;
import dev.alejandro.jenkinsproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderDto createOrder(OrderDto orderDto) throws OrderNotCreatedException {
        if (orderDto == null) throw new OrderNotCreatedException("Order not created");
        if (orderDto.getProductName() == null || orderDto.getProductName().isEmpty())
            throw new OrderNotCreatedException("Product name is required");
        if (orderDto.getPrice() == 0) throw new OrderNotCreatedException("Price is required");
        if (orderDto.getQuantity() == 0) throw new OrderNotCreatedException("Quantity is required");
        OrderEntity order = orderRepository.save(OrderEntity.builder()
                        .productName(orderDto.getProductName())
                        .price(orderDto.getPrice())
                        .quantity(orderDto.getQuantity())
                .build());
        return OrderDto.builder()
                .productName(order.getProductName())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }
    public OrderDto getOrderById(Integer id) throws OrderNotFoundException {
        OrderEntity order = orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order not found"));
        return OrderDto.builder()
                .productName(order.getProductName())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }
    public void deleteOrder(Integer id) throws OrderNotFoundException {
        orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order don't exist"));
        orderRepository.deleteById(id);
    }

    public OrderDto updateOrder(Integer id, OrderDto orderDto) throws OrderNotFoundException {
        OrderEntity order = orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order not found"));
        order.setProductName(orderDto.getProductName());
        order.setPrice(orderDto.getPrice());
        order.setQuantity(orderDto.getQuantity());
        orderRepository.save(order);
        return OrderDto.builder()
                .productName(order.getProductName())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }
    public List<OrderDto> getAllOrders() throws OrderNotFoundException {
        List<OrderDto> orders = orderRepository.findAll().stream().map(order -> OrderDto.builder()
                .productName(order.getProductName())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .build()).toList();
        if (orders.isEmpty()) throw new OrderNotFoundException("No orders found");
        return orders;
    }
}
