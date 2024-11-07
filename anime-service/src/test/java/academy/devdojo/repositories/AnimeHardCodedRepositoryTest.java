package academy.devdojo.repositories;

import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
@ExtendWith(MockitoExtension.class)
class AnimeHardCodedRepositoryTest {
    @InjectMocks
    AnimeHardCodedRepository repository;
    @Mock
    AnimeData animeData;
    private List<Anime> animesList;
    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {
        animesList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("findAll returns a list with all animes")
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var animes = repository.findAll();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSameElementsAs(animesList);
    }

    @Test
    @DisplayName("findById returns an anime with given id")
    void findById_ReturnsAnimeById_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var expectedAnime = animesList.getFirst();
        var animes = repository.findById(1L);
        Assertions.assertThat(animes).isPresent().contains(expectedAnime);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animes = repository.findByName(null);
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns list with found objects when name exists")
    void findByName_ReturnsAnimeInList_WhenNameIsFound() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var expectedAnime = animesList.getFirst();
        var animes = repository.findByName(expectedAnime.getName());
        Assertions.assertThat(animes).contains(expectedAnime);
    }

    @Test
    @DisplayName("save creates an anime")
    void save_CreatesAnime_WhenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animeToSave = animeUtils.newSavedProducer();
        var anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

        var animeSavedOptional = repository.findById(animeToSave.getId());
        Assertions.assertThat(animeSavedOptional).isPresent().contains(animeToSave);
    }

    @Test
    @DisplayName("delete removes an anime")
    void delete_RemoveAnime_WhenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animeToDelete = animesList.getFirst();
        repository.delete(animeToDelete);

        var animes = repository.findAll();

        Assertions.assertThat(animes).isNotEmpty().doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update updates an anime")
    void update_UpdateAnime_WhenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animeToUpdate = this.animesList.getFirst();
        animeToUpdate.setName("Aniplex");

        repository.update(animeToUpdate);

        Assertions.assertThat(this.animesList).contains(animeToUpdate);

        var animeUpdatedOptional = repository.findById(animeToUpdate.getId());

        Assertions.assertThat(animeUpdatedOptional).isPresent();
        Assertions.assertThat(animeUpdatedOptional.get().getName()).isEqualTo(animeToUpdate.getName());
    }
}