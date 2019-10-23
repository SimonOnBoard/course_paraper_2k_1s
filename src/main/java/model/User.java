package model;

import lombok.*;
import service.PasswordEncripter;

import java.sql.Date;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String nick;
    private String email;
    private Date birth_date;
    private LocalDateTime regiStrationDate;
    private Long countPosts;
    
    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.getRegiStrationDate();
    }

    public User(String name, String password, String nick, String mail, Date birth) {
        this.name = name;
        this.password = PasswordEncripter.getEncryptedString(password);
        this.nick = nick;
        this.email = mail;
        this.birth_date = birth;
        this.countPosts = 0L;
    }
}
