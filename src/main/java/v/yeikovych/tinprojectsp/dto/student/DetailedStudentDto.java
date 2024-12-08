package v.yeikovych.tinprojectsp.dto.student;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class DetailedStudentDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private Integer age;

    private List<String> itns;

    private boolean isActive;
}
