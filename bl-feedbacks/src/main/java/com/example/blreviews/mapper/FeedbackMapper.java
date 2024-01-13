package com.example.blreviews.mapper;

import com.example.blreviews.domain.Feedback;
import com.example.blreviews.dto.FeedbackDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "id", ignore = true)
    Feedback toEntity(FeedbackDto dto);
    FeedbackDto toDto(Feedback feedback);
    List<FeedbackDto> toDtos(List<Feedback> feedbacks);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Feedback toUpdate, FeedbackDto dto);
}
