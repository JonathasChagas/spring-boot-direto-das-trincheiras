package academy.devdojo.services;

import academy.devdojo.domain.Producer;
import academy.devdojo.repositories.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodedRepository repository;
    private List<Producer> producersList;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().name("Ufotable").id(1L).createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().name("Wit Studio").id(2L).createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().name("Studio Ghibli").id(3L).createdAt(LocalDateTime.now()).build();
        producersList = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    @Test
    @DisplayName("findAll returns a list with all producers when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(producersList);

        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().isNotEmpty().hasSameElementsAs(producersList);
    }

    @Test
    @DisplayName("findAll returns list with found objects when name exists")
    @Order(2)
    void findByName_ReturnsProducerInList_WhenNameIsFound() {
        var producer = producersList.getFirst();
        var expectedProducersFound = singletonList(producer);
        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducersFound);

        var producers = service.findAll(producer.getName());
        Assertions.assertThat(producers).containsAll(expectedProducersFound);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not found";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());

        var producers = repository.findByName(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns a producer with given id")
    @Order(4)
    void findById_ReturnsProducerById_WhenSuccessful() {
        var expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producers = service.findByIdOrThrowNotFound(expectedProducer.getId());
        Assertions.assertThat(producers).isEqualTo(expectedProducer);
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() ->service.findByIdOrThrowNotFound(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSuccesful() {
        var producerToSave = Producer.builder().id(99L).name("A1 Studios").createdAt(LocalDateTime.now()).build();
        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);

        var savedProducer = service.save(producerToSave);

        Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a producer")
    @Order(7)
    void delete_RemoveProducer_WhenSuccesful() {
        var producerToDelete = producersList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));
        BDDMockito.doNothing().when(repository).delete(producerToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(producerToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToDelete = producersList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() ->service.delete(producerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a producer")
    @Order(9)
    void update_UpdateProducer_WhenSuccesful() {
        var producerToUpdate = producersList.getFirst();
        producerToUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
        BDDMockito.doNothing().when(repository).update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToUpdate = producersList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() ->service.update(producerToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}