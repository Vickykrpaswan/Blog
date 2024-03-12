package com.blog.services;

import com.blog.payloads.PostDto;
import com.blog.repositories.PostResponse;

import java.util.List;

public interface PostService {
    // create
    PostDto createPostDto(PostDto postDto, Integer userId, Integer categoryId);

    // update
    PostDto updatePostDto(PostDto postDto, Integer postId);

    // delete
    void delete(Integer postId);

    // get all post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy);

    // get post by id
    PostDto getPostById(Integer postId);

    // get post by category
    List<PostDto> getPostByCategory(Integer categoryId);

    // get post by user
    List<PostDto> getPostByUser(Integer userId);

    // get by search post
    List<PostDto> searchPost(String keyword);
}
