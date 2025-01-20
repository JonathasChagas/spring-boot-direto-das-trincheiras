package academy.devdojo.anime;

import academy.devdojo.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByNameIgnoreCase(String name);
}
