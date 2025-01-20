package academy.devdojo.producer;

import academy.devdojo.domain.Producer;
import academy.devdojo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository repository;

    public List<Producer> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByNameIgnoreCase(name);
    }

    public Producer findByIdOrThrowNotFound(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Producer not Found"));
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public void delete(Long id) {
        var producer = findByIdOrThrowNotFound(id);
        repository.delete(producer);
    }

    public void update(Producer producerToUpdate) {
        assertProducerExists(producerToUpdate.getId());
        repository.save(producerToUpdate);
    }

    private void assertProducerExists(Long id) {
        findByIdOrThrowNotFound(id);
    }
}
