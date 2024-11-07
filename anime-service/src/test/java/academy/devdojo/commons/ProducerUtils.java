package academy.devdojo.commons;

import academy.devdojo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUtils {

    public List<Producer> newProducerList() {
        var dateTime = "2024-11-05T12:17:12.5031944";
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);

        var ufotable = Producer.builder().name("Ufotable").id(1L).createdAt(localDateTime).build();
        var witStudio = Producer.builder().name("Wit Studio").id(2L).createdAt(localDateTime).build();
        var studioGhibli = Producer.builder().name("Studio Ghibli").id(3L).createdAt(localDateTime).build();

        return new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    public Producer newProducerToSave() {
        return Producer.builder().name("Aniplex").id(99L).createdAt(LocalDateTime.now()).build();
    }
}
