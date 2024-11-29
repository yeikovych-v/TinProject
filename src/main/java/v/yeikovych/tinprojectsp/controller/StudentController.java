package v.yeikovych.tinprojectsp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.tinprojectsp.model.Student;
import v.yeikovych.tinprojectsp.repository.StudentRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public String viewAllStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students/view-all";
    }

    @GetMapping("/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/new-student";
    }

    @PostMapping
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", student);
            return "students/new-student";
        }

        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable UUID id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow();
        model.addAttribute("student", student);
        return "students/edit-student";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable UUID id, @Valid @ModelAttribute Student updatedStudent, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", updatedStudent);
            return "students/edit-student";
        }

        Student student = studentRepository.findById(id).orElseThrow();
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());
        student.setAge(updatedStudent.getAge());
        student.setActive(updatedStudent.isActive());

        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable UUID id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}

