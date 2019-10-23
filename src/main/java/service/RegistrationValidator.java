package service;

import dao.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidator {

    public List<String> errors;
    private Pattern pattern;
    private Matcher matcher;
    private UsersRepository userDao;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public RegistrationValidator() {
        userDao = new UsersRepository();
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public synchronized List<String> validate(String mail, String password, String nick, String name) {
        this.cleanErrors();
        this.validateMail(mail);
        this.validatePassword(password);
        this.validateName(name);
        return this.errors;
    }

    private void validateName(String name) {
        if(userDao.find(name).isPresent()){
            errors.add("Error : invalid Username. User with that name already exists");
        }
    }

    private void validatePassword(String password) {
        if(password.length()<8){
            errors.add("Error : invalid Password. Should have >=8 length");
        }
    }

    private void validateMail(String mail) {
        matcher = pattern.matcher(mail);
        if(!matcher.matches()){
           errors.add("Error : invalid Email. Should contains @ and .xxx ");
        }
    }

    public void cleanErrors() {
        this.errors = new ArrayList<>();
    }
}
