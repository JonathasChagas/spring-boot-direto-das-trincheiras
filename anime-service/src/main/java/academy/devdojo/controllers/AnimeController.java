package academy.devdojo.controllers;

import academy.devdojo.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

    @GetMapping
    public List<Anime> listAll(@RequestParam(required = false) String name) {
        if (name == null) return Anime.getAnimeList();

        return Anime.getAnimeList().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable Long id) {
       return Anime.getAnimeList()
               .stream()
               .filter(anime -> anime.getId().equals(id))
               .findFirst().orElse(null);
    }

    @PostMapping
    public Anime save(@RequestBody Anime anime) {
        long id = Anime.getAnimeList().stream().mapToLong(Anime::getId).max().orElseThrow(NoSuchElementException::new);

        anime.setId(id + 1);
        Anime.getAnimeList().add(anime);

        return anime;
    }
}
