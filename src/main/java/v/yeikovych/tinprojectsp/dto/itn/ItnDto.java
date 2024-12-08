package v.yeikovych.tinprojectsp.dto.itn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import v.yeikovych.tinprojectsp.dto.Dto;
import v.yeikovych.tinprojectsp.model.itn.Itn;
import v.yeikovych.tinprojectsp.model.itn.ItnStatus;
import v.yeikovych.tinprojectsp.validation.enums.ValidEnum;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItnDto implements Dto<Itn> {

    private UUID id;

    @NotBlank(message = "Subject is required.")
    private String subject;

    @NotNull(message = "Date issued cannot be Empty.")
    private LocalDate dateIssued;
    @NotBlank(message = "Description is required.")
    private String description;

    @NotBlank(message = "Student to receive ITN is required.")
    private String studentEmail;

    @NotBlank(message = "Teacher who awarded ITN is required.")
    private String teacherEmail;

    @ValidEnum(enumClass = ItnStatus.class, message = "Should be Either: [Pending] or [Completed]")
    private ItnStatus status;

    public Itn toEntity() {
        return Itn.builder()
                .id(id)
                .subject(subject)
                .dateIssued(dateIssued)
                .description(description)
                .status(status)
                .build();
    }

    @Override
    public Dto<Itn> copyFrom(Itn other) {
        id = other.getId();
        subject = other.getSubject();
        dateIssued = other.getDateIssued();
        description = other.getDescription();
        status = other.getStatus();
        return this;
    }
}
