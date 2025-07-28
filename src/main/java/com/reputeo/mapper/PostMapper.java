package com.reputeo.mapper;

import com.reputeo.dto.request.PostCreateRequest;
import com.reputeo.dto.response.PostResponse;
import com.reputeo.dto.response.PostSummaryResponse;
import com.reputeo.model.Comment;
import com.reputeo.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "keanuMoment", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Post toEntity(PostCreateRequest request);

    PostResponse toResponse(Post post);

    @Mapping(target = "commentCount", source = "comments", qualifiedByName = "countComments")
    PostSummaryResponse toSummaryResponse(Post post);

    @Named("countComments")
    default int countComments(List<Comment> comments) {
        return comments != null ? comments.size() : 0;
    }
}