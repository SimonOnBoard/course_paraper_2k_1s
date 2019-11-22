package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import serializers.CommentSerializer;
import serializers.PostSerializer;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@JsonSerialize(using = CommentSerializer.class)
public class Comment {
    private Long id;
    private String text;
    private Long ownerId;
    private Long postId;
    private LocalDateTime date;

    public Comment(String text, Long id, Long postId, LocalDateTime now) {
        this.text = text;
        this.ownerId = id;
        this.postId = postId;
        this.date = now;
    }

}
