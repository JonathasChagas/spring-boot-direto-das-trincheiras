package academy.devdojo.repositories;

import academy.devdojo.domain.Producer;
import external.dependency.Connection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProducerHardCodedRepository {
    private static final List<Producer> PRODUCERS = new ArrayList<>(List.of(
            Producer.builder().name("Mappa").id(1L).createdAt(LocalDateTime.now()).build(),
            Producer.builder().name("Kyoto Animation").id(2L).createdAt(LocalDateTime.now()).build(),
            Producer.builder().name("Madhouse").id(3L).createdAt(LocalDateTime.now()).build()
    ));

    private final Connection connection;

    public static List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name) {
        return PRODUCERS.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer) {
        PRODUCERS.add(producer);
        return producer;
    }

    public void delete(Producer producer) {
        PRODUCERS.remove(producer);
    }

    public void update(Producer producer) {
        delete(producer);
        save(producer);
    }
}
