package v.yeikovych.tinprojectsp.dto.itn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class DetailedItnDto {

    private UUID id;
    private String subject;
    private LocalDate dateIssued;
    private String description;
    private String status;

    private List<String> studentNames;
    private List<String> teacherNames;
}
