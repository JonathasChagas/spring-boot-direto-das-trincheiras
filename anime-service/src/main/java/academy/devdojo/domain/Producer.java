package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {
    @EqualsAndHashCode.Include
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
