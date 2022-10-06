package com.example.reddit_clone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.reddit_clone.dto.SubRedditRequest;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.SubReddit;

@Mapper(componentModel =  "spring")
public interface SubRedditMapper {  
    
    /**
     * 
     * @param subReddit
     * @return
     */
    @Mapping(
        target = "numberOfPosts",
        expression = "java(mapPosts(subReddit.getPosts()))"
    )
    SubRedditRequest mapSubRedditToDto(SubReddit subReddit);

    
    /**
     * 
     * @param numberOfPosts
     * @return
     */
    default Integer mapPosts(List<Post> numberOfPosts) { 
        return numberOfPosts.size();
    }


    /**
     * 
     * @param subRedditDto
     * @return
     */
    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubReddit mapDtoToSubReddit(SubRedditRequest subRedditDto);
}
