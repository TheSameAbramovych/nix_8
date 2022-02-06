package ua.com.alevel.hw_9_hibernate.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseController {

    public void showError(BindingResult br, Model model) {
        model.addAttribute("errors", br.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
    }

    public void showError(BindingResult br, RedirectAttributes model) {
        model.addFlashAttribute("errors", br.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
    }


    protected boolean errorHandling(ServiceRunner o, RedirectAttributes model) {
        try {
            o.run();
        } catch (ConstraintViolationException e) {
            model.addFlashAttribute("errors", e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList());
            return true;
        } catch (Exception e) {
            model.addFlashAttribute("errors", List.of(e.getMessage()));
            return true;
        }
        return false;
    }

    protected boolean errorHandling(ServiceRunner o, Model model) {
        try {
            o.run();
        } catch (ConstraintViolationException e) {
            model.addAttribute("errors", e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList());
            return true;
        } catch (Exception e) {
            model.addAttribute("errors", List.of(e.getMessage()));
            return true;
        }
        return false;
    }

    interface ServiceRunner {
        public void run();
    }


}
