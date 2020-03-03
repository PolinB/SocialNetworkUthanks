package com.uthanks.form.validators;

import com.uthanks.domain.User;
import com.uthanks.form.UserCredentials;
import com.uthanks.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Class for validation of data for login.
 */
@Component
public class UserCredentialsEnterValidator implements Validator {
    private final UserService userService;

    public UserCredentialsEnterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCredentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        UserCredentials enterForm = (UserCredentials) target;

        if (StringUtils.isBlank(enterForm.getLogin())) {
            errors.rejectValue("login", "login.is.empty", "login can not be empty");
            return;
        }

        User user = userService.findByLoginAndPassword(enterForm.getLogin(), enterForm.getPassword());
        if (user == null) {
            errors.rejectValue("password", "invalid.login.or.password",
                    "invalid login or password");
        }
    }
}