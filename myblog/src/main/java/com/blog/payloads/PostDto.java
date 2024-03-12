package com.blog.payloads;


import com.blog.entities.Comment;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postid;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private String imageName="default.jpg";
    private Date date;
    private CategoryDto category;
    private UserDto user;

    private Set<CommentDto> comments=new HashSet<>();
}
