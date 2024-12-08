package v.yeikovych.tinprojectsp.controller.teacher;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.tinprojectsp.dto.teacher.TeacherDto;
import v.yeikovych.tinprojectsp.service.ErrorService;
import v.yeikovych.tinprojectsp.service.teacher.TeacherService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final ErrorService errorService;

    public TeacherController(TeacherService teacherService, ErrorService errorService) {
        this.teacherService = teacherService;
        this.errorService = errorService;
    }


    @GetMapping
    public String viewAllTeachers(Model model) {
        List<TeacherDto> teachers = teacherService.dtoList();
        model.addAttribute("teachers", teachers);
        return "teachers/view-all";
    }

    @GetMapping("/new")
    @RolesAllowed("ROLE_USER")
    public String newTeacherForm(Model model) {
        model.addAttribute("teacher", new TeacherDto());
        return "teachers/new-teacher";
    }

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public String saveTeacher(@Valid @ModelAttribute TeacherDto teacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            initErrors(teacher, result, model);
            return "teachers/new-teacher";
        }

        teacherService.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    @RolesAllowed("ROLE_USER")
    public String editTeacherForm(@PathVariable UUID id, Model model) {
        TeacherDto teacher = teacherService.findDtoById(id).orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID: " + id));
        model.addAttribute("teacher", teacher);
        return "teachers/edit-teacher";
    }

    @PostMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String updateTeacher(@PathVariable UUID id, @Valid @ModelAttribute TeacherDto updatedTeacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            initErrors(updatedTeacher, result, model);
            return "teachers/edit-teacher";
        }

        teacherService.findEntityById(id).orElseThrow();

        teacherService.save(updatedTeacher);
        return "redirect:/teachers";
    }

    @GetMapping("/delete/{id}")
    @RolesAllowed("ROLE_USER")
    public String deleteTeacher(@PathVariable UUID id) {
        teacherService.deleteById(id);
        return "redirect:/teachers";
    }

    private void initErrors(@ModelAttribute @Valid TeacherDto teacher, BindingResult result, Model model) {
        errorService.tryAddError("firstName", model, result);
        errorService.tryAddError("lastName", model, result);
        errorService.tryAddError("department", model, result);
        errorService.tryAddError("email", model, result);
        errorService.tryAddError("experienceYears", model, result);
        model.addAttribute("teacher", teacher);
    }
}