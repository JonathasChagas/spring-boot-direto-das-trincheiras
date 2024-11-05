package academy.devdojo.controllers;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapperImpl;
import academy.devdojo.repositories.ProducerData;
import academy.devdojo.repositories.ProducerHardCodedRepository;
import academy.devdojo.services.ProducerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({ProducerData.class, ProducerHardCodedRepository.class, ProducerMapperImpl.class, ProducerService.class})
class ProducerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProducerData producerData;
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
    void findAll_ReturnsAllProducers_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producersList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect()
    }
}