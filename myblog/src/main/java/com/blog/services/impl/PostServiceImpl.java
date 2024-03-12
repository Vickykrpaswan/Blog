package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.PostResponse;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPostDto(PostDto postDto, Integer userId, Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User","User Id",userId));

        Category cat =this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName(postDto.getImageName());
        post.setDate(new Date());
        post.setCategory(cat);
        post.setUser(user);

        Post saved = this.postRepo.save(post);

        return this.modelMapper.map(saved,PostDto.class);
    }

    @Override
    public PostDto updatePostDto(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post saved = this.postRepo.save(post);
        return this.modelMapper.map(saved, PostDto.class);
    }

    @Override
    public void delete(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
       // List<Post> all = this.postRepo.findAll(pageable);
        Page<Post> postRepoAll = this.postRepo.findAll(pageable);
        List<Post> all = postRepoAll.getContent();

        List<PostDto> postDtos = all.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postRepoAll.getNumber());
        postResponse.setPageSize(postRepoAll.getSize());
        postResponse.setTotalPages(postRepoAll.getTotalPages());
        postResponse.setTotalElements(postRepoAll.getTotalElements());
        postResponse.setLastPage(postRepoAll.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat =this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        List<Post> category = this.postRepo.findByCategory(cat);
        return category.stream().map((c) -> this.modelMapper.map(c, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User","User Id",userId));
        List<Post> byUser = this.postRepo.findByUser(user);
        return byUser.stream().map((c)->this.modelMapper.map(c, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> byTitleContaining = this.postRepo.findByTitleContaining(keyword);
        return byTitleContaining.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
