package v.yeikovych.tinprojectsp.service.itn;

import org.springframework.stereotype.Service;
import v.yeikovych.tinprojectsp.dto.itn.DetailedItnDto;
import v.yeikovych.tinprojectsp.dto.itn.ItnDto;
import v.yeikovych.tinprojectsp.model.itn.Itn;
import v.yeikovych.tinprojectsp.model.student.Student;
import v.yeikovych.tinprojectsp.model.teacher.Teacher;
import v.yeikovych.tinprojectsp.repository.ItnRepository;
import v.yeikovych.tinprojectsp.repository.StudentRepository;
import v.yeikovych.tinprojectsp.repository.TeacherRepository;
import v.yeikovych.tinprojectsp.service.CrudService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItnService extends CrudService<Itn, ItnDto, ItnRepository> {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public ItnService(ItnRepository repository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        super(repository);
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<DetailedItnDto> getDetailedItn(UUID id) {
        return repository.findById(id).stream()
                .map(itn -> DetailedItnDto.builder()
                        .id(itn.getId())
                        .subject(itn.getSubject())
                        .dateIssued(itn.getDateIssued())
                        .description(itn.getDescription())
                        .status(itn.getStatus().name())
                        .studentNames(itn.getStudents().stream()
                                .map(Student::getFullName)
                                .collect(Collectors.toList()))
                        .teacherNames(itn.getTeachers().stream()
                                .map(Teacher::getFullName)
                                .collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public void save(ItnDto itn) {
        Teacher teacher = teacherRepository.findByEmail(itn.getTeacherEmail()).orElseThrow();
        Student student = studentRepository.findByEmail(itn.getStudentEmail()).orElseThrow();

        Itn entity = itn.toEntity();
        entity.setTeachers(List.of(teacher));
        entity.setStudents(List.of(student));

        super.save(entity);
    }
}
