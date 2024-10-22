package academy.devdojo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    @GetMapping
    public List<String> listAll() {
        return Arrays.asList("Boku no Hero", "Naruto", "Ansatsu Kyoushitsu", "Shigatsu Wa Kimi no Uso", "Magi");
    }
}
