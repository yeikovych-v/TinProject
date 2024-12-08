package v.yeikovych.tinprojectsp.service.student;

import org.springframework.stereotype.Service;
import v.yeikovych.tinprojectsp.dto.student.StudentDto;
import v.yeikovych.tinprojectsp.model.student.Student;
import v.yeikovych.tinprojectsp.repository.StudentRepository;
import v.yeikovych.tinprojectsp.service.CrudService;

import java.util.List;

@Service
public class StudentService extends CrudService<Student, StudentDto, StudentRepository> {
    public StudentService(StudentRepository repository) {
        super(repository);
    }

    public List<String> getStudentEmails() {
        return repository.findAll().stream().map(Student::getEmail).toList();
    }
}
