package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import serializers.PostSerializer;
import serializers.UserSerializer;
import service.PasswordEncripter;

import java.sql.Date;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@JsonSerialize(using = UserSerializer.class)
public class User {
    private Long id;
    private String name;
    private String password;
    private String nick;
    private String email;
    private Date birth_date;
    private LocalDateTime regiStrationDate;
    private Long countPosts;
    private String photoPath;


    public User(String name, String password, String nick, String mail, Date birth) {
        this.name = name;
        this.password = PasswordEncripter.getEncryptedString(password);
        this.nick = nick;
        this.email = mail;
        this.birth_date = birth;
        this.countPosts = 0L;
    }

    public User(String name, String password, String nick, String mail,
                Date birth, LocalDateTime now, Long l, String photoPath) {
        this.name = name;
        this.password = PasswordEncripter.getEncryptedString(password);
        this.nick = nick;
        this.email = mail;
        this.birth_date = birth;
        this.regiStrationDate = now;
        this.countPosts = l;
        this.photoPath = photoPath;

    }
}
