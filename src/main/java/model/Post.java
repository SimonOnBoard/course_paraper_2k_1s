package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import serializers.PostSerializer;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@JsonSerialize(using = PostSerializer.class)
public class Post {
    private Long id;
    private String name;
    private String text;
    private Category category;
    private String photoPath;
    private LocalDateTime publication;
    private Boolean showAuthor;
    private Long auth_id;
    private List<Comment> comments;

    public Post(Long id, String name, String text, Category category, String photoPath,
                LocalDateTime publication, Boolean showAuthor, Long auth_id) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.category = category;
        this.photoPath = photoPath;
        this.publication = publication;
        this.showAuthor = showAuthor;
        this.auth_id = auth_id;
        this.comments = null;
    }
}
