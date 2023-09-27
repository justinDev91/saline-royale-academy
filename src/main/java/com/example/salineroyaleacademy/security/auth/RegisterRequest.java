package com.example.salineroyaleacademy.security.auth;

import com.example.salineroyaleacademy.user.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.salineroyaleacademy.user.Role.USER;

@Data
@Builder
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "First name is required and cannot be empty.")
  @NotNull(message = "First name is required and cannot be null.")
  @Size(max = 20, message = "First name cannot exceed 20 characters.")
  private String firstname;

  @NotBlank(message = "Last name is required and cannot be empty.")
  @NotNull(message = "Last name is required and cannot be null.")
  @Size(max = 20, message = "Last name cannot exceed 20 characters.")
  private String lastname;

  @Size(max = 50, message = "Email cannot exceed 50 characters.")
  @Email(message = "Invalid email format.")
  private String email;

  @NotBlank(message = "Password is required and cannot be empty.")
  @Size(max = 120, message = "Password cannot exceed 120 characters.")
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role = USER;
}
