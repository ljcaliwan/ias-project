package dev.ljaycaliwan.iasproject.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "/sign-up")
    public String signUpView(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "sign-up";
    }

    @GetMapping(path = "/student/new")
    public String newStudentView(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "new-student-form";
    }

    @GetMapping(path = "/students")
    public String getAllStudents(Model model){
        try {
            List<Student> students = new ArrayList<Student>(studentService.getAllStudents());

            model.addAttribute("students", students);
        }catch (Exception e){
            model.addAttribute("message", e.getMessage());
        }
        return "homepage";
    }
    @PostMapping(path = "/students/save")
    public String addNewStudent(Model model, @ModelAttribute("student") Student student, RedirectAttributes attributes){
        try {
            model.addAttribute("student", student);
            studentService.addNewStudent(student);
            attributes.addFlashAttribute("message", "Added Student Successfully");
        }catch (Exception e){
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/students";
    }
    @GetMapping(path = "/students/{studentId}")
    public String editStudent(@PathVariable("studentId") Long studentId, Model model, RedirectAttributes attributes){
        try {
            Student student = studentService.findStudentById(studentId);

            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Edit Student (ID: " + studentId + ")");

            return "edit-student-form";
        } catch (Exception e) {
            attributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/students";
        }
    }

    @PutMapping(path = "/students/{studentId}")
    public String updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String fullName,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String address, RedirectAttributes attributes){

        try {
            studentService.updateStudent(studentId, fullName, email, address);
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students";
    }

    //using GET mapping instead of DELETE mapping because target tag is not a <form> and it doesn't
    // support some http method other than the default GET method
    @GetMapping(path = "/students/delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);

        return "redirect:/students";
    }

}
