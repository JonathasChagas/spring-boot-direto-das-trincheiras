package academy.devdojo.anime;

import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.domain.Anime;
import academy.devdojo.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private AnimeRepository repository;
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

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSameElementsAs(animesList);
    }

    @Test
    @DisplayName("findAll returns list with found objects when name exists")
    @Order(2)
    void findByName_ReturnsAnimeInList_WhenNameIsFound() {
        var anime = animesList.getFirst();

        var expectedAnimesFound = singletonList(anime);

        BDDMockito.when(repository.findByNameIgnoreCase(anime.getName())).thenReturn(expectedAnimesFound);

        var animes = service.findAll(anime.getName());

        Assertions.assertThat(animes).containsAll(expectedAnimesFound);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not found";

        BDDMockito.when(repository.findByNameIgnoreCase(name)).thenReturn(emptyList());

        var animes = service.findAll(name);

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns a anime with given id")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() {
        var expectedAnime = animesList.getFirst();

        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));

        var animes = service.findByIdOrThrowNotFound(expectedAnime.getId());

        Assertions.assertThat(animes).isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findById throws NotFoundException when anime is not found")
    @Order(5)
    void findById_ThrowsNotFoundException_WhenAnimeIsNotFound() {
        var expectedAnime = animesList.getFirst();

        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedAnime.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("save creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccesful() {
        var animeToSave = animeUtils.newSavedProducer();

        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

        var savedAnime = service.save(animeToSave);

        Assertions.assertThat(savedAnime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a anime")
    @Order(7)
    void delete_RemoveAnime_WhenSuccesful() {
        var animeToDelete = animesList.getFirst();

        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.of(animeToDelete));
        BDDMockito.doNothing().when(repository).delete(animeToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(animeToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws NotFoundException when anime is not found")
    @Order(8)
    void delete_ThrowsNotFoundException_WhenAnimeIsNotFound() {
        var animeToDelete = animesList.getFirst();

        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("update updates a anime")
    @Order(9)
    void update_UpdateAnime_WhenSuccesful() {
        var animeToUpdate = animesList.getFirst().withName("Overlord");
        var id = animeToUpdate.getId();

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToUpdate));
        BDDMockito.when(repository.save(animeToUpdate)).thenReturn(animeToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @DisplayName("update throws NotFoundException when anime is not found")
    @Order(10)
    void update_ThrowsNotFoundException_WhenAnimeIsNotFound() {
        var animeToUpdate = animesList.getFirst();

        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(NotFoundException.class);
    }
}