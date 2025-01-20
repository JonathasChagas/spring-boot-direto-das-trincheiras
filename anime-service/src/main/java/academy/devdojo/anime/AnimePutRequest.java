package academy.devdojo.anime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnimePutRequest {
    @NotNull(message = "The field 'id' is required")
    private Long id;
    @NotBlank(message = "The field 'name' is required")
    private String name;
}
