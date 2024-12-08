package v.yeikovych.tinprojectsp.service.teacher;

import org.springframework.stereotype.Service;
import v.yeikovych.tinprojectsp.dto.teacher.TeacherDto;
import v.yeikovych.tinprojectsp.model.teacher.Teacher;
import v.yeikovych.tinprojectsp.repository.TeacherRepository;
import v.yeikovych.tinprojectsp.service.CrudService;

import java.util.List;

@Service
public class TeacherService extends CrudService<Teacher, TeacherDto, TeacherRepository> {

    public TeacherService(TeacherRepository repository) {
        super(repository);
    }

    public List<String> getTeacherEmails() {
        return repository.findAll().stream().map(Teacher::getEmail).toList();
    }
}
