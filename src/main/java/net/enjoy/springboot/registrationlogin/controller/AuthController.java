package net.enjoy.springboot.registrationlogin.controller;

import jakarta.validation.Valid;
import java.util.List;
import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  // Handler method to serve the home page
  @GetMapping("/index")
  public String home() {
    return "index";
  }

  // Handler method to display the user registration form
  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    // Create a new UserDto object to store form data
    UserDto user = new UserDto();
    model.addAttribute("user", user);
    return "register";
  }

  // Handler method to process user registration form submission
  @PostMapping("/register/save")
  public String registration(
      @Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
    // Check if user already exists with the same email
    User existingUser = userService.findUserByEmail(userDto.getEmail());

    if (existingUser != null
        && existingUser.getEmail() != null
        && !existingUser.getEmail().isEmpty()) {
      result.rejectValue(
          "email", null, "There is already an account registered with the same email");
    }

    // If there are validation errors, return back to the registration form
    if (result.hasErrors()) {
      model.addAttribute("user", userDto);
      return "/register";
    }

    // Save the user and redirect to registration form with success message
    userService.saveUser(userDto);
    return "redirect:/register?success";
  }

  // Handler method to display a list of users
  @GetMapping("/users")
  public String users(Model model) {
    List<UserDto> users = userService.findAllUsers();
    model.addAttribute("users", users);
    return "users";
  }

  // Handler method to serve the login page
  @GetMapping("/login")
  public String login() {
    return "login";
  }

  // Handler method to serve the model page
  @GetMapping("/model")
  public String modelPage(Model model) {
    return "model";
  }

  @GetMapping("/Translator")
  public String getModelPage() {
    // Redirect to Flask app when accessing model.html
    return "redirect:http://localhost:5000/";
  }
}
