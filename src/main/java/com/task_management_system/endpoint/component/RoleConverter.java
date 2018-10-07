package com.task_management_system.endpoint.component;

import com.task_management_system.endpoint.model.RoleResponseDto;
import com.task_management_system.entity.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleConverter {

    public List<RoleResponseDto> convertEntityToDtoList(final List<Role> roles){
        final List<RoleResponseDto> roleResponseDtoList = new ArrayList<>();
        for (Role role: roles) {
            roleResponseDtoList.add(convertEntityToDto(role));
        }
        return roleResponseDtoList;
    }

    private RoleResponseDto convertEntityToDto(final Role role){
        final String name = role.getType().name();
        final String id = role.getId();
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setId(id);
        roleResponseDto.setRoleName(name);
        return roleResponseDto;
    }
}
