package v.yeikovych.tinprojectsp.dto.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class RegisterUserDto {

    @NotNull
    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email is required.")
    private String email;
    @NotNull
    @NotBlank(message = "First Name is Required.")
    @Size(max = 254, message = "First name cannot be more than 254 characters")
    private String firstName;
    @NotNull
    @Size(max = 254, message = "Last name cannot be more than 254 characters")
    @NotBlank(message = "Last Name is Required.")
    private String lastName;
    @NotNull
    @NotBlank(message = "Password Required.")
    @Size(min = 6, message = "Password Must be at least 6 characters long.")
    private String password;
    @NotNull
    @NotBlank(message = "Repeat Password is Required.")
    @Size(min = 6, message = "Password Must be at least 6 characters long.")
    private String matchingPassword;

}
