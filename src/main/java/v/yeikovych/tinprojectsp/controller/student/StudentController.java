package v.yeikovych.tinprojectsp.controller.student;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.tinprojectsp.dto.student.StudentDto;
import v.yeikovych.tinprojectsp.service.ErrorService;
import v.yeikovych.tinprojectsp.service.student.StudentService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final ErrorService errorService;

    public StudentController(StudentService studentService, ErrorService errorService) {
        this.studentService = studentService;
        this.errorService = errorService;
    }

    @GetMapping
    public String viewAllStudents(Model model) {
        List<StudentDto> students = studentService.dtoList();
        model.addAttribute("students", students);
        return "students/view-all";
    }

    @GetMapping("/new")
    @RolesAllowed("ROLE_USER")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new StudentDto());
        return "students/new-student";
    }

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public String saveStudent(@Valid @ModelAttribute StudentDto student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            initErrors(student, result, model);
            return "students/new-student";
        }

        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    @RolesAllowed("ROLE_USER")
    public String editStudentForm(@PathVariable UUID id, Model model) {
        StudentDto student = studentService.findDtoById(id).orElseThrow();
        model.addAttribute("student", student);
        return "students/edit-student";
    }

    @PostMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String updateStudent(@PathVariable UUID id, @Valid @ModelAttribute StudentDto updatedStudent, BindingResult result, Model model) {
        if (result.hasErrors()) {
            initErrors(updatedStudent, result, model);
            return "students/edit-student";
        }

        studentService.findEntityById(id).orElseThrow();

        studentService.save(updatedStudent);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    @RolesAllowed("ROLE_USER")
    public String deleteStudent(@PathVariable UUID id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }

    private void initErrors(@Valid @ModelAttribute StudentDto student, BindingResult result, Model model) {
        errorService.tryAddError("firstName", model, result);
        errorService.tryAddError("lastName", model, result);
        errorService.tryAddError("email", model, result);
        errorService.tryAddError("age", model, result);
        model.addAttribute("student", student);
    }

}

