package academy.devdojo.services;

import academy.devdojo.domain.Anime;
import academy.devdojo.repositories.AnimeHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeHardCodedRepository repository;

    public List<Anime> findAll(String name) {
        return name == null ? repository.findAll() : repository.findAll().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Anime findByIdOrThrowNotFound(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not Found"));
    }

    public void save(Anime animeToSave) {
        repository.save(animeToSave);
    }

    public void delete(Long id) {
        var animeToDelete = findByIdOrThrowNotFound(id);
        repository.delete(animeToDelete);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate.getId());
        repository.update(animeToUpdate);
    }

    public Long getIdToNewSavedAnime() {
        return repository.findAll().stream().mapToLong(Anime::getId).max().orElseThrow(java.util.NoSuchElementException::new) + 1;
    }

    public void assertAnimeExists(Long id) {
        findByIdOrThrowNotFound(id);
    }
}
