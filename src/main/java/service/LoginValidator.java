package service;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginValidator {
    public static List<String> errors;
    public static synchronized List<String> validate(String name, String password, Optional<User> user){
        errors = new ArrayList<>();
        password = PasswordEncripter.getEncryptedString(password);
        if(user.isPresent()) {
            User user1 = user.get();
            if (!user1.getName().equals(name) || !user1.getPassword().equals(password)){
                errors.add("Неверный логин или пароль");
            }
        }
        else{
            errors.add("Пользователя не существует, пожалуйста зарегистрируйтесь)");
        }
        return errors;
    }
}
