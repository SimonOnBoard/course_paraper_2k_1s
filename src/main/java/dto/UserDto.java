package dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UserDto {
    private String nick;
    private String photoPath;

    public UserDto(String nick, String photoPath) {
        this.nick = nick;
        this.photoPath = photoPath;
        this.getNick();
    }
}
