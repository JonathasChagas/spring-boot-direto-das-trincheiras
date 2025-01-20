package academy.devdojo.anime;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@RequiredArgsConstructor
@Slf4j
public class AnimeController {
    private final AnimeService service;
    private final AnimeMapper mapper;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> findAll(@RequestParam(required = false) String name) {
        log.debug("Request received to list all animes, param name: '{}'", name);

        var animes = service.findAll(name);

        var animeGetResponse = mapper.toAnimeGetResponseList(animes);
        
        return ResponseEntity.ok(animeGetResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id: '{}'", id);

        var anime = service.findByIdOrThrowNotFound(id);

        AnimeGetResponse animeGetResponse = mapper.toAnimeGetResponse(anime);

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody @Valid AnimePostRequest animePostRequest) {
        log.debug("Request to save anime: '{}'", animePostRequest);

        var anime = mapper.toAnime(animePostRequest);

        var animeSaved = service.save(anime);

        var animePostResponse = mapper.toAnimePostResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(animePostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete anime by id: '{}'", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid AnimePutRequest request) {
        log.debug("Request to update anime '{}'", request);

        var animeUpdated = mapper.toAnime(request);

        service.update(animeUpdated);

        return ResponseEntity.noContent().build();
    }
}
