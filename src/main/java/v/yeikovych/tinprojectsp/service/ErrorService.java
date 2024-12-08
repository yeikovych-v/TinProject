package v.yeikovych.tinprojectsp.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Service
public class ErrorService {

    public void tryAddError(String field, Model model, BindingResult bindingResult) {
        tryAddError(field, "Err", model, bindingResult);
    }

    public void tryAddError(String field, String errorSuffix, Model model, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            model.addAttribute(field + errorSuffix, Objects.requireNonNull(bindingResult.getFieldError(field)).getDefaultMessage());
        }
    }
}
