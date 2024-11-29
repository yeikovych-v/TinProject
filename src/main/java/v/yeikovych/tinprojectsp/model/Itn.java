package v.yeikovych.tinprojectsp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import v.yeikovych.tinprojectsp.util.ValidEnum;

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
public class Itn {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Subject is required.")
    private String subject;

    @NotNull(message = "Date issued cannot be null.")
    private LocalDate dateIssued;
    @NotBlank(message = "Description is required.")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "student_itn",
            joinColumns = @JoinColumn(name = "itn_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    @ManyToMany(mappedBy = "awardedItns")
    private List<Teacher> teachers = new ArrayList<>();

    @ValidEnum(enumClass = ItnStatus.class, message = "Should be Either: [Pending] or [Completed]")
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
}
