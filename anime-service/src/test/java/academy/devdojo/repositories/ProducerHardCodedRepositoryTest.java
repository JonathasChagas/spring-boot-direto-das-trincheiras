package academy.devdojo.repositories;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;
    @Mock
    private ProducerData producerData;
    private final List<Producer> producersList = new ArrayList<>();

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().name("Ufotable").id(1L).createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().name("Wit Studio").id(2L).createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().name("Studio Ghibli").id(3L).createdAt(LocalDateTime.now()).build();
        producersList.addAll(List.of(ufotable, witStudio, studioGhibli));
    }

    @Test
    @DisplayName("findAll returns a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().isNotEmpty().hasSameElementsAs(producersList);
    }

    @Test
    @DisplayName("findById returns a producer with given id")
    void findById_ReturnsProducerById_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        var expectedProducer = producersList.getFirst();
        var producers = repository.findById(1L);
        Assertions.assertThat(producers).isPresent().contains(expectedProducer);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        var producers = repository.findByName(null);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns list with found objects when name exists")
    void findByName_ReturnsProducerInList_WhenNameIsFound() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        var expectedProducer = producersList.getFirst();
        var producers = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producers).contains(expectedProducer);
    }

    @Test
    @DisplayName("save creates a producer")
    void save_CreatesProducer_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        var producerToSave = Producer.builder().id(99L).name("A1 Studios").createdAt(LocalDateTime.now()).build();
        var producer = repository.save(producerToSave);

        Assertions.assertThat(producer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();

        var producerSavedOptional = repository.findById(producerToSave.getId());
        Assertions.assertThat(producerSavedOptional).isPresent().contains(producerToSave);
    }

    @Test
    @DisplayName("delete removes a producer")
    void delete_RemoveProducer_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        var producerToDelete = producersList.getFirst();
        repository.delete(producerToDelete);

        var producers = repository.findAll();

        Assertions.assertThat(producers).isNotEmpty().doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update updates a producer")
    void update_UpdateProducer_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        var producerToUpdate = this.producersList.getFirst();
        producerToUpdate.setName("Aniplex");

        repository.update(producerToUpdate);

        Assertions.assertThat(this.producersList).contains(producerToUpdate);

        var producerUpdatedOptional = repository.findById(producerToUpdate.getId());

        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getName()).isEqualTo(producerToUpdate.getName());
    }
}