package academy.devdojo.producer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class ProducerGetResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
