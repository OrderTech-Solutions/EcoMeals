package com.tp24.OrderMngmtAPI.mapper;

import com.tp24.OrderMngmtAPI.model.Order;
import com.tp24.OrderMngmtAPI.rest.dto.CreateOrderRequest;
import com.tp24.OrderMngmtAPI.rest.dto.OrderDto;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toOrder(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest == null) {
            return null;
        }
        return new Order(createOrderRequest.getDescription());
    }

    @Override
    public OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto.UserDto userDto = new OrderDto.UserDto(order.getUser().getUsername());
        return new OrderDto(order.getId(), order.getDescription(), userDto, order.getCreatedAt());
    }
}
