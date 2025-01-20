package academy.devdojo.producer;

import academy.devdojo.commons.FileUtils;
import academy.devdojo.commons.ProducerUtils;
import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"academy.devdojo.producer", "academy.devdojo.commons"})
class ProducerControllerTest {
    private static final String URL = "/v1/producers";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerRepository repository;
    private List<Producer> producersList;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {
        producersList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("GET v1/producers returns a list with all producers when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(repository.findAll()).thenReturn(producersList);

        var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?param=Ufotable returns a list with found object when name exists")
    @Order(2)
    void findAll_ReturnsProducerInList_WhenNameIsFound() throws Exception {
        var name = "Ufotable";
        var ufotable = producersList.stream().filter(producer -> producer.getName().equals(name)).findFirst().orElse(null);

        BDDMockito.when(repository.findByNameIgnoreCase(name)).thenReturn(Collections.singletonList(ufotable));

        var response = fileUtils.readResourceFile("producer/get-producer-ufotable-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        var name = "x";

        var response = fileUtils.readResourceFile("producer/get-producer-x-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/1 returns a producer with given id")
    @Order(4)
    void findById_ReturnsProducerById_WhenSuccessful() throws Exception {
        var id = 1L;
        var foundProducer = producersList.stream().filter(producer -> producer.getId().equals(id)).findFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);

        var response = fileUtils.readResourceFile("producer/get-producer-by-id-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/99 throws NotFound 404 when producer is not found")
    @Order(5)
    void findById_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var id = 99L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        var response = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("POST v1/producers creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSuccesful() throws Exception {
        var request = fileUtils.readResourceFile("producer/post-request-producer-200.json");
        var response = fileUtils.readResourceFile("producer/post-response-producer-201.json");

        var producerToSave = producerUtils.newProducerToSave();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/producers updates a producer")
    @Order(7)
    void update_UpdateProducer_WhenSuccesful() throws Exception {
        var id = 1L;
        var foundProducer = producersList.stream().filter(producer -> producer.getId().equals(id)).findFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);

        var request = fileUtils.readResourceFile("producer/put-request-producer-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/producers throws NotFound when producer is not found")
    @Order(8)
    void update_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("producer/put-request-producer-404.json");
        var response = fileUtils.readResourceFile("producer/put-producer-by-id-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("DEL v1/producers/1 removes a producer")
    @Order(9)
    void delete_RemoveProducer_WhenSuccesful() throws Exception {
        var id = 1L;
        var foundProducer = producersList.stream().filter(producer -> producer.getId().equals(id)).findFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DEL v1/producers/99 throws NotFound when producer is not found")
    @Order(10)
    void delete_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var id = 1L;

        var response = fileUtils.readResourceFile("producer/delete-producer-by-id-404.json");

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @ParameterizedTest
    @MethodSource("postProducerBadRequestSource")
    @DisplayName("POST v1/producers returns bad request when fields are invalid")
    @Order(11)
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    @ParameterizedTest
    @MethodSource("putProducerBadRequestSource")
    @DisplayName("PUT v1/producers returns bad request when fields are invalid")
    @Order(12)
    void update_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> postProducerBadRequestSource() {
        var allErrors = allRequiredErrors();

        return Stream.of(
                Arguments.of("post-request-producer-blank-fields-400.json", allErrors),
                Arguments.of("post-request-producer-empty-fields-400.json", allErrors)
        );
    }

    private static Stream<Arguments> putProducerBadRequestSource() {
        var allErrors = allRequiredErrors();
        allErrors.add("The field 'id' is required");

        return Stream.of(
                Arguments.of("put-request-producer-blank-fields-400.json", allErrors),
                Arguments.of("put-request-producer-empty-fields-400.json", allErrors)
        );
    }

    private static List<String> allRequiredErrors() {
        var nameError = "The field 'name' is required";
        return new ArrayList<>(List.of(nameError));
    }
}