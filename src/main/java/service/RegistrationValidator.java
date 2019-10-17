package service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidator {

    public List<String> errors;
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public RegistrationValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public synchronized List<String> validate(String mail, String password, String nick, String name) {
        this.cleanErrors();
        this.validateMail(mail);
        this.validatePassword(password);
        return this.errors;
    }

    private boolean validatePassword(String password) {
        if(password.length()<8){
            errors.add("Error : invalid Password. Should have >=8 length");
        }
        return true;
    }

    private boolean validateMail(String mail) {
        matcher = pattern.matcher(mail);
        if(!matcher.matches()){
           errors.add("Error : invalid Email. Should contains @ and .xxx ты проиграл");
        }
        return matcher.matches();
    }

    public void cleanErrors() {
        this.errors = new ArrayList<>();
    }
}
