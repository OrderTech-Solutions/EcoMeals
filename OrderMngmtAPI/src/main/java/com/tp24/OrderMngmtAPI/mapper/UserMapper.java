package com.tp24.OrderMngmtAPI.mapper;

import com.tp24.OrderMngmtAPI.model.User;
import com.tp24.OrderMngmtAPI.rest.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}