package academy.devdojo.mapper;

import academy.devdojo.domain.Anime;
import academy.devdojo.request.*;
import academy.devdojo.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnimeMapper {
    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(academy.devdojo.repositories.AnimeHardCodedRepository.findAll().stream().mapToLong(Anime::getId).max().orElseThrow(java.util.NoSuchElementException::new) + 1)")
    Anime toAnime(AnimePostRequest postRequest);
    Anime toAnime(AnimePutRequest request);

    AnimePostResponse toAnimePostResponse(Anime anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> anime);
}
