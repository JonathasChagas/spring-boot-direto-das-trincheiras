package academy.devdojo.repositories;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Component
public class AnimeData {

    private final List<Anime> animes = new ArrayList<>();

    {
        var haikyuu = Anime.builder().name("Haikyuu").id(1L).createdAt(LocalDateTime.now()).build();
        var bokuNoHero = Anime.builder().name("Boku no Hero").id(2L).createdAt(LocalDateTime.now()).build();
        var naruto = Anime.builder().name("Naruto").id(3L).createdAt(LocalDateTime.now()).build();
        var ansatsuKyoushitsu = Anime.builder().name("Ansatsu Kyoushitsu").id(4L).createdAt(LocalDateTime.now()).build();
        var shigatsuWaKimiNoUso = Anime.builder().name("Shigatsu Wa Kimi no Uso").id(5L).createdAt(LocalDateTime.now()).build();
        animes.addAll(List.of(haikyuu, bokuNoHero, naruto, ansatsuKyoushitsu, shigatsuWaKimiNoUso));
    }
}
