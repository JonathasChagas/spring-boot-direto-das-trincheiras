package academy.devdojo.controllers;

import academy.devdojo.domain.Anime;
import academy.devdojo.repositories.AnimeData;
import academy.devdojo.repositories.AnimeHardCodedRepository;
import academy.devdojo.services.AnimeService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class AnimeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimeData animeData;
    @SpyBean
    private AnimeHardCodedRepository repository;
    private List<Anime> animesList;
    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var dateTime = "2024-11-05T12:17:12.5031944";
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);

        var dragonBallZ = Anime.builder().name("Dragon Ball Z").id(1L).createdAt(localDateTime).build();
        var durarara = Anime.builder().name("Durarara").id(2L).createdAt(localDateTime).build();
        var loveLive = Anime.builder().name("Love Live").id(3L).createdAt(localDateTime).build();
        var saikiKusuo = Anime.builder().name("Saiki Kusuo no Psi-nan").id(4L).createdAt(localDateTime).build();
        var konosuba = Anime.builder().name("Konosuba").id(5L).createdAt(localDateTime).build();
        animesList = new ArrayList<>(List.of(dragonBallZ, durarara, loveLive, saikiKusuo, konosuba));
    }

    @Test
    @DisplayName("GET v1/animes returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var response = readResourceFile("anime/get-anime-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?param=Ufotable returns a list with found object when name exists")
    @Order(2)
    void findAll_ReturnsAnimeInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var response = readResourceFile("anime/get-anime-dragonballz-name-200.json");
        var name = "Dragon Ball Z";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var response = readResourceFile("anime/get-anime-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/1 returns a anime with given id")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var response = readResourceFile("anime/get-anime-by-id-200.json");
        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/99 throws ResponseStatusException 404 when anime is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not Found"));
    }

    @Test
    @DisplayName("POST v1/animes creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccesful() throws Exception {
        var request = readResourceFile("anime/post-request-anime-200.json");
        var response = readResourceFile("anime/post-response-anime-201.json");
        var animeToSave = Anime.builder().name("Sailor Moon").id(99L).build();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToSave);
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/animes")
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
    @DisplayName("PUT v1/animes updates a anime")
    @Order(7)
    void update_UpdateAnime_WhenSuccesful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var request = readResourceFile("anime/put-request-anime-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/animes throws ResponseStatusException when anime is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var request = readResourceFile("anime/put-request-anime-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not Found"));
    }

    @Test
    @DisplayName("DEL v1/animes/1 removes a anime")
    @Order(9)
    void delete_RemoveAnime_WhenSuccesful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        Long id = animesList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DEL v1/animes/99 throws ResponseStatusException when anime is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        Long id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not Found"));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }
}