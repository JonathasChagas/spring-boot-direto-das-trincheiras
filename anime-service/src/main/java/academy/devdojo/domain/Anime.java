package academy.devdojo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Anime
{
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    @Getter
    private static List<Anime> animes = new ArrayList<>(List.of(
            Anime.builder().name("Haikyuu").id(1L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Boku no Hero").id(2L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Naruto").id(3L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Ansatsu Kyoushitsu").id(4L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Shigatsu Wa Kimi no Uso").id(5L).createdAt(LocalDateTime.now()).build()
    ));

}

