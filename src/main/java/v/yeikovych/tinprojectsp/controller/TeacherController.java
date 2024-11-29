package v.yeikovych.tinprojectsp.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.tinprojects.model.Itn;
import v.yeikovych.tinprojectsp.model.Teacher;
import v.yeikovych.tinprojectsp.repository.TeacherRepository;

import java.util.UUID;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public String viewAllTeachers(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        return "teachers/view-all";
    }

    @GetMapping("/new")
    public String newTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teachers/new-teacher";
    }

    @PostMapping
    public String saveTeacher(@Valid @ModelAttribute Teacher teacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "teachers/new-teacher";
        }
        teacherRepository.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String editTeacherForm(@PathVariable UUID id, Model model) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID: " + id));
        model.addAttribute("teacher", teacher);
        return "teachers/edit-teacher";
    }

    @PostMapping("/{id}")
    public String updateTeacher(@PathVariable UUID id, @Valid @ModelAttribute Teacher updatedTeacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("teacher", updatedTeacher);
            return "teachers/edit-teacher";
        }

        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        teacher.setFirstName(updatedTeacher.getFirstName());
        teacher.setLastName(updatedTeacher.getLastName());
        teacher.setEmail(updatedTeacher.getEmail());
        teacher.setDepartment(updatedTeacher.getDepartment());
        teacher.setTenured(updatedTeacher.isTenured());
        teacher.setExperienceYears(updatedTeacher.getExperienceYears());

        teacherRepository.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable UUID id) {
        teacherRepository.deleteById(id);
        return "redirect:/teachers";
    }
}