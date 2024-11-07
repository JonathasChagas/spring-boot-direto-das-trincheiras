package academy.devdojo.commons;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {

    public List<Anime> newAnimeList() {
        var dateTime = "2024-11-05T12:17:12.5031944";
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);

        var dragonBallZ = Anime.builder().name("Dragon Ball Z").id(1L).createdAt(localDateTime).build();
        var durarara = Anime.builder().name("Durarara").id(2L).createdAt(localDateTime).build();
        var loveLive = Anime.builder().name("Love Live").id(3L).createdAt(localDateTime).build();
        var saikiKusuo = Anime.builder().name("Saiki Kusuo no Psi-nan").id(4L).createdAt(localDateTime).build();
        var konosuba = Anime.builder().name("Konosuba").id(5L).createdAt(localDateTime).build();
        return new ArrayList<>(List.of(dragonBallZ, durarara, loveLive, saikiKusuo, konosuba));
    }

    public Anime newSavedProducer() {
        return Anime.builder().name("Sailor Moon").id(99L).createdAt(LocalDateTime.now()).build();
    }
}
