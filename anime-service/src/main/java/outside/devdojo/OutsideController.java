package outside.devdojo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = {"outside.devdojo", "academy.devdojo"})
public class OutsideController {

    @GetMapping("test")
    public String outside() {
        return "OutsideController";
    }
}
