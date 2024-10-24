package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Producer {
    private Long id;
    @JsonProperty("name")
    private String name;
    private LocalDateTime createdAt;

    @Getter
    private static List<Producer> producers = new ArrayList<>(List.of(
            Producer.builder().name("Mappa").id(1L).createdAt(LocalDateTime.now()).build(),
            Producer.builder().name("Kyoto Animation").id(2L).createdAt(LocalDateTime.now()).build(),
            Producer.builder().name("Madhouse").id(3L).createdAt(LocalDateTime.now()).build()
    ));
}
