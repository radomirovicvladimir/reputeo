package com.reputeo.mapper;

import com.reputeo.dto.response.CommentResponse;
import com.reputeo.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "hasMoreReplies", ignore = true)
    CommentResponse toResponse(Comment comment);

}