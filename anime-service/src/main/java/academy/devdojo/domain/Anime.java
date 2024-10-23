package academy.devdojo.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Anime
{
    private Long id;
    private String name;

    public static List<Anime> getAnimes() {
        return List.of(
                Anime.builder().name("Haikyuu").id(1L).build(),
                Anime.builder().name("Boku no Hero").id(2L).build(),
                Anime.builder().name("Naruto").id(3L).build(),
                Anime.builder().name("Ansatsu Kyoushitsu").id(4L).build(),
                Anime.builder().name("Shigatsu Wa Kimi no Uso").id(5L).build()
        );
    }
}

