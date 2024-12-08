package v.yeikovych.tinprojectsp.dto.teacher;

import jakarta.validation.constraints.*;
import lombok.*;
import v.yeikovych.tinprojectsp.dto.Dto;
import v.yeikovych.tinprojectsp.model.teacher.Teacher;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto implements Dto<Teacher> {

    private UUID id;

    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name cannot exceed 50 characters.")
    private String firstName;
    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot exceed 50 characters.")
    private String lastName;

    @NotBlank(message = "Department is required.")
    private String department;
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is not valid.")
    private String email;
    @PositiveOrZero(message = "Experience years must be zero or positive.")
    private int experienceYears;

    private boolean isTenured;

    public Teacher toEntity() {
        return Teacher.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .department(department)
                .experienceYears(experienceYears)
                .isTenured(isTenured)
                .build();
    }

    @Override
    public Dto<Teacher> copyFrom(Teacher other) {
        id = other.getId();
        firstName = other.getFirstName();
        lastName = other.getLastName();
        email = other.getEmail();
        department = other.getDepartment();
        experienceYears = other.getExperienceYears();
        isTenured = other.isTenured();
        return this;
    }
}
