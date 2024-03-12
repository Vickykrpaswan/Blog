package com.blog.controllers;

import com.blog.payloads.PostDto;
import com.blog.repositories.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDto postDto1 = this.postService.createPostDto(postDto, userId, categoryId);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    // get by category
    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> postByCategory = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postByCategory,HttpStatus.OK);
    }

    // get by user
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> postByUser = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(postByUser,HttpStatus.OK);
    }

    // get by post Id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
    {
        PostDto postById = this.postService.getPostById(postId);
        return new ResponseEntity<>(this.postService.getPostById(postId),HttpStatus.OK);
    }

    // get All post
    @GetMapping("/post")
    public ResponseEntity<PostResponse> getPost(
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId", required = false)String sortBy){
        return new ResponseEntity<>(this.postService.getAllPost(pageNumber,pageSize,sortBy), HttpStatus.OK);
    }

    // delete by Id
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Integer postId){
        this.postService.delete(postId);
        return new ResponseEntity<>(Map.of("message","successfully deleted"),HttpStatus.OK);
    }

    // update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable Integer postId){
        PostDto postDto1 = this.postService.updatePostDto(postDto, postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    // search
    @GetMapping("/post/search/{keyword}")
    public ResponseEntity<List<PostDto>> search(@PathVariable("keyword") String keyword){
        List<PostDto> postDtos = this.postService.searchPost(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws Exception {
        PostDto postDto = this.postService.getPostById(postId);
        String filename = this.fileService.uploadImage(path, image);
        postDto.setImageName(filename);
        PostDto postDto1 = this.postService.updatePostDto(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    // get method to serve file
    @GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
