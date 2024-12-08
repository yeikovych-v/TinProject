package v.yeikovych.tinprojectsp.controller.itn;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.tinprojectsp.dto.itn.DetailedItnDto;
import v.yeikovych.tinprojectsp.dto.itn.ItnDto;
import v.yeikovych.tinprojectsp.service.ErrorService;
import v.yeikovych.tinprojectsp.service.itn.ItnService;
import v.yeikovych.tinprojectsp.service.student.StudentService;
import v.yeikovych.tinprojectsp.service.teacher.TeacherService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/itns")
@Slf4j
public class ItnController {

    private final ItnService itnService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ErrorService errorService;

    public ItnController(ItnService itnService, StudentService studentService, TeacherService teacherService, ErrorService errorService) {
        this.itnService = itnService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.errorService = errorService;
    }


    @GetMapping
    public String viewAllITNs(Model model) {
        List<ItnDto> itns = itnService.dtoList();
        model.addAttribute("itns", itns);
        return "itns/view-all";
    }

    @GetMapping("/new")
    @RolesAllowed("ROLE_USER")
    public String newItnForm(Model model) {
        model.addAttribute("itn", new ItnDto());
        model.addAttribute("teacherEmails", teacherService.getTeacherEmails());
        model.addAttribute("studentEmails", studentService.getStudentEmails());
        return "itns/new-itn";
    }

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public String saveItn(@Valid @ModelAttribute ItnDto itn, BindingResult result, Model model) {
        if (result.hasErrors()) {
            initErrors(itn, result, model);
            return "itns/new-itn";
        }

        itnService.save(itn);
        return "redirect:/itns";
    }

    @GetMapping("/edit/{id}")
    @RolesAllowed("ROLE_USER")
    public String editItnForm(@PathVariable UUID id, Model model) {
        ItnDto itn = itnService.findDtoById(id).orElseThrow();
        model.addAttribute("itn", itn);
        return "itns/edit-itn";
    }

    @PostMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String updateItn(@PathVariable UUID id, @Valid @ModelAttribute ItnDto updatedItn, BindingResult result, Model model) {
        if (result.hasErrors()) {
            initErrors(updatedItn, result, model);
            return "itns/edit-itn";
        }

        itnService.findEntityById(id).orElseThrow();

        itnService.save(updatedItn);
        return "redirect:/itns";
    }

    @GetMapping("/delete/{id}")
    @RolesAllowed("ROLE_USER")
    public String deleteItn(@PathVariable UUID id) {
        itnService.deleteById(id);
        return "redirect:/itns";
    }

    @GetMapping("/detailed/{id}")
    public String detailedView(@PathVariable UUID id, Model model) {
        List<DetailedItnDto> detailedItns = itnService.getDetailedItn(id);
        model.addAttribute("detailedItns", detailedItns);
        return "itns/view-detailed";
    }

    private void initErrors(@Valid @ModelAttribute ItnDto itn, BindingResult result, Model model) {
        errorService.tryAddError("subject", model, result);
        errorService.tryAddError("dateIssued", model, result);
        errorService.tryAddError("description", model, result);
        errorService.tryAddError("studentEmail", model, result);
        errorService.tryAddError("teacherEmail", model, result);
        errorService.tryAddError("status", model, result);
        model.addAttribute("itn", itn);
    }
}

