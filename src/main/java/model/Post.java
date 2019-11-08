package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Post {
    private Long id;
    private String name;
    private String text;
    private Category category;
    private String photopath;
    private LocalDateTime publication;
    private Boolean showAuthor;
    private Long auth_id;
    private List<Comment> comments;

    public Post(Long id, String name, String text, Category category, String photopath, LocalDateTime publication, Boolean showAuthor, Long auth_id) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.category = category;
        this.photopath = photopath;
        this.publication = publication;
        this.showAuthor = showAuthor;
        this.auth_id = auth_id;
        this.comments = null;
        this.getName();
    }
}
