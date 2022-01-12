package ua.com.alevel.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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


}
