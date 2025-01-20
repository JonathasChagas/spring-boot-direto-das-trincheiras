package academy.devdojo.anime;

import academy.devdojo.domain.Anime;
import academy.devdojo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository repository;

    public List<Anime> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByNameIgnoreCase(name);
    }

    public Anime findByIdOrThrowNotFound(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Anime not Found"));
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public void delete(Long id) {
        var animeToDelete = findByIdOrThrowNotFound(id);
        repository.delete(animeToDelete);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate.getId());
        repository.save(animeToUpdate);
    }

    public void assertAnimeExists(Long id) {
        findByIdOrThrowNotFound(id);
    }
}
