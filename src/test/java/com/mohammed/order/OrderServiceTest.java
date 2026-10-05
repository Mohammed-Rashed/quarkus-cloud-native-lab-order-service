package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.entity.OrderEntity;
import com.mohammed.order.exception.OrderNotFoundException;
import com.mohammed.order.messaging.OrderEventProducer;
import com.mohammed.order.repository.OrderRepository;
import com.mohammed.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setup() {

        orderRepository = mock(OrderRepository.class);
        OrderEventProducer orderEventProducer = mock(OrderEventProducer.class);

        orderService = new OrderService(orderRepository, orderEventProducer);
    }
    @Test
    void shouldReturnOrderWhenOrderExists() {
        OrderEntity order = new OrderEntity();
        order.id = 1L;
        order.product = "iPhone";
        order.quantity = 2;
        order.status = "PENDING";
        when(orderRepository.findByIdOptional(1L))
                .thenReturn(Optional.of(order));

        var result = orderService.getOrderById(1L);
        assertEquals(1L, result.id());
        assertEquals("iPhone", result.product());
        assertEquals(2, result.quantity());
        assertEquals("PENDING", result.status());

        verify(orderRepository).findByIdOptional(1L);

    }
    @Test
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findByIdOptional(999L))
                .thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrderById(999L)
        );

        assertEquals(
                "Order with ID 999 not found",
                exception.getMessage()
        );

    }

    @Test
    void shouldCreateOrder() {
        CreateOrderDto dto =
                new CreateOrderDto(101L,"MacBook Pro", 2);

        orderService.create(dto);

        ArgumentCaptor<OrderEntity> captor =
                ArgumentCaptor.forClass(OrderEntity.class);

        verify(orderRepository).persist(captor.capture());
        OrderEntity savedOrder = captor.getValue();

        assertEquals("MacBook Pro", savedOrder.product);
        assertEquals(2, savedOrder.quantity);
        assertEquals("PENDING", savedOrder.status);

    }
}
