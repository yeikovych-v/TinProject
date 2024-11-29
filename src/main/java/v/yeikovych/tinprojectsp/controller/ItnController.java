package v.yeikovych.tinprojectsp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.tinprojectsp.model.Itn;
import v.yeikovych.tinprojectsp.repository.ItnRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/itns")
public class ItnController {

    @Autowired
    private ItnRepository itnRepository;

    @GetMapping
    public String viewAllITNs(Model model) {
        List<Itn> itns = itnRepository.findAll();
        model.addAttribute("itns", itns);
        return "itns/view-all";
    }

    @GetMapping("/new")
    public String newItnForm(Model model) {
        model.addAttribute("itn", new Itn());
        return "itns/new-itn";
    }

    @PostMapping
    public String saveItn(@Valid @ModelAttribute Itn itn, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("itn", itn);
            return "itns/new-itn";
        }

        itnRepository.save(itn);
        return "redirect:/itns";
    }

    @GetMapping("/edit/{id}")
    public String editItnForm(@PathVariable UUID id, Model model) {
        Itn itn = itnRepository.findById(id).orElseThrow();
        model.addAttribute("itn", itn);
        return "itns/edit-itn";
    }

    @PostMapping("/{id}")
    public String updateItn(@PathVariable UUID id, @Valid @ModelAttribute Itn updatedItn, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("itn", updatedItn);
            return "itns/edit-itn";
        }

        Itn itn = itnRepository.findById(id).orElseThrow();
        itn.setSubject(updatedItn.getSubject());
        itn.setDescription(updatedItn.getDescription());
        itn.setStatus(updatedItn.getStatus());
        itn.setDateIssued(updatedItn.getDateIssued());

        itnRepository.save(itn);
        return "redirect:/itns";
    }

    @GetMapping("/delete/{id}")
    public String deleteItn(@PathVariable UUID id) {
        itnRepository.deleteById(id);
        return "redirect:/itns";
    }
}

