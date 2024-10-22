package academy.devdojo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hi")
    public String hi() {
        return "OMAE WA MOU SHINDE IRU";
    }

//    @GetMapping(value = {"hi", "hi/" })      //PROTEJA OS DOIS END POINTS
//    public String hi() {
//        return "OMAE WA MOU SHINDE IRU";
//    }

}
