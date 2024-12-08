package v.yeikovych.tinprojectsp.model.teacher;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import v.yeikovych.tinprojectsp.dto.teacher.TeacherDto;
import v.yeikovych.tinprojectsp.model.EntityClass;
import v.yeikovych.tinprojectsp.model.itn.Itn;

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
public class Teacher implements EntityClass<TeacherDto> {

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;

    private String department;
    private String email;
    private int experienceYears;

    @ManyToMany(mappedBy = "teachers")
    private List<Itn> awardedItns = new ArrayList<>();

    private boolean isTenured;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Teacher teacher = (Teacher) o;
        return getId() != null && Objects.equals(getId(), teacher.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public TeacherDto toDto() {
        return TeacherDto.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .experienceYears(experienceYears)
                .department(department)
                .isTenured(isTenured)
                .build();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
