package com.tp24.OrderMngmtAPI.serviceTests;


import com.tp24.OrderMngmtAPI.exception.OrderNotFoundException;
import com.tp24.OrderMngmtAPI.model.Order;
import com.tp24.OrderMngmtAPI.repository.OrderRepository;
import com.tp24.OrderMngmtAPI.service.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("1");
        order.setDescription("Sample Order");
    }

    @Test
    void getOrders() {
        List<Order> orders = List.of(order);
        when(orderRepository.findAllByOrderByCreatedAtDesc()).thenReturn(orders);

        List<Order> result = orderService.getOrders();

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
        verify(orderRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    void getOrdersContainingText() {
        List<Order> orders = List.of(order);
        when(orderRepository.findByIdContainingOrDescriptionContainingIgnoreCaseOrderByCreatedAt(anyString(), anyString())).thenReturn(orders);

        List<Order> result = orderService.getOrdersContainingText("Sample");

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
        verify(orderRepository, times(1)).findByIdContainingOrDescriptionContainingIgnoreCaseOrderByCreatedAt(anyString(), anyString());
    }

    @Test
    void validateAndGetOrder_found() {
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));

        Order result = orderService.validateAndGetOrder("1");

        assertEquals(order, result);
        verify(orderRepository, times(1)).findById("1");
    }

    @Test
    void validateAndGetOrder_notFound() {
        when(orderRepository.findById("1")).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderService.validateAndGetOrder("1"));
        
        assertEquals("Order with id 1 not found", exception.getMessage());
        verify(orderRepository, times(1)).findById("1");
    }

    @Test
    void saveOrder() {
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.saveOrder(order);

        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void deleteOrder() {
        doNothing().when(orderRepository).delete(order);

        orderService.deleteOrder(order);

        verify(orderRepository, times(1)).delete(order);
    }
}
