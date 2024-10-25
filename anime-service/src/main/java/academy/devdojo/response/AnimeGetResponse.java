package academy.devdojo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class AnimeGetResponse
{
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
