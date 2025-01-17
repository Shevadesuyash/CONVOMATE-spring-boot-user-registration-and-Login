package net.enjoy.springboot.registrationlogin.service;

import java.util.List;
import java.util.stream.Collectors;
import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.Role;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.repository.RoleRepository;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Service class implementing UserService interface
@Service
@Transactional
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  // Constructor-based dependency injection
  public UserServiceImpl(
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // Method to save user
  @Override
  public void saveUser(UserDto userDto) {
    // Create user entity object and populate with user DTO data
    User user = new User();
    user.setName(userDto.getFirstName() + " " + userDto.getLastName());
    user.setEmail(userDto.getEmail());
    // Encrypt the password using Spring Security's password encoder
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));

    // Check if ROLE_USER exists, otherwise create it
    Role role = roleRepository.findByName("ROLE_USER");
    if (role == null) {
      role = checkRoleExist();
    }
    user.setRoles(List.of(role));
    // Save the user to the repository
    userRepository.save(user);
  }

  // Method to check if ROLE_USER exists, otherwise create it
  private Role checkRoleExist() {
    Role role = new Role();
    role.setName("ROLE_USER");
    return roleRepository.save(role);
  }

  // Method to find user by email
  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  // Method to find all users and convert them to DTOs
  @Override
  public List<UserDto> findAllUsers() {
    // Retrieve all users from the repository
    List<User> users = userRepository.findAll();
    // Convert user entities to user DTOs
    return users.stream().map((user) -> convertEntityToDto(user)).collect(Collectors.toList());
  }

  // Method to convert user entity to DTO
  private UserDto convertEntityToDto(User user) {
    UserDto userDto = new UserDto();
    // Split full name into first name and last name
    String[] name = user.getName().split(" ");
    userDto.setFirstName(name[0]);
    userDto.setLastName(name[1]);
    userDto.setEmail(user.getEmail());
    return userDto;
  }
}
