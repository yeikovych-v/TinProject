package v.yeikovych.tinprojectsp.dto.student;

import jakarta.validation.constraints.*;
import lombok.*;
import v.yeikovych.tinprojectsp.dto.Dto;
import v.yeikovych.tinprojectsp.model.student.Student;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto implements Dto<Student> {

    private UUID id;

    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name cannot exceed 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot exceed 50 characters.")
    private String lastName;

    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Min(value = 1, message = "Age must be 1 or greater.")
    @NotNull(message = "Age is required.")
    private Integer age;

    private boolean isActive;

    public Student toEntity() {
        return Student.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();
    }

    @Override
    public Dto<Student> copyFrom(Student other) {
        id = other.getId();
        firstName = other.getFirstName();
        lastName = other.getLastName();
        email = other.getEmail();
        age = other.getAge();
        isActive = other.isActive();
        return this;
    }
}
