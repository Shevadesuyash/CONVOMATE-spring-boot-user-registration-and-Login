package net.enjoy.springboot.registrationlogin.service;

import java.util.List;
import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.User;

public interface UserService {
  void saveUser(UserDto userDto);

  User findUserByEmail(String email);

  List<UserDto> findAllUsers();
}
