package ua.com.alevel.hw_8_9_jpa_hibernate.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseController {

    public void showError(BindingResult br, Model model) {
        br.getModel().entrySet().stream()
                .filter(it -> !"errors".equals(it.getKey()))
                .forEach(it -> model.addAttribute(it.getKey(), it.getValue()));
        model.addAttribute("errors", br.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
    }

//    @ControllerAdvice
//    public static class UserControllerAdvice {
//
//        @ExceptionHandler(value = SQLException.class)
//        public ModelAndView handleUserException(SQLException ex) {
//
//            // Generate corresponding results(ModelAndView) based on exception.
//            // example: Put the error message to model.
//            return new ModelAndView("create", HttpStatus.valueOf(ex.getMessage()));
//        }
//    }

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
