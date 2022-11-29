package dev.ljaycaliwan.iasproject.home;

import dev.ljaycaliwan.iasproject.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Welcome to Home Page!";
    }

}
