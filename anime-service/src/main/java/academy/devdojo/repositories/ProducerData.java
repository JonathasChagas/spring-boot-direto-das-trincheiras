package academy.devdojo.repositories;

import academy.devdojo.domain.Producer;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class ProducerData {
    private final List<Producer> producers = new ArrayList<>();

    {
        var mappa = Producer.builder().name("Mappa").id(1L).createdAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producer.builder().name("Kyoto Animation").id(2L).createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().name("Madhouse").id(3L).createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(mappa, kyotoAnimation, madhouse));
    }
}
