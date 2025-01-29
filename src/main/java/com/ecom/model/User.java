package com.ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @NotNull(message = "Full Name cannot be null")
    @Size(min = 3, max = 100, message = "Full Name must be between 3 and 100 characters")
    private String fullName;

    @Column(length = 15, unique = true)
    @NotNull(message = "Mobile Number cannot be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @Column(length = 100, unique = true)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Column(length = 255)
    @NotNull(message = "Address cannot be null")
    private String address;

    @Column(length = 50)
    @NotNull(message = "City cannot be null")
    private String city;

    @Column(length = 50)
    @NotNull(message = "State cannot be null")
    private String state;

    @Column(length = 10)
    @NotNull(message = "Pincode cannot be null")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
    private String pincode;

    @Column(length = 100)
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    @Transient // This field will not be persisted in the database
    @NotNull(message = "Confirm Password cannot be null")
    @Size(min = 8, max = 50, message = "Confirm Password must be between 8 and 50 characters")
    private String confirmPassword;

    @Column(length = 255)
    private String profileImage;

    @Column(length = 255)
    private String role;
}

