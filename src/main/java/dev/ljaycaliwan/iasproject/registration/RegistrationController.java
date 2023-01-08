package dev.ljaycaliwan.iasproject.registration;

import dev.ljaycaliwan.iasproject.student.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(Model model, RegistrationRequest request, RedirectAttributes attributes) {
        try {
            model.addAttribute("user", request);
            registrationService.register(request);
            attributes.addFlashAttribute("message", "Registration Successful");
        }catch (Exception e){
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/login";
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
