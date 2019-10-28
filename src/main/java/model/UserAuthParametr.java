package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
public class UserAuthParametr {
    Long id;
    String parametr;
    Long ownerId;

    public UserAuthParametr(String parametr, Long ownerId) {
        this.parametr = parametr;
        this.ownerId = ownerId;
    }
}
