package v.yeikovych.tinprojectsp.model.itn;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import v.yeikovych.tinprojectsp.dto.itn.ItnDto;
import v.yeikovych.tinprojectsp.model.EntityClass;
import v.yeikovych.tinprojectsp.model.student.Student;
import v.yeikovych.tinprojectsp.model.teacher.Teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Itn implements EntityClass<ItnDto> {

    @Id
    @GeneratedValue
    private UUID id;

    private String subject;

    private LocalDate dateIssued;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "student_itn",
            joinColumns = @JoinColumn(name = "itn_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "teacher_itn",
            joinColumns = @JoinColumn(name = "itn_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers = new ArrayList<>();

    private ItnStatus status;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Itn itn = (Itn) o;
        return getId() != null && Objects.equals(getId(), itn.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public ItnDto toDto() {
        return ItnDto.builder()
                .id(id)
                .subject(subject)
                .dateIssued(dateIssued)
                .description(description)
                .status(status)
                .build();
    }
}
