package academy.devdojo.services;

import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.domain.Anime;
import academy.devdojo.repositories.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;
    @Mock
    private AnimeHardCodedRepository repository;
    private List<Anime> animesList;
    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {
        animesList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("findAll returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(animesList);

        var animes = repository.findAll();
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSameElementsAs(animesList);
    }

    @Test
    @DisplayName("findAll returns list with found objects when name exists")
    @Order(2)
    void findByName_ReturnsAnimeInList_WhenNameIsFound() {
        var anime = animesList.getFirst();
        var expectedAnimesFound = singletonList(anime);
        BDDMockito.when(repository.findByName(anime.getName())).thenReturn(expectedAnimesFound);

        var animes = service.findAll(anime.getName());
        Assertions.assertThat(animes).containsAll(expectedAnimesFound);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not found";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());

        var animes = repository.findByName(name);
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns a anime with given id")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() {
        var expectedAnime = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));

        var animes = service.findByIdOrThrowNotFound(expectedAnime.getId());
        org.assertj.core.api.Assertions.assertThat(animes).isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when anime is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnime = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccesful() {
        var animeToSave = animeUtils.newSavedProducer();
        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

        var savedAnime = service.save(animeToSave);

        org.assertj.core.api.Assertions.assertThat(savedAnime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a anime")
    @Order(7)
    void delete_RemoveAnime_WhenSuccesful() {
        var animeToDelete = animesList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.of(animeToDelete));
        BDDMockito.doNothing().when(repository).delete(animeToDelete);

        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() -> service.delete(animeToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToDelete = animesList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a anime")
    @Order(9)
    void update_UpdateAnime_WhenSuccesful() {
        var animeToUpdate = animesList.getFirst();
        animeToUpdate.setName("Overlord");

        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.of(animeToUpdate));
        BDDMockito.doNothing().when(repository).update(animeToUpdate);

        org.assertj.core.api.Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToUpdate = animesList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}