package academy.devdojo.repositories;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodedRepository {
    private static final List<Anime> ANIMES = new ArrayList<>(List.of(
            Anime.builder().name("Haikyuu").id(1L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Boku no Hero").id(2L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Naruto").id(3L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Ansatsu Kyoushitsu").id(4L).createdAt(LocalDateTime.now()).build(),
            Anime.builder().name("Shigatsu Wa Kimi no Uso").id(5L).createdAt(LocalDateTime.now()).build()
    ));

    public static List<Anime> findAll() {
        return ANIMES;
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public void save(Anime anime) {
        ANIMES.add(anime);
    }

    public void delete(Anime anime) {
        ANIMES.remove(anime);
    }

    public void update(Anime anime) {
        ANIMES.remove(anime);
        ANIMES.add(anime);
    }
}
