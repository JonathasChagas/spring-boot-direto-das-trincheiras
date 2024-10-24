package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Producer {
    private Long id;
    @JsonProperty("name")
    private String name;

    @Getter
    private static List<Producer> producers = new ArrayList<>(List.of(
            Producer.builder().name("Mappa").id(1L).build(),
            Producer.builder().name("Kyoto Animation").id(2L).build(),
            Producer.builder().name("Madhouse").id(3L).build()
    ));
}
