package com.tp24.OrderMngmtAPI.mapper;

import com.tp24.OrderMngmtAPI.model.Order;
import com.tp24.OrderMngmtAPI.rest.dto.CreateOrderRequest;
import com.tp24.OrderMngmtAPI.rest.dto.OrderDto;

public interface OrderMapper {

    Order toOrder(CreateOrderRequest createOrderRequest);

    OrderDto toOrderDto(Order order);
}