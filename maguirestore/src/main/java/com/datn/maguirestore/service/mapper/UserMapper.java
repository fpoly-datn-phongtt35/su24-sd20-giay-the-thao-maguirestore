package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.UserDTO;
import com.datn.maguirestore.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public UserDTO toDtoId(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("firstName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName", source = "firstName")
    public UserDTO toDtoFirstName(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("firstNameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName", source = "firstName")
    public Set<UserDTO> DtoFirstNameSet(Set<User> users) {
        if (users == null) {
            return Collections.emptySet();
        }
        Set<UserDTO> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoFirstName(userEntity));
        }
        return userSet;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<UserDTO> toDtoIdSet(Set<User> users) {
        if (users == null) {
            return Collections.emptySet();
        }
        Set<UserDTO> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoId(userEntity));
        }
        return userSet;
    }
}
