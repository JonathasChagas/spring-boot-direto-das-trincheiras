package academy.devdojo.services;

import academy.devdojo.domain.Anime;
import academy.devdojo.repositories.AnimeHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AnimeService {
    public AnimeHardCodedRepository repository;

    public AnimeService() {
        this.repository = new AnimeHardCodedRepository();
    }

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

    public void assertAnimeExists(Long id) {
        findByIdOrThrowNotFound(id);
    }
}
