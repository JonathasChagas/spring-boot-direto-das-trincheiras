package academy.devdojo.controllers;

import academy.devdojo.domain.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

    @GetMapping
    public List<Producer> listAll(@RequestParam(required = false) String name) {
        if (name == null) return Producer.getProducers();

        return Producer.getProducers().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Producer findById(@PathVariable Long id) {
        return Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst().orElse(null);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
    headers = "x-api-key = 1234")
    public Producer save(@RequestBody Producer producer, @RequestHeader HttpHeaders headers) {
        long id = Producer.getProducers().stream().mapToLong(Producer::getId).max().orElseThrow(NoSuchElementException::new);
        log.info("'{}'", headers);

        producer.setId(id + 1);
        Producer.getProducers().add(producer);

        return producer;
    }
}
