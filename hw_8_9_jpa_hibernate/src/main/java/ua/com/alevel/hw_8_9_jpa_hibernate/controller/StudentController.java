package ua.com.alevel.hw_8_9_jpa_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto.PageAndSizeData;
import ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto.SortData;
import ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto.StudentGroupIds;
import ua.com.alevel.hw_8_9_jpa_hibernate.entity.Student;
import ua.com.alevel.hw_8_9_jpa_hibernate.service.GroupService;
import ua.com.alevel.hw_8_9_jpa_hibernate.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController extends BaseController {

    private final GroupService groupService;
    private final StudentService studentService;

    @Autowired
    public StudentController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping()
    public String findAll(Model model, PageAndSizeData pageAndSizeData, SortData sortData) {
        pageAndSizeData = RequestHelper.getValidPageAndSizeData(pageAndSizeData);
        sortData = RequestHelper.getValidSortData(sortData);

        List<Student> students = studentService.findAll(pageAndSizeData, sortData);
        int size = pageAndSizeData.getSize();
        long pageCount = studentService.count() % size == 0 ? studentService.count() / size : studentService.count() / size + 1;

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("students", students);
        model.addAttribute("pageAndSize", pageAndSizeData);
        model.addAttribute("sortData", sortData);

        return "student/list";
    }


    @GetMapping("/create")
    public String createStudentForm(Student student) {
        return "student/create";
    }

    @PostMapping("/create")
    public String createStudent(@Valid Student student, BindingResult br, Model model) {
        if (br.hasErrors()) {
            showError(br, model);
            return "student/create";
        }
        if (errorHandling(() -> studentService.save(student), model)) {
            return "student/create";
        }

        return "redirect:/students";
    }

    @GetMapping("/details/{id}")
    public String detailsStudent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("groups", groupService.findByStudentId(id));
        model.addAttribute("ids", new StudentGroupIds());
        return "student/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String updateStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "student/update";
    }

    @PostMapping("/update")
    public String updateStudent(@Valid Student student, BindingResult br, Model model) {
        if (br.hasErrors()) {
            showError(br, model);
            return "student/update";
        }
        studentService.update(student);
        return "redirect:/students";
    }

    @PostMapping("/group/add")
    public String addStudentToGroup(@Valid StudentGroupIds ids, BindingResult br, @RequestHeader("Referer") String referer, Model model) {
        if (br.hasErrors()) {
            showError(br, model);
            return detailsStudent(ids.getStudentId(), model);
        }
        groupService.addStudent(ids.getGroupId(), ids.getStudentId());
        return detailsStudent(ids.getStudentId(), model);
    }
}
