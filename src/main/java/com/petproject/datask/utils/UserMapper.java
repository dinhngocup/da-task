package com.petproject.datask.utils;

import com.petproject.datask.dto.UserDTO;
import com.petproject.datask.entity.User;

public class UserMapper {

    public static UserDTO mapEntityToDto(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .roleId(user.getRoleId())
                .build();
    }

    public static User mapDtoToEntity(UserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .roleId(dto.getRoleId())
                .pswd(dto.getPswd())
                .build();
    }
}
