package v.yeikovych.tinprojectsp.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import v.yeikovych.tinprojectsp.model.itn.Itn;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class DetailedTeacherDto {

    private UUID id;

    private String firstName;
    private String lastName;

    private String department;
    private String email;
    private int experienceYears;

    private List<Itn> itns;

    private boolean isTenured;
}
