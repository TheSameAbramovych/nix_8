package ua.com.alevel.module_3.controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

public abstract class BaseController {

    protected void errorHandling(ServiceRunner o, RedirectAttributes model) {
        try {
            o.run();
        } catch (ConstraintViolationException e) {
            model.addFlashAttribute("errors", e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList());
        } catch (Exception e) {
            model.addFlashAttribute("errors", List.of(e.getMessage()));
        }
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
        void run();
    }


}
